package utils;

/**
 * String Utilities
 */
public class MyStringUtils {
    /**
     * Rotates a string 90 degreese counter clockwise
     * @param src string to rotate
     * @return rotated string
     */
    public static String rotateCounterClockwise(String src) {
        /// Don't judge me. This didn't end up having a huge performance cost
        return rotateClockwise(rotateClockwise(rotateClockwise(src)));
    }

    /**
     * Rotates a string 90 degrees clockwise
     * @param src string to rotate
     * @return rotated string
     */
    public static String rotateClockwise(String src) {
        StringBuilder sb = new StringBuilder();
        String[] rows = src.split("\n");
        int height = 0;
        boolean hasWritten;
        do {
            hasWritten = false;
            StringBuilder line = new StringBuilder(rows.length);
            for (String row : rows) {
                if (height < row.length()) {
                    line.append(row.charAt(height));
                    hasWritten = true;
                } else {
                    line.append(' ');
                }
            }
            if(hasWritten) {
                sb.append(line.reverse());
                sb.append('\n');
            }
            height++;
        } while(hasWritten);
        return sb.toString();
    }

    /**
     * returns the character repeated the given number of times
     * @param ch character to repeat
     * @param length how many times to repeat
     * @return repeated string
     */
    public static String repeatChar(char ch, int length) {
        StringBuilder sb = new StringBuilder(Math.max(0,length));
        for (int i = 0; i < length; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }

    /**
     * returns the character repeated the to fit the provided length with the end characters matching 'cap'
     * Example return is "+-------+" where cap = '+' and ch = '-'
     * @param ch character to repeate
     * @param length how long the string should be
     * @param cap cap
     * @return formatted string
     */
    public static String repeatChar(char ch, int length, char cap) {
        boolean canFitCaps = length > 1;
        int bodyLength = canFitCaps ? length -2 : length;

        StringBuilder sb = new StringBuilder(Math.max(0,length));

        if(canFitCaps) {
            sb.append(cap);
        }
        for (int i = 0; i < bodyLength; i++) {
            sb.append(ch);
        }
        if(canFitCaps) {
            sb.append(cap);
        }
        return sb.toString();
    }
}
