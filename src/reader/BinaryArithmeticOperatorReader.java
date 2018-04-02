package reader;

import token.Token;

public class BinaryArithmeticOperatorReader implements IReader {
    private String[] binaryOperators = { ":=", "+", "-", "*", "/", "%", "^", "="  };

    public Token tryReadToken(String input) {
    	if (input.isEmpty())
    		return null;
        for (String operator : binaryOperators)
            if (input.startsWith(operator))
                return new Token("bao", String.valueOf(operator), operator);
        return null;
    }
}
