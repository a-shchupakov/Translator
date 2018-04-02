package language.languages.pascal_lang;

import language.languages.Language;
import language.languages.pascal_lang.reader.*;
import language.objects.*;
import lexer.Lexer;
import reader.WhitespaceReader;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import token.Token;

public class PascalLanguage extends Language {
    public PascalLanguage() {
        lineSeparator = ";";
        blockBeginning = "begin";
        blockEnding = "end";
        equalSign = ":=";
        endOfStatement = "";

        ifConstruction = "if (%s) then " + blockBeginning + carriageReturnLineFeed +
                "%s" + blockEnding + carriageReturnLineFeed +
                "else " + blockBeginning + carriageReturnLineFeed +
                "%s" + blockEnding + lineSeparator + carriageReturnLineFeed;

        whileConstruction = "while (%s) do"+ carriageReturnLineFeed + blockBeginning + carriageReturnLineFeed +
                "%s" + blockEnding + lineSeparator + carriageReturnLineFeed;

        typeMapping = new HashMap<>();
        typeMapping.put("int", "integer");
        typeMapping.put("double", "real");

        logicalOperatorsMapping = new HashMap<>();
        logicalOperatorsMapping.put("and", "and");
        logicalOperatorsMapping.put("or", "or");
        logicalOperatorsMapping.put("not", "not");
        logicalOperatorsMapping.put("==", "=");
        logicalOperatorsMapping.put("!=", "<>");


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
        String initBlockRegex = String.format("(?<=%s).*(?=%s)", "Program Main;\n", "Begin\n");
        Pattern p1 = Pattern.compile(initBlockRegex, Pattern.DOTALL);
        Matcher m1 = p1.matcher(code);
        String codeBlockRegex = String.format("(?<=%s).*(?=%s)", "Begin\n", "End\\.");
        Pattern p2 = Pattern.compile(codeBlockRegex, Pattern.DOTALL);
        Matcher m2 = p2.matcher(code);

        Token[] initBlock;
        Token[] codeBlock;

        if (m1.find()) {
            initBlock = languageLexer.tokenize(m1.group());
            if (m2.find()){
                codeBlock = languageLexer.tokenize(m2.group());
                return concatenate(initBlock, codeBlock);
            }
        }
        return new Token[0];
    }

    @Override
    public String translateFromObjects(Token[] objects) {
        StringBuilder sb = new StringBuilder();
        sb.append("Program Main;");
        sb.append(carriageReturnLineFeed);

        for (Token token: objects)
            if (token.getType().equals("initObject"))
                sb.append(translateToken(token));

        sb.append("Begin");
        sb.append(carriageReturnLineFeed);
        for (Token token: objects)
            if (!token.getType().equals("initObject")) {
                sb.append(translateToken(token));
            }
        sb.append("End.");
        sb.append(carriageReturnLineFeed);
        return sb.toString();
    }

    @Override
    protected String translateInitObject(InitObject initObject) {
        return "var " + initObject.variableName + ": " + typeMapping.get(initObject.variableType) + lineSeparator + carriageReturnLineFeed;
    }

    private <T> T[] concatenate(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }
}
