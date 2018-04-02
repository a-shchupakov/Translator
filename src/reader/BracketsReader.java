package reader;

import token.Token;

public class BracketsReader implements IReader {
    private final char[] brackets = new char[] {'(', ')', '{', '}', '[', ']', '<', '>' };

    public Token tryReadToken(String input) {
        for (char bracket : brackets)
            if (input.charAt(0) == bracket)
                return new Token("bracket", String.valueOf(bracket), bracket);
        return null;
    }
}
