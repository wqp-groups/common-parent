package com.wqp.common.util.common;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 十六进制工具类
 */
public final class HexUtil extends Hex {
//    private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


    public static String byteToString(byte[] data, char delimit) {
        int len = data.length;
        char[] out = new char[(len << 1) + len];
        for(int v = 0, i = 0; i < len; ++i) {
            out[v++] = DIGITS_UPPER[(240 & data[i]) >>> 4];
            out[v++] = DIGITS_UPPER[15 & data[i]];
            out[v++] = delimit;
        }
        return out.length > 0 ? new String(ArrayUtils.remove(out, out.length - 1)) : new String(out);
    }

    private HexUtil(){
    }

}
