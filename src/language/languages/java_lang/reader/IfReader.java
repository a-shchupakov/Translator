package language.languages.java_lang.reader;

import language.objects.AdvancedReader;
import language.objects.IfObject;
import language.objects.LogicalExpressionObject;
import lexer.Lexer;
import lexer.TokenNotRecognizedException;
import reader.BracketsReader;
import reader.KeyWordReader;
import token.Token;
import reader.WhitespaceReader;

import java.util.ArrayList;
import java.util.Collections;


public class IfReader extends AdvancedReader {
    private static IfReader ourInstance = new IfReader();
    private Lexer lexer;
    private Lexer advancedLexer;
    private boolean added = false;

    public static IfReader getInstance() {
        return ourInstance;
    }

    private IfReader(){
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
            if ((!tokens[0].getText().equals("if")) || (!tokens[1].getType().equals("ws"))
                    || (!tokens[2].getType().equals("expressionObject")) || (!tokens[3].getType().equals("ws"))
                    || (!tokens[4].getType().equals("block")))
                return null;
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }

        int elseBlockIndex = -1;
        for (int i = 4; i < tokens.length; i++){
            if (tokens[i].getText().equals("else")) {
                elseBlockIndex = i;
                break;
            }
        }

        ArrayList<Token> elseBody = new ArrayList<>();
        if (elseBlockIndex != -1){
            Collections.addAll(elseBody, advancedLexer.tokenize((String) tokens[elseBlockIndex + 2].getValue(), false)); //TODO: мб надо true
        }
        ArrayList<Token> ifBody = new ArrayList<>();
        Collections.addAll(ifBody, advancedLexer.tokenize((String) tokens[4].getValue(), false));//TODO: мб надо true


        IfObject io = new IfObject((LogicalExpressionObject) tokens[2].getValue(), ifBody, elseBody);
        String result = (elseBlockIndex != -1) ? mergeTokens(tokens, 0, elseBlockIndex + 3) : mergeTokens(tokens, 0, 5);

        return new Token("ifObject", result, io);
    }
}
