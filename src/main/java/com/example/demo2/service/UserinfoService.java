package com.example.demo2.service;

import com.example.demo2.model.Userchainrelation;
import com.example.demo2.model.Userinfo;
import jakarta.validation.Valid;

public interface UserinfoService {
    boolean insertUserChain(@Valid Userinfo userinfo, @Valid Userchainrelation userchainrelation);
}