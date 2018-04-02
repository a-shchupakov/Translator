package language.objects;

import java.util.HashMap;

import reader.IReader;
import token.Token;

public abstract class AdvancedReader implements IReader {
    protected HashMap<String, String> logicalOperatorsMapping;
    protected HashMap<String, String> typeMapping;

    final protected String translateUsingMap(HashMap<String, String> mapping, String expression){
        StringBuilder sb = new StringBuilder(expression);
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

    protected String mergeTokens(Token[] tokens, int from, int to){
        StringBuilder sb = new StringBuilder();
        for (int i = from; i < to && i < tokens.length; i++){
            sb.append(tokens[i].getText());
        }
        return sb.toString();
    }
}
