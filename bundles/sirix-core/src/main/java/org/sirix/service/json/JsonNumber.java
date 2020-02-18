package org.sirix.service.json;

import org.sirix.utils.Coverage;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonNumber { //24 lines som coverage bryr sig om 10 grona 14 roda => 41% cov
    public static Coverage cov = new Coverage();
    public static Number stringToNumber(String stringValue) {
        var fun = cov.inFunction("JsonNumber.stringToNumber", 24);
        Number number;
        fun.inBranch(0, 2);
        if (stringValue.contains(".")) {
            fun.inBranch(1, 1);
            if (stringValue.contains("E") || stringValue.contains("e")) {
                fun.inBranch(2, 2);
                try {
                    number = Float.valueOf(stringValue);
                } catch (final NumberFormatException eeee) {
                    fun.inBranch(3, 3);
                    try {
                        number = Double.valueOf(stringValue);
                    } catch (final NumberFormatException eeeee) {
                        fun.inBranch(4, 2);
                        throw new IllegalStateException(eeeee);
                    }
                }
            } else {
                fun.inBranch(5, 2);
                try {
                    number = new BigDecimal(stringValue);
                } catch (final NumberFormatException eeeeee) {
                    fun.inBranch(6, 2);
                    throw new IllegalStateException(eeeeee);
                }
            }
        } else {
            fun.inBranch(7, 2);
            try {
                number = Integer.valueOf(stringValue);
            } catch (final NumberFormatException e) {
                fun.inBranch(8, 3);
                try {
                    number = Long.valueOf(stringValue);
                } catch (final NumberFormatException ee) {
                    fun.inBranch(9, 3);
                    try {
                        number = new BigInteger(stringValue);
                    } catch (final NumberFormatException eee) {
                        fun.inBranch(10, 2);
                        throw new IllegalStateException(eee);
                    }
                }
            }
        }
        return number;
    }
}
