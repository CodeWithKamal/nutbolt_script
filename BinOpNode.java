import java.lang.reflect.Field;

public class BinOpNode {
    public Object left_node;
    public Token op_tok;
    public Object right_node;

    public Position pos_start;
    public Position pos_end;

    public BinOpNode(Object left_node, Token op_tok, Object right_node) {
        this.left_node = left_node;
        this.op_tok = op_tok;
        this.right_node = right_node;

        try {
            Field ps = this.left_node.getClass().getDeclaredField("pos_start");
            ps.setAccessible(true);
            Field pe = this.right_node.getClass().getDeclaredField("pos_end");
            pe.setAccessible(true);
            this.pos_start = (Position) ps.get(this.left_node);
            this.pos_end = (Position) pe.get(this.right_node);
        } catch(Exception e){
            System.err.print("Error<BinOpNode>: ");
            System.err.print(e);
        }
    }

    public String toString() {
        return ("(" + left_node.toString() + ", " + op_tok.toString() + ", " + right_node.toString() + ")");
    }
}