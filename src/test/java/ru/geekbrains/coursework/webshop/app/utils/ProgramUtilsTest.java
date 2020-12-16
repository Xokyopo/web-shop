package ru.geekbrains.coursework.webshop.app.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramUtilsTest {
    private ProgramUtils programUtils;

    @BeforeEach
    public void init() {
        this.programUtils = new ProgramUtils();
    }

    @Test
    public void convertToHumanFileLength_ShouldNotThrow_WhenInputMaxLong() {
        assertFalse(this.programUtils.convertToHumanFileLength(Long.MAX_VALUE).isEmpty());
    }

    @Test
    public void convertToHumanFileLength_ShouldNotThrow_WhenInputMinLong() {
        assertFalse(this.programUtils.convertToHumanFileLength(Long.MIN_VALUE).isEmpty());
    }

    @Test
    public void convertToHumanFileLength_ShouldReturnTrue_WhenCompareCalculatedAndReturnedValues() {
        long value = 1049586898L;
        String expected = "1000,96 MB";
        String actual = this.programUtils.convertToHumanFileLength(value);
        assertEquals(expected, actual);
    }

    @Test
    public void removeSlashOnStartAndEnd_ShouldThrowException_WhenInputIsNull() {
        Class<IllegalArgumentException> expected = IllegalArgumentException.class;
        Executable actual = () -> ProgramUtils.removeSlashOnStartAndEnd(null);
        assertThrows(expected, actual);
    }

    @Test
    public void removeSlashOnStartAndEnd_ShouldGetEmptyString_WhenInputEmptyString() {
        String expected = "";
        String actual = ProgramUtils.removeSlashOnStartAndEnd(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void removeSlashOnStartAndEnd_ShouldGetEmptyString_WhenInputOnlyOneSlash() {
        String expected = "";
        String actual = ProgramUtils.removeSlashOnStartAndEnd("/");
        assertEquals(expected, actual);
    }

    @Test
    public void removeSlashOnStartAndEnd_ShouldGetEmptyString_WhenInputTwoSlash() {
        String expected = "";
        String actual = ProgramUtils.removeSlashOnStartAndEnd("//");
        assertEquals(expected, actual);
    }

    @Test
    public void removeSlashOnStartAndEnd_ShouldGetSlash_WhenInputThreeSlash() {
        String expected = "/";
        String actual = ProgramUtils.removeSlashOnStartAndEnd("///");
        assertEquals(expected, actual);
    }

    @Test
    public void removeSlashOnStartAndEnd_ShouldReturnInputString_WhenInputAllNonSlashSigns() {
        String expected = "1234567890-=qwertyuiop[]asdfghjkl;'zxcvbnm,.`~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:ZXCVBNM<>?\"\\";
        String actual = ProgramUtils.removeSlashOnStartAndEnd(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void getRequestMappingValue_ShouldThrowException_WhenInputIsNull() {
        Class<IllegalArgumentException> expected = IllegalArgumentException.class;
        Executable actual = () -> ProgramUtils.getRequestMappingValue(null);
        assertThrows(expected, actual);
    }

    @Test
    public void getRequestMappingValue_ShouldReturnEmptyOptional_WhenInputNotAnnotatedClass() {
        Optional<String> actual = ProgramUtils.getRequestMappingValue(Object.class);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void getRequestMappingValue_ShouldReturnAnnotatedValue_WhenInputAnnotatedClass() {
        String expected = "/test/test";
        String actual = ProgramUtils.getRequestMappingValue(new TestClassWithOneAnnotationParameter()).get();
        assertEquals(expected, actual);
    }

    @Test
    public void getRequestMappingValue_ShouldReturnAnnotatedValue_WhenInputAnnotatedClassWhitTwoAnnotation() {
        String expected = "/test/test";
        String actual = ProgramUtils.getRequestMappingValue(new TestClassWithTwoAnnotationParameter()).get();
        assertEquals(expected, actual);
    }

    @RequestMapping("/test/test")
    private static class TestClassWithOneAnnotationParameter {
    }

    @RequestMapping(params = "sdf", value = "/test/test")
    private static class TestClassWithTwoAnnotationParameter {
    }

}
