package com.kotlindemo.hx;

import androidx.test.filters.SmallTest;
import org.junit.Test;

import java.math.BigInteger;

/**
 * Created by Manish Patel on 6/17/2019.
 */
@SmallTest
public class HexToStr {

    public static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static final int LOW_BYTE_MASK = 0x0F;

    String Hexdata = "000000000000003508010000016B6408763F002B39617A0DBD56F2000000000000000004000243102FCD4C11014600000140014E00000000000000000100000660";

    @Test
    public void conversionText() {
        //String AsciiData = toHex(hex.getBytes());
        //System.out.println("HexData: " + AsciiData);

        String AsciiData = HexToAscii(Hexdata);
        System.out.println("AsciiData: " + AsciiData);
    }

    public static String HexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }


    public static String toHex(byte[] data) {
        return toHex(data, false);
    }

    public static String toHex(byte[] data, boolean usePadding) {
        if (data == null || data.length == 0) {
            return "";
        }

        if (usePadding) {
            BigInteger bi = new BigInteger(1, data);
            String hexString = bi.toString(16).toUpperCase();

            // Hex value will be double the length of the passed array, as it takes two characters to represent any given byte.
            // If paddingLength is not 0, add a leading zero the the hex string.
            int paddingLength = (data.length * 2) - hexString.length();

            if (paddingLength > 0)
                return String.format("%0" + paddingLength + "d", 0) + hexString;

            return hexString;

        } else {

            /*
            Algorithm 1:
            ============

              int l = data.length;

                char[] hex = new char[l << 1];

                for (int i = 0, j = 0; i < l; i++) {
                    // two characters form the hex value.
                    hex[j++] = HEX_CHARS[(0xF0 & data[i]) >>> 4];
                    hex[j++] = HEX_CHARS[0x0F & data[i]];
                }

                return hex;
         */

            char[] hex = new char[data.length * 2];

            for (int i = 0; i < data.length; i++) {
                int v = data[i] & 0xFF;
                hex[i * 2] = HEX_CHARS[v >>> 4];
                hex[i * 2 + 1] = HEX_CHARS[v & LOW_BYTE_MASK];
            }

            return new String(hex);
        }
    }
}
