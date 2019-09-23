package com.wqp.common.util.common;

import org.apache.commons.lang3.CharUtils;

/**
 * 字符工具类
 */
public final class CharUtil extends CharUtils {
    public static final char DQUOTE = '"';
    public static final char EQ = '=';
    public static final char QUOTE = '\'';
    public static final char SPACE = ' ';
    public static final char TAB = '\t';
    public static final char EM = '-';

    public static char getUpperCaseHex(int digit) {
        if (digit >= 0 && digit < 16) {
            return digit < 10 ? getNumericalDigit(digit) : getUpperCaseAlphaDigit(digit);
        } else {
            return '\u0000';
        }
    }

    public static char getLowerCaseHex(int digit) {
        if (digit >= 0 && digit < 16) {
            return digit < 10 ? getNumericalDigit(digit) : getLowerCaseAlphaDigit(digit);
        } else {
            return '\u0000';
        }
    }

    private static char getNumericalDigit(int digit) {
        return (char)(48 + digit);
    }

    private static char getUpperCaseAlphaDigit(int digit) {
        return (char)(65 + digit - 10);
    }

    private static char getLowerCaseAlphaDigit(int digit) {
        return (char)(97 + digit - 10);
    }

    private CharUtil() {
    }
}
