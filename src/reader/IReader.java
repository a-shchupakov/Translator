package reader;
import token.Token;

public interface IReader {
    Token tryReadToken(String input);
}
