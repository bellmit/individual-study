package cn.gyw.thirdpart.date;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO
 *
 * @author yuewu.guan
 * @date 2021/10/29 14:30
 */
class DateValidatorTest {

    @Test
    void test() {
        DateValidator.validate("2021.10.22");
    }

    @Test
    void validate() {

        DateValidator.validate();
        DateValidator.validate("");
        DateValidator.validate(null, null);
        DateValidator.validate(null, "20211022");
        DateValidator.validate("202192", "2021102", "20211033", "2021.10.22");
    }
}