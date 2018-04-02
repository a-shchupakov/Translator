package language.languages;

import language.objects.*;
import lexer.Lexer;
import token.Token;

import java.util.HashMap;

public abstract class Language {
    protected String lineSeparator;
    protected String blockBeginning;
    protected String blockEnding;
    protected String endOfStatement;
    protected String ifConstruction;
    protected String whileConstruction;
    protected String tabulation = ""; //TODO: removed (?)
    protected String carriageReturnLineFeed = "\r\n";
    protected String equalSign;
    protected Lexer languageLexer;
    protected HashMap<String, String> typeMapping;
    protected HashMap<String, String> logicalOperatorsMapping;

    public abstract Token[] translateToObjects(String code);
    public abstract String translateFromObjects(Token[] objects);

    protected String translateIfObject(IfObject ifObject) {
        StringBuilder ifBody = new StringBuilder();
        for (token.Token token: ifObject.ifBody){ // Все содержимое тела тоже переводим в текущий язык
            ifBody.append(tabulation);
            ifBody.append(translateToken(token));
        }

        StringBuilder elseBody = new StringBuilder();
        for (token.Token token: ifObject.elseBody){ // Все содержимое тела тоже переводим в текущий язык
            elseBody.append(tabulation);
            elseBody.append(translateToken(token));
        }

        return String.format(ifConstruction,
                translateExpressionObject(logicalOperatorsMapping, ifObject.expression),
                ifBody.toString(),
                elseBody.toString()) + endOfStatement;
    }

    protected String translateWhileObject(WhileObject whileObject) {
        StringBuilder body = new StringBuilder();
        for (token.Token token: whileObject.body){  // Все содержимое тела тоже переводим в текущий язык
            body.append(tabulation);
            body = body.append(translateToken(token));
        }

        return String.format(whileConstruction,
                translateExpressionObject(logicalOperatorsMapping, whileObject.expression),
                body.toString()) + endOfStatement;
    }

    protected String translateAssignmentObject(AssignmentObject assignmentObject){
        return assignmentObject.variableName + equalSign + assignmentObject.variableValue + lineSeparator + carriageReturnLineFeed;
    }

    protected abstract String translateInitObject(InitObject initObject);

    final public String translateToken(token.Token token){
        switch (token.getType()){
            case "ifObject":
                return translateIfObject((IfObject) token.getValue());
            case "whileObject":
                return translateWhileObject((WhileObject) token.getValue());
            case "assignmentObject":
                return translateAssignmentObject((AssignmentObject) token.getValue());
            case "initObject":
                return translateInitObject((InitObject) token.getValue());
            case "expressionObject":
                return translateExpressionObject(logicalOperatorsMapping, (LogicalExpressionObject) token.getValue());
            case "ws":
                return (String) token.getValue();
            default:
                return "";
        }
    }

    final public String translateExpressionObject(HashMap<String, String> mapping, LogicalExpressionObject expression){
        StringBuilder sb = new StringBuilder(expression.expression);
        for(HashMap.Entry<String, String> val : mapping.entrySet())
            replaceAll(sb, val.getKey(), val.getValue());
        return sb.toString();
    }

    private static void replaceAll(StringBuilder builder, String from, String to)
    {
        int index = builder.indexOf(from);
        while (index != -1)
        {
            builder.replace(index, index + from.length(), to);
            index += to.length(); // Move to the end of the replacement
            index = builder.indexOf(from, index);
        }
    }
}
