package ru.geekbrains.coursework.webshop.app.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExtendedBCryptPasswordEncoderTest {
    private ExtendedBCryptPasswordEncoder extendedBCryptPasswordEncoder;

    @BeforeEach
    public void init() {
        this.extendedBCryptPasswordEncoder = new ExtendedBCryptPasswordEncoder();
    }

    @Test
    public void isBCryptPassword_ShouldReturnFalse_WhenInputNull() {
        assertFalse(this.extendedBCryptPasswordEncoder.isBCryptPassword(null));
    }

    @Test
    public void isBCryptPassword_ShouldReturnFalse_WhenInputIsEmptyString() {
        assertFalse(this.extendedBCryptPasswordEncoder.isBCryptPassword(""));
    }

    @Test
    public void isBCryptPassword_ShouldReturnTrue_WhenInputBcryptPassword() {
        String bcryptPassword = this.extendedBCryptPasswordEncoder.encode("slkfmtjbsk3240");
        assertTrue(this.extendedBCryptPasswordEncoder.isBCryptPassword(bcryptPassword));
    }

    @Test
    public void isBCryptPassword_ShouldReturnFalse_WhenInputNotBcryptPassword() {
        String bcryptPassword = "slkfmtjbsk3240";
        assertFalse(this.extendedBCryptPasswordEncoder.isBCryptPassword(bcryptPassword));
    }
}
