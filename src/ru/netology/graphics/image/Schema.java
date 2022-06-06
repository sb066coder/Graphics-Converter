package ru.netology.graphics.image;

public class Schema implements TextColorSchema {

    private char[] chars;

    public Schema(String gradient) {
        chars = gradient.toCharArray();
    }

    @Override
    public char convert(int color) {
        return chars[color / (int) Math.ceil(1.0 * 256 / chars.length)];
    }
}