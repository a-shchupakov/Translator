package language.languages.pascal_lang.reader;

import language.objects.AdvancedReader;
import language.objects.InitObject;
import lexer.Lexer;
import lexer.TokenNotRecognizedException;
import reader.IdentifierReader;
import reader.SpecialSymbolReader;
import reader.WhitespaceReader;
import token.Token;

import java.util.HashMap;

public class InitReader extends AdvancedReader {
    private static Lexer lexer;
    private static String lineSep = ";";

    public InitReader(){
        lexer = new Lexer();

        lexer.register(new WhitespaceReader());
        lexer.register(new IdentifierReader());
        lexer.register(new SpecialSymbolReader());

        typeMapping = new HashMap<>();
        typeMapping.put("integer", "int");
        typeMapping.put("real", "double");
        typeMapping.put("String", "str");
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

        if (!tokens[0].getText().equals("var"))
            return null;

        int colonIndex = -1;

        for (int i = 0; i < tokens.length; i++)
            if (tokens[i].getText().equals(":")) {
                colonIndex = i;
                break;
            }

        if (colonIndex == -1)
            return null;

        int lineSepIndex = -1;

        for (int i = 0; i < tokens.length; i++)
            if (tokens[i].getText().equals(lineSep)) {
                lineSepIndex = i;
                break;
            }

        if (lineSepIndex == -1)
            return null;


        String variableName, variableType;
        try {
            variableName = tokens[colonIndex - 1].getText();
            variableType = tokens[colonIndex + 2].getText();
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }
        InitObject io = new InitObject(variableName, translateUsingMap(typeMapping, variableType));
        String result = mergeTokens(tokens, 0, lineSepIndex + 1);
        return new Token("initObject", result, io);
    }
}
