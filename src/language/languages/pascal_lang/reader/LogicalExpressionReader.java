package language.languages.pascal_lang.reader;

import language.objects.AdvancedReader;
import language.objects.LogicalExpressionObject;
import lexer.Lexer;
import lexer.TokenNotRecognizedException;
import reader.*;
import token.Token;

import java.util.HashMap;

public class LogicalExpressionReader extends AdvancedReader {
    private Lexer lexer;

    public LogicalExpressionReader(){
        lexer = new Lexer();
        lexer.register(new IdentifierReader());
        lexer.register(new BracketsReader());
        lexer.register(new WhitespaceReader());
        lexer.register(new IntReader());
        lexer.register(new DoubleReader());
        lexer.register(new IReader() {
            private String[] logicalOperators = { "and", "or", "not", "=", "<>" };
            @Override
            public Token tryReadToken(String input) {
                for (String operator : logicalOperators)
                    if (input.startsWith(operator))
                        return new Token("logical", operator);
                return null;
            }
        });

        logicalOperatorsMapping = new HashMap<>();
        logicalOperatorsMapping.put("and", "and");
        logicalOperatorsMapping.put("or", "or");
        logicalOperatorsMapping.put("not", "not");
        logicalOperatorsMapping.put("=", "==");
        logicalOperatorsMapping.put("<>", "!=");
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

        try {
            if (!tokens[0].getText().equals("("))
                return null;
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }

        int closingBracketIndex = -1;
        int opened = 1;
        for (int i = 1; i < tokens.length; i++){
            if (tokens[i].getText().equals("("))
                opened += 1;
            else if (tokens[i].getText().equals(")"))
                opened -= 1;
            if (opened == 0){
                closingBracketIndex = i;
                break;
            }
        }

        if (closingBracketIndex == -1)
            return null;

        String result = mergeTokens(tokens, 0, closingBracketIndex + 1);

        LogicalExpressionObject leo = new LogicalExpressionObject(translateUsingMap(logicalOperatorsMapping, mergeTokens(tokens, 1, closingBracketIndex)));
        return new Token("expressionObject", result, leo);
    }
}
