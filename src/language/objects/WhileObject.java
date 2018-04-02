package language.objects;

import java.util.ArrayList;

public class WhileObject {
    public final LogicalExpressionObject expression;
    public final ArrayList<token.Token> body;

    public WhileObject(LogicalExpressionObject expression, ArrayList<token.Token> body){
        this.expression = expression;
        this.body = body;
    }
}
