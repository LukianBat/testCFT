package com.lukianbat;


import com.lukianbat.domain.Calculator;
import com.lukianbat.presentation.CalculatorPresenter;
import com.lukianbat.presentation.CalculatorView;

public class Main {

    public static void main(String[] args) {
        new CalculatorView(new CalculatorPresenter(new Calculator())).setVisible(true);
    }
}