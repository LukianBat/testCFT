package com.lukianbat.presentation;

import com.lukianbat.domain.Calculator;
import com.lukianbat.domain.OnParsedExpressionCallback;

import java.util.List;

public class CalculatorPresenter {
    private Calculator calculator;


    public CalculatorPresenter(Calculator calculator) {
        this.calculator = calculator;
    }

    void getCurrent(String expression, OnCurrentCallback callback) {
        calculator.parseExpression(expression, new OnParsedExpressionCallback() {
            @Override
            public void onParsed(List<String> expressionList) {
                callback.onCurrent(expression + " = " + calculator.calculateParsedExpression(expressionList).toString());
            }

            @Override
            public void onError(String message) {
                callback.onCurrent(message);
            }
        });
    }
}
