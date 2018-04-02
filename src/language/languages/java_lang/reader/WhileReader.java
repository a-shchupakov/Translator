package language.languages.java_lang.reader;

import language.objects.AdvancedReader;
import language.objects.LogicalExpressionObject;
import language.objects.WhileObject;
import lexer.TokenNotRecognizedException;
import reader.BracketsReader;
import reader.KeyWordReader;
import token.Token;
import lexer.Lexer;
import reader.WhitespaceReader;

import java.util.ArrayList;
import java.util.Collections;

public class WhileReader extends AdvancedReader {
    private static WhileReader ourInstance = new WhileReader();
    private Lexer lexer;
    private Lexer advancedLexer;
    private boolean added = false;

    public static WhileReader getInstance(){
        return ourInstance;
    }

    private WhileReader(){
        lexer = new Lexer();
        WhitespaceReader wsReader = new WhitespaceReader();
        lexer.register(wsReader);
        lexer.register(new KeyWordReader());
        lexer.register(new BracketsReader());
        lexer.register(new BlockReader());
        AssignmentReader ar = new AssignmentReader();
        LogicalExpressionReader leo = new LogicalExpressionReader();
        InitReader io = new InitReader();
        lexer.register(leo);

        advancedLexer = new Lexer();
        advancedLexer.register(wsReader, ar, io, leo);
    }

    private void addReaders(){
        if (!added){
            advancedLexer.register(IfReader.getInstance(), ourInstance);
            added = true;
        }
    }


    @Override
    public Token tryReadToken(String input) {
        addReaders();
        Token[] tokens;
        try {
            tokens = lexer.tokenize(input, true);
        }
        catch (TokenNotRecognizedException e){
            return null;
        }


        try {
            if ((!tokens[0].getText().equals("while")) || (!tokens[1].getType().equals("ws"))
                    || (!tokens[2].getType().equals("expressionObject")) || (!tokens[3].getType().equals("ws"))
                    || (!tokens[4].getType().equals("block")))
                return null;
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }

        int beginBodyIndex = 4;

        ArrayList<Token> body = new ArrayList<>();
        Collections.addAll(body, advancedLexer.tokenize((String) tokens[beginBodyIndex].getValue(), false)); //TODO: мб надо true

        WhileObject wo = new WhileObject((LogicalExpressionObject) tokens[2].getValue(), body);
        String result = mergeTokens(tokens, 0, beginBodyIndex + 1);

        return new Token("whileObject", result, wo);
    }
}
