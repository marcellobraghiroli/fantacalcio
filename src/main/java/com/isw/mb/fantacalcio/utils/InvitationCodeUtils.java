package com.isw.mb.fantacalcio.utils;

public class InvitationCodeUtils {

    private static final String CHAR_ORDER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = CHAR_ORDER.length();

    public static String generateCode(String lastCode) {

        if (lastCode == null || lastCode.length() != 6) {
            throw new IllegalArgumentException("Input must be a string of 6 alphanumeric characters.");
        }

        char[] chars = lastCode.toCharArray();
        int carry = 1;

        for (int i = chars.length - 1; i >= 0 && carry > 0; i--) {
            int index = CHAR_ORDER.indexOf(chars[i]);
            if (index == -1) {
                throw new IllegalArgumentException("Invalid character in input string.");
            }
            index = (index + carry) % BASE;
            carry = (index == 0) ? 1 : 0;
            chars[i] = CHAR_ORDER.charAt(index);
        }

        return new String(chars);

    }

}
