package main;

import language.Translator;
import language.languages.java_lang.JavaLanguage;
import language.languages.pascal_lang.PascalLanguage;

public class Main {
    public static void main(String[] args) {
        JavaLanguage java = new JavaLanguage();
        PascalLanguage pascal = new PascalLanguage();

        Translator.translate("D:\\IT\\ООП\\практика\\testTranslator\\java-pascal\\in.txt", java, pascal, "D:\\IT\\ООП\\практика\\testTranslator\\java-pascal\\out.txt");
        Translator.translate("D:\\IT\\ООП\\практика\\testTranslator\\java-pascal\\out.txt", pascal, java, "D:\\IT\\ООП\\практика\\testTranslator\\java-pascal\\back.txt");

        Translator.translate("D:\\IT\\ООП\\практика\\testTranslator\\pascal-java\\in.txt", pascal, java, "D:\\IT\\ООП\\практика\\testTranslator\\pascal-java\\out.txt");
        Translator.translate("D:\\IT\\ООП\\практика\\testTranslator\\pascal-java\\out.txt", java, pascal, "D:\\IT\\ООП\\практика\\testTranslator\\pascal-java\\back.txt");

        Translator.translate("D:\\IT\\ООП\\практика\\testTranslator\\java-java\\in.txt", java, java, "D:\\IT\\ООП\\практика\\testTranslator\\java-java\\out.txt");
        Translator.translate("D:\\IT\\ООП\\практика\\testTranslator\\java-java\\in.txt", java, java, "D:\\IT\\ООП\\практика\\testTranslator\\java-java\\back.txt");

    }
}
