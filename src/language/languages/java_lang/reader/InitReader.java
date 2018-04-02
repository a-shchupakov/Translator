package language.languages.java_lang.reader;

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

    public InitReader(){
        lexer = new Lexer();

        lexer.register(new WhitespaceReader());
        lexer.register(new IdentifierReader());
        lexer.register(new SpecialSymbolReader());

        typeMapping = new HashMap<>();
        typeMapping.put("int", "int");
        typeMapping.put("double", "double");
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

        int spaceIndex = -1;

        for (int i = 0; i < tokens.length; i++)
            if (tokens[i].getType().equals("ws")) {
                spaceIndex = i;
                break;
            }

        if (spaceIndex == -1)
            return null;

        String variableName, variableType;
        try {
            variableType = tokens[spaceIndex - 1].getText();
            variableName = tokens[spaceIndex + 1].getText();
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }

        InitObject io = new InitObject(variableName, translateUsingMap(typeMapping, variableType));
        String result = mergeTokens(tokens, 0, spaceIndex + 3);
        return new Token("initObject", result, io);
    }
}
