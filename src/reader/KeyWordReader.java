package reader;

import token.Token;

import java.util.ArrayList;
import java.util.Arrays;

public class KeyWordReader implements IReader{
    private final String keyWords =
            "abstract\tcontinue\tfor\tnew\tswitch\n" +
            "assert\tdefault\tgoto\tpackage\tsynchronized\n" +
            "boolean\tbegin\tdo\tif\tprivate\tthis\n" +
            "break\tdouble\timplements\tprotected\tthrow\n" +
            "byte\telse\tend\timport\tpublic\tthrows\nthen\n" +
            "case\tenum\tinstanceof\treturn\ttransient\n" +
            "catch\textends\tint\tshort\ttry\n" +
            "char\tfinal\tinterface\tstatic\tvoid\n" +
            "class\tfinally\tlong\tstrictfp\tvolatile\n" +
            "const*\tfloat\tnative\tsuper\twhile";

    private ArrayList<WordReader> keyWordReaders;

    public KeyWordReader(){
        keyWordReaders = new ArrayList<>();
        GenerateReaders();
    }

    private void GenerateReaders(){
        String[] words = keyWords.split("\\s");
        Arrays.sort(words, (firstStr, secondStr) -> Integer.compare(secondStr.length(), firstStr.length()));
        for (String word : words) {
            keyWordReaders.add(new WordReader(word));
        }
    }

    public Token tryReadToken(String input) {
        for (WordReader wordReader : keyWordReaders){
            Token token = wordReader.tryReadToken(input);
            if (token != null)
                return new Token("kw", token.getText());
        }
        return null;
    }
}
