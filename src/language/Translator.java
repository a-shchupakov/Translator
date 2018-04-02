package language;

import language.languages.Language;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;

public class Translator {
    static public void translate(String path, Language from, Language to, String outPath){
        File code = new File(path);
        File out = new File(outPath);
        String readCode;
        try {
            readCode = new String(Files.readAllBytes(code.toPath()));
        }
        catch (IOException e){ return; }

        readCode = readCode.replace("\r", "");
        String translated = to.translateFromObjects(from.translateToObjects(readCode));

        if (!out.exists() && !out.isDirectory())
            try{
                out.createNewFile();
            }
            catch (IOException e){ return; }

        ArrayList<String> toWrite = new ArrayList<>();
        toWrite.add(translated);

        try {
            Files.write(out.toPath(), toWrite, Charset.forName("UTF-8"));
        }
        catch (IOException e) { }
    }
}
