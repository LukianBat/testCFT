package com.lukianbat.domain;

import java.util.List;

public interface OnParsedExpression {
    void onParsed(List<String> expression);
    void onError(String message);
}
