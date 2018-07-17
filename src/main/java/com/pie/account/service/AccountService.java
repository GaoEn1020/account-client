package com.pie.account.service;

import com.pie.account.controller.UserController;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pie.gateway.basegw.rpc.proto.authn.AuthnServiceGrpc;
import pie.gateway.basegw.rpc.proto.authn.LoginResponse;
import pie.gateway.basegw.rpc.proto.authn.LoginResquest;
import pie.microservice.rpc.client.RpcClient;
import pie.microservice.rpc.common.RpcError;
import pie.microservice.rpc.exception.ExceptionUtil;
import pie.microservice.rpc.exception.RpcException;

public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    RpcClient<AuthnServiceGrpc.AuthnServiceBlockingStub> mRpcClient=null;

    //rpcClient初始化
    /**
     * mRpcClient.init函数说明：
     *  参数1：clientIndex：表示连接哪个rpc服务，
     *         rpc.properties配置文件中的连接RPC服务列表（microservice.client.rpcserver）中的序号；
     *  参数2：grpcClasszz：grpc类的class。
     * @return
     */
    private boolean initRpcClient(){
        try {
            mRpcClient.init(0,AuthnServiceGrpc.class);
            return true;
        }catch (Exception exp) {
            exp.printStackTrace();
            logger.error(ExceptionUtil.getMessage(exp));
            return false;
        }
    }

    /**
     * 登录
     * @param account
     * @param passwd
     * @return
     */
    public LoginResponse login(String account, String passwd) {
        LoginResponse response=null;
        AuthnServiceGrpc.AuthnServiceBlockingStub stub=null;
        try {
            stub=this.mRpcClient.borrowStub();
            LoginResquest request = LoginResquest.newBuilder().setAccount(account)
                    .setPassword(passwd).build();
            response = stub.login(request);
        }catch (StatusRuntimeException exp) {
            logger.error(ExceptionUtil.getMessage(exp));
            RpcError err = RpcException.fromStatusException(exp);
            //RpcError类中包含了RPC调用的错误信息
        } finally {
            this.mRpcClient.returnStub(stub);
        }
        return response;
    }
}
