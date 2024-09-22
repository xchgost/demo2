package com.example.demo2.service;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
/**
 * 身份证号码验证服务类
 * <p>
 * 符合国家标准 GB 11643-1999 公民身份号码标准
 * 可点击此链接查看更多信息: <a href="https://www.doc88.com/p-5844404443071.html">GB 11643-1999</a>
 * </p>
 */

@Service
public class IdValidationService {

    private static final String ID_REGEX = "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[\\dX]$";
    private static final Pattern ID_PATTERN = Pattern.compile(ID_REGEX);

    // 校验年份是否为闰年
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // 校验出生日期的合法性
    private boolean isValidDate(int year, int month, int day) {
        if (month < 1 || month > 12) return false;
        int[] daysInMonth = {31, isLeapYear(year) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return day > 0 && day <= daysInMonth[month - 1];
    }

    // 校验身份证号码
    public boolean validateId(String idNumber) {
        if (!ID_PATTERN.matcher(idNumber).matches()) {
            return false;
        }

        String birthdateStr = idNumber.substring(6, 14);
        int year = Integer.parseInt(birthdateStr.substring(0, 4));
        int month = Integer.parseInt(birthdateStr.substring(4, 6));
        int day = Integer.parseInt(birthdateStr.substring(6, 8));

        // 校验出生日期的有效性
        if (!isValidDate(year, month, day)) {
            return false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date birthdate = sdf.parse(birthdateStr);
            Date currentDate = new Date();
            return !birthdate.after(currentDate) && checkChecksum(idNumber);
        } catch (Exception e) {
            return false;
        }
    }

    // 校验位计算
    private boolean checkChecksum(String idNumber) {
        int[] weight = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] checksum = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += (idNumber.charAt(i) - '0') * weight[i];
        }

        int mod = sum % 11;
        char expectedChecksum = checksum[mod];

        return expectedChecksum == idNumber.charAt(17);
    }
}