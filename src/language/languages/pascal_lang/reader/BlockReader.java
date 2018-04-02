package language.languages.pascal_lang.reader;

import javafx.util.Pair;
import language.objects.AdvancedReader;
import token.Token;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlockReader extends AdvancedReader {
    private static final String blockBeginning = "begin";
    private static final String blockEnding = "end";
    @Override
    public Token tryReadToken(String input) {
        int i = 0;
        String tempInput = input;
        ArrayList<Pair<String, Integer>> matches = new ArrayList<>();
//        while (i < input.length()){
//            int beginIndex = tempInput.indexOf(blockBeginning);
//            int endIndex = tempInput.lastIndexOf(blockEnding);
//
//            if (beginIndex == -1 && endIndex == -1)
//                break;
//
//            if ((endIndex == -1) || ((beginIndex < endIndex) && (beginIndex != -1))){
//                matches.add(new Pair<>(blockBeginning, beginIndex + i));
//                tempInput = tempInput.substring(beginIndex + blockBeginning.length());
//                i += blockBeginning.length() + beginIndex;
//            }
//
//            else if ((beginIndex == -1) || ((endIndex < beginIndex) && (endIndex != -1))){
//                matches.add(new Pair<>(blockEnding, endIndex + i));
//                tempInput = tempInput.substring(0, endIndex);
//                //i += blockEnding.length();
//            }
//        }

        String initBlockRegex = "\\bbegin|end\\b";
        Pattern p1 = Pattern.compile(initBlockRegex, Pattern.DOTALL);
        Matcher m1 = p1.matcher(input);

        while (m1.find()) {
            matches.add(new Pair<>(m1.group(), m1.start()));
        }

        if (matches.isEmpty())
            return null;

        if (matches.get(0).getValue() != 0)
            return null;

        int opened = 0;
        int closing = -1;
        for (Pair<String, Integer> match: matches) {
            if (match.getKey().equals(blockBeginning))
                opened += 1;
            else if (match.getKey().equals(blockEnding))
                opened -= 1;

            if (opened == 0) {
                closing = match.getValue();
                break;
            }
        }
        if (closing == -1)
            return null;

        String result = input.substring(blockBeginning.length(), closing);
        String matched = input.substring(0, closing + blockEnding.length());

        return new Token("block" , matched, result);
    }
}
