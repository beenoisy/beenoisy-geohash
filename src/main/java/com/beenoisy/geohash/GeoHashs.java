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
    private static final Map<String, String> BASE32_MAP = new HashMap<String, String>();
    static {
        BASE32_MAP.put("00000", "0");
        BASE32_MAP.put("00001", "1");
        BASE32_MAP.put("00010", "2");
        BASE32_MAP.put("00011", "3");
        BASE32_MAP.put("00100", "4");
        BASE32_MAP.put("00101", "5");
        BASE32_MAP.put("00110", "6");
        BASE32_MAP.put("00111", "7");
        BASE32_MAP.put("01000", "8");
        BASE32_MAP.put("01001", "9");
        BASE32_MAP.put("01010", "b");
        BASE32_MAP.put("01011", "c");
        BASE32_MAP.put("01100", "d");
        BASE32_MAP.put("01101", "e");
        BASE32_MAP.put("01110", "f");
        BASE32_MAP.put("01111", "g");
        BASE32_MAP.put("10000", "h");
        BASE32_MAP.put("10001", "j");
        BASE32_MAP.put("10010", "k");
        BASE32_MAP.put("10011", "m");
        BASE32_MAP.put("10100", "n");
        BASE32_MAP.put("10101", "p");
        BASE32_MAP.put("10110", "q");
        BASE32_MAP.put("10111", "r");
        BASE32_MAP.put("11000", "s");
        BASE32_MAP.put("11001", "t");
        BASE32_MAP.put("11010", "u");
        BASE32_MAP.put("11011", "v");
        BASE32_MAP.put("11100", "w");
        BASE32_MAP.put("11101", "x");
        BASE32_MAP.put("11110", "y");
        BASE32_MAP.put("11111", "z");
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
        return base32(result.toString());
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

    private static String base32(
                    String binaryNum) {
        StringBuilder result = new StringBuilder();
        int index = 0;
        while (index < binaryNum.length()) {
            String binaryItem = binaryNum.substring(index, index + 5);
            result.append(BASE32_MAP.get(binaryItem));
            index = index + 5;
        }
        return result.toString();
    }
}
