package com.pie.account.controller;

import com.pie.account.rpc.AccountClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private AccountClient accountClient;

    @GetMapping("/checkAccountIsExist/{account}/{accountType}")
    public Object checkAccountIsExist(@PathVariable String account,@PathVariable Integer accountType){
        if(StringUtils.isBlank(account) || accountType == null){
            return null;
        }
        return accountClient.checkAccountIsExist(account,accountType);
    }

}
