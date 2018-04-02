package reader;

import token.Token;

public class DoubleReader implements IReader {
    public Token tryReadToken(String input) {
        if (!Character.isDigit(input.charAt(0)) && input.charAt(0) != '.')
            return null;
        int dotIndex = input.indexOf('.');
        if (dotIndex == -1)
            return null;
        String leftSide = tryReadPart(input.substring(0, dotIndex));
        String rightSide = tryReadPart(input.substring(dotIndex + 1));
        if (leftSide.isEmpty() && rightSide.isEmpty())
            return null;
        String text = leftSide + '.' + rightSide;
        int newDotIndex  = text.indexOf('.');
        if (dotIndex != newDotIndex)
            return null;
        return new Token("double", text, Double.parseDouble(text));
    }

    private String tryReadPart(String input){
        int i = 0;
        int len = input.length();
        while(i < len && Character.isDigit(input.charAt(i))){
            i++;
        }
        return input.substring(0, i);
    }
}
