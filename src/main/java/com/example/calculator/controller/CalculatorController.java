package com.example.calculator.controller;

import com.example.calculator.dto.CalculatorResponse;
import com.example.calculator.service.CalculatorService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/add")
    public CalculatorResponse add(
            @RequestParam double a,
            @RequestParam double b) {

        return new CalculatorResponse(
                "add", a, b,
                calculatorService.add(a, b)
        );
    }

    @GetMapping("/subtract")
    public CalculatorResponse subtract(
            @RequestParam double a,
            @RequestParam double b) {

        return new CalculatorResponse(
                "subtract", a, b,
                calculatorService.subtract(a, b)
        );
    }

    @GetMapping("/multiply")
    public CalculatorResponse multiply(
            @RequestParam double a,
            @RequestParam double b) {

        return new CalculatorResponse(
                "multiply", a, b,
                calculatorService.multiply(a, b)
        );
    }

    @GetMapping("/divide")
    public CalculatorResponse divide(
            @RequestParam double a,
            @RequestParam double b) {

        return new CalculatorResponse(
                "divide", a, b,
                calculatorService.divide(a, b)
        );
    }
}
