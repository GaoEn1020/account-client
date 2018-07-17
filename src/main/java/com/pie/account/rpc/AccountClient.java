package com.pie.account.rpc;

import com.pie.account.controller.UserController;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pie.microservice.rpc.client.RpcClient;
import pie.microservice.rpc.common.RpcError;
import pie.microservice.rpc.exception.ExceptionUtil;
import pie.microservice.rpc.exception.RpcException;
import pie.secure.account.proto.AccountServiceGrpc;
import pie.secure.account.proto.CheckAccountExistRequest;
import pie.secure.account.proto.CheckAccountExistResponse;

import javax.annotation.PostConstruct;

@Component
public class AccountClient {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    RpcClient<AccountServiceGrpc.AccountServiceBlockingStub> mRpcClient=null;

    //rpcClient初始化
    /**
     * mRpcClient.init函数说明：
     *  参数1：clientIndex：表示连接哪个rpc服务，
     *         rpc.properties配置文件中的连接RPC服务列表（microservice.client.rpcserver）中的序号；
     *  参数2：grpcClasszz：grpc类的class。
     * @return
     */
    @PostConstruct
    private void initRpcClient(){
        try {
            mRpcClient.init(0,AccountServiceGrpc.class);
        }catch (Exception exp) {
            exp.printStackTrace();
            logger.error(ExceptionUtil.getMessage(exp));
        }
    }

    /**
     * 检验账号是否存在
     * @param account 账号
     * @param accountType 账号类型
     * @return
     * @throws RpcException
     * @throws StatusRuntimeException
     * @throws Exception
     */
    public CheckAccountExistResponse checkAccountIsExist(String account, Integer accountType){
        CheckAccountExistResponse response = null;
        AccountServiceGrpc.AccountServiceBlockingStub stub=null;
        try {
            stub=this.mRpcClient.borrowStub();
            CheckAccountExistRequest request = CheckAccountExistRequest.newBuilder().setAccount(account)
                    .setAccountType(accountType).build();
            response = stub.checkAccountExist(request);
        } catch (StatusRuntimeException exp) {
            logger.error(ExceptionUtil.getMessage(exp));
            RpcError err = RpcException.fromStatusException(exp);
            if(err != null) {
                this.mRpcClient.returnStub(stub);
                throw new RpcException(err);
            } else {
                this.mRpcClient.returnStub(stub);
                throw exp;
            }
        } catch (Exception exp) {
            logger.error(ExceptionUtil.getMessage(exp));
        } finally {
            this.mRpcClient.returnStub(stub);
        }
        return response;
    }
}
