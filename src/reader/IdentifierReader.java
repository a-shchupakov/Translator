package reader;

import token.Token;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdentifierReader implements IReader {
    public Token tryReadToken(String input){
        String regex = "^[_a-zA-Z][_a-zA-Z0-9]*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        if (m.find()){
            return new Token("id", m.group());
        }
        return null;
    }
}
