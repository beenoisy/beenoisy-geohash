/*
 * beenoisy-geohash - com.beenoisy.geohash.GeoHashs.java
 * ziyi.wang
 */
package com.beenoisy.geohash;

import java.util.HashMap;
import java.util.Map;

/**
 * GeoHash Utils <br>
 * 
 * @author jacks808@163.com<br>
 */
public final class GeoHashs {
    /**
     * geoHash length enum <br>
     * 
     * @author jacks808@163.com<br>
     */
    public static enum EncodeLength {
        LENGTH_2(2),
        /**
         * At 40 degrees north latitude, the maximum error is 35000 meters
         */
        LENGTH_4(4),
        /**
         * At 40 degrees north latitude, the maximum error is 1000 meters
         */
        LENGTH_6(6),
        /**
         * At 40 degrees north latitude, the maximum error is 40 meters
         */
        LENGTH_8(8),
        /**
         * At 40 degrees north latitude, the maximum error is 1 meters
         */
        LENGTH_10(10);

        int value;

        private EncodeLength(
                        int value) {
            this.value = value;
        }
    }

    /**
     * Minimum longitude constant
     */
    private static final int LON_MIN = -90;

    /**
     * Maximum longitude constant
     */
    private static final int LON_MAX = 90;
    /**
     * Minimum latitude constant
     */
    private static final int LAT_MIN = -180;
    /**
     * Maximum latitude constant
     */
    private static final int LAT_MAX = 180;

    /**
     * Base32 encode table
     */
    private static final Map<String, String> BASE32_ENCODE_MAP = new HashMap<String, String>();
    static {
        BASE32_ENCODE_MAP.put("00000", "0");
        BASE32_ENCODE_MAP.put("00001", "1");
        BASE32_ENCODE_MAP.put("00010", "2");
        BASE32_ENCODE_MAP.put("00011", "3");
        BASE32_ENCODE_MAP.put("00100", "4");
        BASE32_ENCODE_MAP.put("00101", "5");
        BASE32_ENCODE_MAP.put("00110", "6");
        BASE32_ENCODE_MAP.put("00111", "7");
        BASE32_ENCODE_MAP.put("01000", "8");
        BASE32_ENCODE_MAP.put("01001", "9");
        BASE32_ENCODE_MAP.put("01010", "b");
        BASE32_ENCODE_MAP.put("01011", "c");
        BASE32_ENCODE_MAP.put("01100", "d");
        BASE32_ENCODE_MAP.put("01101", "e");
        BASE32_ENCODE_MAP.put("01110", "f");
        BASE32_ENCODE_MAP.put("01111", "g");
        BASE32_ENCODE_MAP.put("10000", "h");
        BASE32_ENCODE_MAP.put("10001", "j");
        BASE32_ENCODE_MAP.put("10010", "k");
        BASE32_ENCODE_MAP.put("10011", "m");
        BASE32_ENCODE_MAP.put("10100", "n");
        BASE32_ENCODE_MAP.put("10101", "p");
        BASE32_ENCODE_MAP.put("10110", "q");
        BASE32_ENCODE_MAP.put("10111", "r");
        BASE32_ENCODE_MAP.put("11000", "s");
        BASE32_ENCODE_MAP.put("11001", "t");
        BASE32_ENCODE_MAP.put("11010", "u");
        BASE32_ENCODE_MAP.put("11011", "v");
        BASE32_ENCODE_MAP.put("11100", "w");
        BASE32_ENCODE_MAP.put("11101", "x");
        BASE32_ENCODE_MAP.put("11110", "y");
        BASE32_ENCODE_MAP.put("11111", "z");
    }

