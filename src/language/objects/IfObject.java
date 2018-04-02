package language.objects;

import java.util.ArrayList;

public class IfObject {
    public final LogicalExpressionObject expression;
    public final ArrayList<token.Token> ifBody;
    public final ArrayList<token.Token> elseBody;

    public IfObject(LogicalExpressionObject expression, ArrayList<token.Token> ifBody, ArrayList<token.Token> elseBody){
        this.expression = expression;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }
}
