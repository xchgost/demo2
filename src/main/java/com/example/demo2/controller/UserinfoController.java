package com.example.demo2.controller;

import com.example.demo2.common.R;
import com.example.demo2.model.Userchainrelation;
import com.example.demo2.model.Userinfo;
import com.example.demo2.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/userinfo", produces = "application/json")
public class UserinfoController {

    private final UserinfoService userinfoService;

    @Autowired
    public UserinfoController(UserinfoService userinfoService) {
        this.userinfoService = userinfoService;
    }

    @PostMapping("/insert")
    public R<?> insertUserChain(@RequestBody Userinfo userinfo) {
        Userchainrelation userchainrelation = new Userchainrelation();
        userchainrelation.setAddress(userinfo.getAddress());
        userchainrelation.setChainId(userinfo.getChainId());
        userinfoService.insertUserChain(userinfo, userchainrelation);
        return R.ok(userinfo);
    }
}