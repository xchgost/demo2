package com.example.demo2.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Userinfo {

    private String id;

    @NotNull(message = "address cannot be null")
    @Size(min = 32, max = 64, message = "address should be between 32 and 64 characters")
    private String address;

    @NotNull(message = "nickname cannot be null")
    @Size(min = 8, max = 32, message = "nickname should be between 8 and 32 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "nickname should only contain alphabets and numbers")
    private String nickname;

    @Size(max = 1024, message = "logo should be less than or equal to 1024 characters")
    private String logo;

    @Size(max = 1024, message = "description should be less than or equal to 1024 characters")
    private String description;

    private int chainId;

}
