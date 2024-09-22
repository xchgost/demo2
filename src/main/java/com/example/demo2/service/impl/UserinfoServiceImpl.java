package com.example.demo2.service.impl;

import com.example.demo2.dao.UserinfoDao;
import com.example.demo2.enums.StatusEnum;
import com.example.demo2.exceptions.*;
import com.example.demo2.model.Userchainrelation;
import com.example.demo2.model.Userinfo;
import com.example.demo2.service.UserinfoService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@Validated
public class UserinfoServiceImpl implements UserinfoService {

    private final UserinfoDao userinfoDao;

    private static final String IMAGE_DIR = "D:/log/image/";          // Picture saving directory

    @Autowired
    public UserinfoServiceImpl(UserinfoDao userinfoDao) {
        this.userinfoDao = userinfoDao;
    }

    @Transactional
    @Override
    public boolean insertUserChain(@Valid Userinfo userinfo, @Valid Userchainrelation userchainrelation) {

        if (userinfoDao.selectUserByAddress(userinfo.getAddress())) {
            log.error("address already exists: {}", userinfo.getAddress());
            throw new VIException(StatusEnum.ADDRESS_ALREADY_REGISTERED);
        }
        userinfo.setId(UUID.randomUUID().toString());
        String logoPath = saveImage(userinfo.getLogo());
        userinfo.setLogo(logoPath);
        try {
            userinfoDao.insertUser(userinfo);
            userinfoDao.insertChain(userchainrelation);
        } catch (
                DataAccessException e) {
            log.error("User information insertion failed: {}", e.getMessage());
            throw new VIException(StatusEnum.REQUEST_FAILED);
        }
        return true;
    }

    public String saveImage(String base64Image) {

        if (base64Image == null || base64Image.isEmpty()) {
            return null;
        }

        String imageDataBytes = base64Image.startsWith("data:image/png;base64,") ?
                base64Image.split(",")[1] : base64Image;
        byte[] imageBytes = Base64.getDecoder().decode(imageDataBytes);

        String imageFileName = UUID.randomUUID().toString() + ".png";
        String imagePath = IMAGE_DIR + imageFileName;

        try (FileOutputStream fos = new FileOutputStream(imagePath)) {
            fos.write(imageBytes);
            return imagePath;                                        // Return image path
        } catch (IOException e) {
            log.error("Image saving failed: {}", e.getMessage(), e);
            throw new RuntimeException("Image saving failed", e);
        }
    }
}
