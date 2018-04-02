package reader;

import token.Token;

public class IntReader implements IReader {
    public Token tryReadToken(String input) {
        int i = 0;
        int len = input.length();
        while(i < len && Character.isDigit(input.charAt(i)))
            i++;
        String text = input.substring(0, i);
        Token token = (text.length() == 0) ? null : new Token("int", text, Integer.parseInt(text));
        return token;
    }
}
