package reader;

import token.Token;

public class WordReader implements IReader {
    private final String word;
    private boolean ignoreCase = false;

    public WordReader(String word)
    {
        super();
        this.word = word;
    }

    public WordReader(String word, boolean ignoreCase){
        this.word = word.toLowerCase();
        this.ignoreCase = ignoreCase;
    }

    public Token tryReadToken(String input)
    {
        String tempInput = ignoreCase ? input.toLowerCase() : input;
        if (tempInput.startsWith(word))
            return new Token("word", input.substring(0, word.length()));
        return null;
    }
}
