package com.example.demo2.controller;

import com.example.demo2.service.IdValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/id-validation")
public class IdValidationController {

    private final IdValidationService idValidationService;

    public IdValidationController(IdValidationService idValidationService) {
        this.idValidationService = idValidationService;
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateId(@RequestBody String idNumber) {
        if (idNumber == null || idNumber.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("身份证号码不能为空");
        }
        boolean isValid = idValidationService.validateId(idNumber.trim());
        if (isValid) {
            return ResponseEntity.ok("身份证号码有效"); // 返回200状态码
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("身份证号码无效"); // 返回400状态码
        }
    }
}