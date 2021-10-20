import java.lang.reflect.Field;

public class UnaryOpNode {
    public Token op_tok;
    public Object node;

    public Position pos_start;
    public Position pos_end;

    public UnaryOpNode(Token op_tok, Object node) {
        this.op_tok = op_tok;
        this.node = node;

        this.pos_start = this.op_tok.pos_start;
        try {
            Field pe = this.node.getClass().getDeclaredField("pos_end");
            pe.setAccessible(true);
            this.pos_end = (Position) pe.get(this.node);
        } catch (Exception e) {
            System.err.print("Error<UnaryOpNode>: ");
            System.err.print(e);
        }
    }

    public String toString() {
        return ("(" + this.op_tok.toString() + ", " + this.node.toString() + ")");
    }
}
