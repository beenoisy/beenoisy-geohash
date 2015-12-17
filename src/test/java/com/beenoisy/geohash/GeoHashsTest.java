/*
 * beenoisy-geohash - com.beenoisy.geohash.GeoHashsTest.java
 * 2015年12月17日:下午6:02:06
 * ziyi.wang | wangziyi@didichuxing.com
 */
package com.beenoisy.geohash;

import com.beenoisy.geohash.GeoHashs.EncodeLength;

import junit.framework.TestCase;

/**
 * unit test <br>
 * 2015年12月17日:下午6:02:06
 * 
 * @author jacks808@163.com<br>
 */
public class GeoHashsTest extends TestCase {

    /**
     * Test method for {@link com.beenoisy.geohash.GeoHashs#encode(float, float)}.
     */
    public void testEncodeFloatFloat() {
        String encode;
        encode = GeoHashs.encode(116.3906, 39.92324);
        assertEquals("wx4g0ec1", encode);
    }

    /**
     * Test method for {@link com.beenoisy.geohash.GeoHashs#encode(float, float, int)}.
     */
    public void testEncodeFloatFloatInt() {
        String encode;
        encode = GeoHashs.encode(116.3906, 39.92324, EncodeLength.LENGTH_2);
        assertEquals("wx", encode);
        encode = GeoHashs.encode(116.3906, 39.92324, EncodeLength.LENGTH_4);
        assertEquals("wx4g", encode);
        encode = GeoHashs.encode(116.3906, 39.92324, EncodeLength.LENGTH_6);
        assertEquals("wx4g0e", encode);
        encode = GeoHashs.encode(116.3906, 39.92324, EncodeLength.LENGTH_8);
        assertEquals("wx4g0ec1", encode);
        encode = GeoHashs.encode(116.3906, 39.92324, EncodeLength.LENGTH_10);
        assertEquals("wx4g0ec19x", encode);
    }

}
