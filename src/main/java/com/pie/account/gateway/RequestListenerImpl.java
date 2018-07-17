package com.pie.account.gateway;

import org.springframework.stereotype.Service;
import pie.gateway.basegw.business.BaseGWRequestListener;
import pie.gateway.basegw.model.request.GWRequest;
import pie.gateway.basegw.rpc.proto.authn.LoginResponse;
import pie.gateway.basegw.rpc.proto.authn.VerifyTokenResponse;

@Service
public class RequestListenerImpl implements BaseGWRequestListener{

    /*
    接收到登录数据请求，用户登录时调用该接口
        rpcLoginResp：RPC服务登录响应结构数据
        request：基础网关请求体

    接口返回值：
        返回响应数据体给基础网关，基础网关将该数据体封装成固定的json串返回给前端
     */
    @Override
    public Object recvLoginData(LoginResponse loginResponse, GWRequest gwRequest) {
        //请求api
        String api = gwRequest.getRequestBody().getApi();
        //请求数据体
        String data = gwRequest.getRequestBody().getReqData();
        //这里处理登录业务，并根据通信协议格式，返回响应包结构体对象
        return null;
    }

    /*
    接收到常规数据请求，用户所有的非登录请求都调用该接口
        rpcTokenResp：RPC服务token响应结构数据
        request：基础网关请求体

    接口返回值：
        返回响应数据体给基础网关，基础网关将该数据体封装成固定的json串返回给前端
     */
    @Override
    public Object recvNormalData(VerifyTokenResponse verifyTokenResponse, GWRequest gwRequest) {
        //请求api
        String api = gwRequest.getRequestBody().getApi();
        //请求数据体
        String data = gwRequest.getRequestBody().getReqData();
        //这里处理业务，并根据通信协议格式，返回响应包结构体对象
        return null;
    }

}
