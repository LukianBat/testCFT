package com.lukianbat.presentation;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CalculatorView extends JFrame {

    private static String titleWindow = "TestCFT";
    private static int widthWindow = 400;
    private static int heightWindow = 250;
    private static int textSize = 23;
    private static int rows = 5;
    private static int cols = 4;
    private static int buttonsNumber = 17;
    private static String clearSymbol = "C";
    private static String clearText = "";
    private static String equalsSymbol = "=";
    private static String pointSymbol = ".";
    private static String leftParenthesis = "(";
    private static String rightParenthesis = ")";
    private static String plusSymbol = "+";
    private static String minusSymbol = "-";
    private static String productSymbol = "*";
    private static String divisionSymbol = "/";

    private boolean isCalculated = false;
    private JTextField calculatorField;
    private CalculatorPresenter presenter;

    public CalculatorView(CalculatorPresenter presenter) {
        this.presenter = presenter;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(titleWindow);
        setSize(widthWindow, heightWindow);
        setLocationRelativeTo(null);
        initView();
    }

    private void initView() {
        calculatorField = new JTextField();
        calculatorField.setHorizontalAlignment(JTextField.RIGHT);
        calculatorField.setEditable(true);
        calculatorField.setFont(new Font("Serif", Font.BOLD, textSize));
        add(calculatorField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(rows, cols));
        add(buttonPanel, BorderLayout.SOUTH);
        for (int i = 0; i < buttonsNumber; i++) {
            addButton(buttonPanel, i);
        }

        JButton clearButton = new JButton();
        clearButton.setText(clearSymbol);
        clearButton.addActionListener(actionEvent -> clearAction());
        buttonPanel.add(clearButton);

        JButton equalsButton = new JButton();
        equalsButton.setText(equalsSymbol);
        equalsButton.addActionListener(actionEvent -> equalsAction());
        buttonPanel.add(equalsButton);

        calculatorField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (isCalculated) {
                    clearAction();
                    isCalculated = false;
                }
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.VK_ENTER: {
                        equalsAction();
                        break;
                    }
                    case KeyEvent.VK_DELETE: {
                        clearAction();
                        break;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
    }

    private void addButton(Container parent, int name) {
        JButton button = new JButton();
        String symbol;
        switch (name) {
            case 10: {
                symbol = productSymbol;
                break;
            }
            case 11: {
                symbol = plusSymbol;
                break;
            }
            case 12: {
                symbol = minusSymbol;
                break;
            }
            case 13: {
                symbol = divisionSymbol;
                break;
            }
            case 14: {
                symbol = leftParenthesis;
                break;
            }
            case 15: {
                symbol = rightParenthesis;
                break;
            }
            case 16: {
                symbol = pointSymbol;
                break;
            }
            default: {
                symbol = String.valueOf(name);
            }
        }

        button.setText(symbol);
        button.addActionListener(actionEvent -> {
            if (isCalculated) {
                clearAction();
                isCalculated = false;
            }
            calculatorField.setText(calculatorField.getText() + symbol);
        });
        parent.add(button);
    }

    private void equalsAction() {
        isCalculated = true;
        String expression = calculatorField.getText();
        String result = presenter.getResult(expression);
        calculatorField.setText(expression + " = " + result);
    }

    private void clearAction() {
        calculatorField.setText(clearText);
    }
}