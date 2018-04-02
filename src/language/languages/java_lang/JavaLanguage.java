package language.languages.java_lang;

import language.languages.Language;
import language.languages.java_lang.reader.*;
import language.objects.*;
import lexer.Lexer;
import reader.WhitespaceReader;
import token.Token;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaLanguage extends Language {
    private String wrapperFormat;

    public JavaLanguage() {
        lineSeparator = ";";
        blockBeginning = "{";
        blockEnding = "}";
        equalSign = "=";
        endOfStatement = "";

        ifConstruction = "if (%s) " + blockBeginning + carriageReturnLineFeed +
                            "%s" + blockEnding + carriageReturnLineFeed +
                            "else " + blockBeginning + carriageReturnLineFeed +
                            "%s" + blockEnding + carriageReturnLineFeed;

        whileConstruction = "while (%s) " + blockBeginning + carriageReturnLineFeed +
                                "%s" + blockEnding + carriageReturnLineFeed;

        wrapperFormat = "public class Main {\r\n" +
                             "    public static void main(String[] args) {\r\n" +
                             "    %s    }\r\n" +
                             "}";

        typeMapping = new HashMap<>();
        typeMapping.put("int", "int");
        typeMapping.put("double", "double");
        typeMapping.put("str", "String");

        logicalOperatorsMapping = new HashMap<>();
        logicalOperatorsMapping.put("and", "&&");
        logicalOperatorsMapping.put("or", "||");
        logicalOperatorsMapping.put("not", "!");
        logicalOperatorsMapping.put("==", "==");
        logicalOperatorsMapping.put("!=", "!=");

        languageLexer = new Lexer();
        languageLexer.register(new WhitespaceReader());
        languageLexer.register(new AssignmentReader());
        languageLexer.register(IfReader.getInstance());
        languageLexer.register(WhileReader.getInstance());
        languageLexer.register(new InitReader());
        languageLexer.register(new LogicalExpressionReader());

    }

    @Override
    public Token[] translateToObjects(String code) {
        String regex = String.format("(?<=%s).*(?=%s)", "public class Main \\{\n" +
                        "    public static void main\\(String\\[\\] args\\) \\{\n",
                "\n    \\}\n" +
                        "\\}");

        Pattern p = Pattern.compile(regex, Pattern.DOTALL);
        Matcher m = p.matcher(code);
        if (m.find())
            return languageLexer.tokenize(m.group());
        return new Token[0];
    }

    @Override
    public String translateFromObjects(Token[] objects) {
       StringBuilder sb = new StringBuilder();
       for (Token token: objects)
           sb.append(translateToken(token));
       return String.format(wrapperFormat, sb.toString());
    }

    @Override
    protected String translateInitObject(InitObject initObject) {
        return typeMapping.get(initObject.variableType) + " " + initObject.variableName + lineSeparator + carriageReturnLineFeed;
    }
}
