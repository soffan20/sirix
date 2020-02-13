package org.sirix.service.json;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonNumber {
    public static Number stringToNumber(String stringValue) {
        Number number;
        //complexity is 1
        if (stringValue.contains(".")) {
            //complexity is 2
            if (stringValue.contains("E") || stringValue.contains("e")) {
                //complexity is 3
                //complexity is 4
                try {
                    number = Float.valueOf(stringValue);
                } catch (final NumberFormatException eeee) {
                    //complexity is 5
                    try {
                        number = Double.valueOf(stringValue);
                    } catch (final NumberFormatException eeeee) {
                        //complexity is 6
                        throw new IllegalStateException(eeeee);
                    }
                }
            } else {
                try {
                    number = new BigDecimal(stringValue);
                } catch (final NumberFormatException eeeeee) {
                    //complexity is 7
                    throw new IllegalStateException(eeeeee);
                }
            }
        } else {
            try {
                number = Integer.valueOf(stringValue);
            } catch (final NumberFormatException e) {
                //complexity is 8
                try {
                    number = Long.valueOf(stringValue);
                } catch (final NumberFormatException ee) {
                    //complexity is 9
                    try {
                        number = new BigInteger(stringValue);
                    } catch (final NumberFormatException eee) {
                        //complexity is 10
                        throw new IllegalStateException(eee);
                    }
                }
            }
        }
        return number;
    }
}
