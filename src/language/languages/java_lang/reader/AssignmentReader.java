package language.languages.java_lang.reader;

import language.objects.AdvancedReader;
import language.objects.AssignmentObject;
import lexer.Lexer;
import lexer.TokenNotRecognizedException;
import reader.*;
import token.Token;

public class AssignmentReader extends AdvancedReader {
    private static Lexer lexer;
    private static String equalSign = "=";
    static {
        lexer = new Lexer();
        lexer.register(new WhitespaceReader());
        lexer.register(new IdentifierReader());
        lexer.register(new BinaryArithmeticOperatorReader());
        lexer.register(new SpecialSymbolReader());
        lexer.register(new BracketsReader());
        lexer.register(new IntReader());
        lexer.register(new DoubleReader());
    }
    @Override
    public Token tryReadToken(String input) {
        Token[] tokens;
        try {
            tokens = lexer.tokenize(input, true);
        }
        catch (TokenNotRecognizedException e){
            return null;
        }


        int equalIndex = -1;

        for (int i = 0; i < tokens.length; i++)
            if (tokens[i].getText().equals(equalSign)) {
                equalIndex = i;
                break;
            }

        if (equalIndex == -1 || equalIndex != 2)
            return null;

        int lineSeparatorIndex = -1;
        for (int i = equalIndex; i < tokens.length; i++){
            if (tokens[i].getText().equals(";")){
                lineSeparatorIndex = i;
                break;
            }
        }

        String variableName = mergeTokens(tokens, 0, equalIndex);
        String variableValue = mergeTokens(tokens, equalIndex + 1, lineSeparatorIndex);

        String matched = mergeTokens(tokens, 0, lineSeparatorIndex + 1);
        AssignmentObject ao = new AssignmentObject(variableName, variableValue);
        return new Token("assignmentObject", matched, ao);
    }
}
