package com.generic.utils;

public class CommonsUtil {
    public static boolean isInvalidPattern(String obj) {
        String pattern = "^(?=[\\w\\d])(?=[\\w\\s\\d])[\\d\\w\\W].+\\S$";
        return !obj.matches(pattern);
    }
}
