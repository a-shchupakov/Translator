package language.languages.java_lang.reader;

import language.objects.AdvancedReader;
import token.Token;

public class BlockReader extends AdvancedReader {
    private static final char blockBeginning = '{';
    private static final char blockEnding = '}';
    @Override
    public Token tryReadToken(String input) {
        if (input.charAt(0) != blockBeginning)
            return null;

        int openned = 1;
        int closing = -1;
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == blockBeginning)
                openned += 1;
            else if (input.charAt(i) == blockEnding)
                openned -= 1;

            if (openned == 0) {
                closing = i;
                break;
            }
        }
        if (closing == -1)
            return null;

        return new Token("block" , input.substring(0, closing + 1), input.substring(1, closing));
    }
}
