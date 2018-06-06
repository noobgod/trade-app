package com.xianjinxia.trade.app.controller;

import com.xianjinxia.trade.app.response.mqapp.ResultMsg;

public class BaseController {

    private static final ResultMsg PARAM_ERRO_RESP_ENTITY = new ResultMsg(
            ResultMsg.RESULT_FAIL, "param error");


    private static final ResultMsg INNER_ERROR_RESP_ENTITY = new ResultMsg(
            ResultMsg.RESULT_FAIL, "error");

    private static final ResultMsg SUCCESS_RESP_ENTITY = new ResultMsg(
            ResultMsg.RESULT_SUCCESS, "success");

    protected ResultMsg paramErrorResp() {
        return PARAM_ERRO_RESP_ENTITY;
    }


    protected ResultMsg innerErrorResp() {
        return INNER_ERROR_RESP_ENTITY;
    }

    protected ResultMsg successResp() {
        return SUCCESS_RESP_ENTITY;
    }
}
