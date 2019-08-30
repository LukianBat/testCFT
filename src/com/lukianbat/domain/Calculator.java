package com.lukianbat.domain;

import java.util.*;
import java.lang.*;

public class Calculator {
    private static String operators = "+-*/";
    private static String delimiters = "() " + operators;


    private boolean isDelimiter(String string) {
        if (string.length() != 1) return false;
        for (int i = 0; i < delimiters.length(); i++) {
            if (string.charAt(0) == delimiters.charAt(i)) return true;
        }
        return false;
    }

    private boolean isOperator(String string) {
        if (string.equals("u-")) return true;
        for (int i = 0; i < operators.length(); i++) {
            if (string.charAt(0) == operators.charAt(i)) return true;
        }
        return false;
    }

    private boolean containCorrectSymbols(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i)) && !isDelimiter(String.valueOf(string.charAt(i)))
                    && !isOperator(String.valueOf(string.charAt(i))) && string.charAt(i) != '.')
                return false;
        }
        return true;
    }


    private int priority(String token) {
        if (token.equals("(")) return 1;
        if (token.equals("+") || token.equals("-")) return 2;
        if (token.equals("*") || token.equals("/")) return 3;
        return 4;
    }

    public void parseExpression(String infix, OnParsedExpressionCallback callback) {
        List<String> postfix = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringTokenizer tokenizer = new StringTokenizer(infix, delimiters, true);
        String prev = "";
        String current;
        if (!containCorrectSymbols(infix)) {
            callback.onError("Некорректное выражение.");
            return;
        }
        while (tokenizer.hasMoreTokens()) {
            current = tokenizer.nextToken();
            if (isOperator(current) && isOperator(prev) && !current.equals("-") && !prev.equals("-")) {
                callback.onError("Некорректное выражение.");
                return;
            }
            if (!tokenizer.hasMoreTokens() && isOperator(current)) {
                callback.onError("Некорректное выражение.");
                return;
            }
            if (current.equals(" ")) continue;
            if (isDelimiter(current)) {
                if (current.equals("(")) stack.push(current);
                else if (current.equals(")")) {
                    while (!stack.getFirst().equals("(")) {
                        postfix.add(stack.pop());
                        if (stack.isEmpty()) {
                            callback.onError("Скобки не согласованы.");
                            return;
                        }
                    }
                    stack.pop();
                } else {
                    if (current.equals("-") && (prev.equals("") || (isDelimiter(prev) && !prev.equals(")")))) {
                        current = "u-";
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
                callback.onError("Скобки не согласованы.");
                return;
            }
        }

        callback.onParsed(postfix);
    }


    public Double calculateParsedExpression(List<String> postfix) {
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