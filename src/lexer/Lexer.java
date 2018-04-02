package lexer;

import reader.IReader;
import token.Token;

import java.util.ArrayList;
import java.util.Collections;

public class Lexer {
    private ArrayList<IReader> readers;

    public Lexer(){
        readers = new ArrayList<>();
    }

    public Lexer(IReader ... readers){
        this();
        Collections.addAll(this.readers, readers);
    }

    public void register(IReader reader){
        readers.add(reader);
    }

    public void register(IReader ... readers){
        Collections.addAll(this.readers, readers);
    }

    public Token[] tokenize(String input){
        return tokenize(input, false);
    }

    public Token[] tokenize(String input, Boolean ignore) throws TokenNotRecognizedException{
        ArrayList<Token> result = new ArrayList<>();
        int len = input.length();
        for (int i = 0; i < len; i++){
            Token token = analyzePart(input.substring(i));
            if (token != null){
                int step = token.getText().length();
                i += step - 1;
                result.add(token);
            }
            else {
                if (ignore)
                    break;
                throw new TokenNotRecognizedException("Unrecognized token (index: " + i + ")");
            }
        }
        Token[] tokens = new Token[result.size()];
        return result.toArray(tokens);
    }

    private Token analyzePart(String input){
        Token maxToken = null;
        for (IReader reader : readers){
            Token token = reader.tryReadToken(input);
            if (token != null)
                if (maxToken == null || token.getText().length() > maxToken.getText().length())
                    maxToken = token;
        }
        return maxToken;
    }
}