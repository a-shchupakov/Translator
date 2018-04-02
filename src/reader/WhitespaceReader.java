package reader;

import token.Token;

public class WhitespaceReader implements IReader
{
    /**
     * Читает с начала строки максимальное количество пробельных символов.
     *
     * @return Возвращает прочитанный префикс строки.
     */
    public Token tryReadToken(String input)
    {
        int i = 0;
        int len = input.length();
        while (i < len && Character.isWhitespace(input.charAt(i)))
            i++;
        String text = input.substring(0, i);
        Token token = (text.length() == 0) ? null : new Token("ws", text);
        return token;
    }
}
