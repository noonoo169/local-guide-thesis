package com.example.localguidebe.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class AddressUtils {
    public static String removeVietnameseAccents(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }
}
