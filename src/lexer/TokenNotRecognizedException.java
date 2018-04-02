package lexer;

public class TokenNotRecognizedException extends RuntimeException {
    public TokenNotRecognizedException(){
        super();
    }
    public TokenNotRecognizedException(String message) {
        super(message);
    }
}
