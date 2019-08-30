package com.lukianbat;


import com.lukianbat.domain.Calculator;
import com.lukianbat.presentation.CalculatorPresenter;
import com.lukianbat.presentation.CalculatorView;

public class Main {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        CalculatorPresenter calculatorPresenter = new CalculatorPresenter(calculator);
        new CalculatorView(calculatorPresenter).setVisible(true);
    }
}