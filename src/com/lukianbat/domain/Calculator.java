package com.lukianbat.domain;

import java.util.*;
import java.lang.*;

public class Calculator {

    private static String operators = "+-*/";
    private static String delimiters = "() " + operators;
    private static String unaryMinus = "u-";
    private static char pointSymbol = '.';
    private static String leftParenthesis = "(";
    private static String rightParenthesis = ")";
    private static String plusSymbol = "+";
    private static String minusSymbol = "-";
    private static String productSymbol = "*";
    private static String divisionSymbol = "/";
    private static String incorrectErrorMessage = "Некорректное выражение.";
    private static String parenthesisErrorMessage = "Скобки не согласованы.";

    private boolean isDelimiter(String string) {
        if (string.length() != 1) return false;
        for (int i = 0; i < delimiters.length(); i++) {
            if (string.charAt(0) == delimiters.charAt(i)) return true;
        }
        return false;
    }

    private boolean isOperator(String string) {
        if (string.equals(unaryMinus)) return true;
        for (int i = 0; i < operators.length(); i++) {
            if (string.charAt(0) == operators.charAt(i)) return true;
        }
        return false;
    }

    private boolean containCorrectSymbols(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i)) && !isDelimiter(String.valueOf(string.charAt(i)))
                    && !isOperator(String.valueOf(string.charAt(i))) && string.charAt(i) != pointSymbol)
                return false;
        }
        return true;
    }


    private int priority(String token) {
        if (token.equals(leftParenthesis)) return 1;
        if (token.equals(plusSymbol) || token.equals(minusSymbol)) return 2;
        if (token.equals(productSymbol) || token.equals(divisionSymbol)) return 3;
        return 4;
    }

    public Double getResult(String infix) throws Exception {
        List<String> postfix = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringTokenizer tokenizer = new StringTokenizer(infix, delimiters, true);
        String prev = "";
        String current;
        if (!containCorrectSymbols(infix)) {
            throw new Exception(incorrectErrorMessage);
        }
        while (tokenizer.hasMoreTokens()) {
            current = tokenizer.nextToken();
            if (isOperator(current) && isOperator(prev) && !current.equals(minusSymbol) && !prev.equals(minusSymbol)) {
                throw new Exception(incorrectErrorMessage);
            }
            if (!tokenizer.hasMoreTokens() && isOperator(current)) {
                throw new Exception(incorrectErrorMessage);
            }
            if (current.equals(" ")) continue;
            if (isDelimiter(current)) {
                if (current.equals(leftParenthesis)) stack.push(current);
                else if (current.equals(rightParenthesis)) {
                    while (!stack.getFirst().equals(leftParenthesis)) {
                        postfix.add(stack.pop());
                        if (stack.isEmpty()) {
                            throw new Exception(parenthesisErrorMessage);
                        }
                    }
                    stack.pop();
                } else {
                    if (current.equals(minusSymbol) && (prev.equals("") || (isDelimiter(prev) && !prev.equals(rightParenthesis)))) {
                        current = unaryMinus;
                    } else {
                        while (!stack.isEmpty() && (priority(current) <= priority(stack.getFirst()))) {
                            postfix.add(stack.pop());
                        }

                    }
                    stack.push(current);
                }

            } else {
                postfix.add(current);
            }
            prev = current;
        }

        while (!stack.isEmpty()) {
            if (isOperator(stack.peek())) postfix.add(stack.pop());
            else {
                throw new Exception(parenthesisErrorMessage);
            }
        }

        return calculateParsedExpression(postfix);
    }


    private Double calculateParsedExpression(List<String> postfix) {
        Deque<Double> stack = new ArrayDeque<>();
        for (String x : postfix) {
            switch (x) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-": {
                    Double b = stack.pop(), a = stack.pop();
                    stack.push(a - b);
                    break;
                }
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/": {
                    Double b = stack.pop(), a = stack.pop();
                    stack.push(a / b);
                    break;
                }
                case "u-":
                    stack.push(-stack.pop());
                    break;
                default:
                    stack.push(Double.valueOf(x));
                    break;
            }
        }
        return stack.pop();
    }
}