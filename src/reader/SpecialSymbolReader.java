package reader;

import token.Token;

public class SpecialSymbolReader implements IReader {
    private final char[] specialSymbols = new char[] { ',', '.', ';', ':', '?' };

    public Token tryReadToken(String input) {
        for (char symbol : specialSymbols)
            if (input.charAt(0) == symbol)
                return new Token("ss", String.valueOf(symbol), symbol);
        return null;
    }
}
