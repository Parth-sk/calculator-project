package com.example.calculator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    void testAddition() {
        assertEquals(5.0, calculatorService.add(2, 3));
    }

    @Test
    void testSubtraction() {
        assertEquals(2.0, calculatorService.subtract(5, 3));
    }

    @Test
    void testMultiplication() {
        assertEquals(6.0, calculatorService.multiply(2, 3));
    }

    @Test
    void testDivision() {
        assertEquals(5.0, calculatorService.divide(10, 2));
    }

    @Test
    void testDivisionByZero() {
        assertThrows(
                IllegalArgumentException.class,
                () -> calculatorService.divide(10, 0)
        );
    }
}
