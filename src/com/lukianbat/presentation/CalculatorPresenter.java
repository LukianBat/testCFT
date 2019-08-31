package com.lukianbat.presentation;

import com.lukianbat.domain.Calculator;


public class CalculatorPresenter {

    private Calculator calculator;

    public CalculatorPresenter(Calculator calculator) {
        this.calculator = calculator;
    }

    String getResult(String expression) {

        try {
            Double result = calculator.getResult(expression);
            return expression + " = " + result.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