    /**
     * Base32 decode table
     */
    private static final Map<Character, String> BASE32_DECODE_MAP = new HashMap<Character, String>();
    static {
        BASE32_DECODE_MAP.put('0', "00000");
        BASE32_DECODE_MAP.put('1', "00001");
        BASE32_DECODE_MAP.put('2', "00010");
        BASE32_DECODE_MAP.put('3', "00011");
        BASE32_DECODE_MAP.put('4', "00100");
        BASE32_DECODE_MAP.put('5', "00101");
        BASE32_DECODE_MAP.put('6', "00110");
        BASE32_DECODE_MAP.put('7', "00111");
        BASE32_DECODE_MAP.put('8', "01000");
        BASE32_DECODE_MAP.put('9', "01001");
        BASE32_DECODE_MAP.put('b', "01010");
        BASE32_DECODE_MAP.put('c', "01011");
        BASE32_DECODE_MAP.put('d', "01100");
        BASE32_DECODE_MAP.put('e', "01101");
        BASE32_DECODE_MAP.put('f', "01110");
        BASE32_DECODE_MAP.put('g', "01111");
        BASE32_DECODE_MAP.put('h', "10000");
        BASE32_DECODE_MAP.put('j', "10001");
        BASE32_DECODE_MAP.put('k', "10010");
        BASE32_DECODE_MAP.put('m', "10011");
        BASE32_DECODE_MAP.put('n', "10100");
        BASE32_DECODE_MAP.put('p', "10101");
        BASE32_DECODE_MAP.put('q', "10110");
        BASE32_DECODE_MAP.put('r', "10111");
        BASE32_DECODE_MAP.put('s', "11000");
        BASE32_DECODE_MAP.put('t', "11001");
        BASE32_DECODE_MAP.put('u', "11010");
        BASE32_DECODE_MAP.put('v', "11011");
        BASE32_DECODE_MAP.put('w', "11100");
        BASE32_DECODE_MAP.put('x', "11101");
        BASE32_DECODE_MAP.put('y', "11110");
        BASE32_DECODE_MAP.put('z', "11111");
    }

    /**
     * encode <br>
     * 
     * @param lat
     *            latitude
     * @param lon
     *            longitud
     * @return
     */
    public static String encode(
                    double lat,
                    double lon) {
        return encode(lat, lon, EncodeLength.LENGTH_8);
    }

    public static String encode(
                    double lat,
                    double lon,
                    EncodeLength length) {
        String latSplit = split(lat, LAT_MIN, LAT_MAX, (length.value * 5) / 2);
        String lonSplit = split(lon, LON_MIN, LON_MAX, (length.value * 5) / 2);
        StringBuilder result = new StringBuilder();
        int index = 0;
        while (index < latSplit.length()) {
            result.append(latSplit.charAt(index)).append(lonSplit.charAt(index++));
        }
        return base32encode(result.toString());
    }

    /**
     * split a number to length in min to max <br>
     * 
     * @param min
     * @param max
     * @param length
     * @return
     */
    private static String split(
                    final double num,
                    final double min,
                    final double max,
                    int length) {

        if (num < min || num > max) {
            throw new IllegalStateException("Error range, num: " + num + " is not in [" + min + ", " + max + "].");
        }

        double tempMin = min;
        double tempMax = max;

        StringBuilder result = new StringBuilder();
        while (length-- > 0) {
            double middle = (tempMin + tempMax) / 2;
            if (num > middle) {
                result.append("1");
                tempMin = middle;
            } else {
                result.append("0");
                tempMax = middle;
            }
        }
        return result.toString();
    }

    private static String base32encode(
                    String binaryNum) {
        StringBuilder result = new StringBuilder();
        int index = 0;
        while (index < binaryNum.length()) {
            String binaryItem = binaryNum.substring(index, index + 5);
            result.append(BASE32_ENCODE_MAP.get(binaryItem));
            index = index + 5;
        }
        return result.toString();
    }

    private static String base32decode(
                    String base32String) {
        StringBuilder result = new StringBuilder();
        int index = 0;
        while (index < base32String.length()) {
            result.append(BASE32_DECODE_MAP.get(base32String.charAt(index++)));
        }
        return result.toString();
    }
}
