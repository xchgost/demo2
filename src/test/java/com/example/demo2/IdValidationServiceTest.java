package com.example.demo2;

import com.example.demo2.service.IdValidationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IdValidationServiceTest {

    private final IdValidationService idValidationService = new IdValidationService();
    // 测试有效的身份证号码
    @Test
    void testValidId() {
        // 合法的身份证号码
        String validId = "430422200309077337";
        assertTrue(idValidationService.validateId(validId));
    }

    // 测试无效的身份证号码（边界情况）
    @Test
    void testInvalidIdPattern() {
        // 格式不符合的身份证号码
        String invalidId = "4304222003090773x7"; // 含有非法字符
        assertFalse(idValidationService.validateId(invalidId));

        invalidId = "030422200309077337"; // 不符合正则表达式
        assertFalse(idValidationService.validateId(invalidId));

        invalidId = "430111200002290121"; // 不符合正则表达式
        assertFalse(idValidationService.validateId(invalidId));
    }

    // 测试日期合法性
    @Test
    void testInvalidDate() {
        // 日期不合法
        String invalidId = "430422200309327331"; // 9月32日
        assertFalse(idValidationService.validateId(invalidId));

        invalidId = "43042220030229733X"; // 2月29日，非闰年
        assertFalse(idValidationService.validateId(invalidId));

        invalidId = "110119200002293431"; // 2月29日，闰年
        assertTrue(idValidationService.validateId(invalidId));
    }

    // 测试未来日期
    @Test
    void testFutureDate() {
        // 出生日期在未来
        String invalidId = "430422300309077331"; // 未来日期
        assertFalse(idValidationService.validateId(invalidId));
    }

    // 测试异常情况
    @Test
    void testExceptionHandling() {
        // 无效身份证在服务内产生异常
        String invalidId = "43042220030907733"; // 长度不足
        assertFalse(idValidationService.validateId(invalidId));

        invalidId = "430422200309077336"; // 长度不足，且最后一位不是合法校验位
        assertFalse(idValidationService.validateId(invalidId));
    }
}