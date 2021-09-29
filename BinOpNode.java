public class BinOpNode {
    public Object left_node;
    public Token op_tok;
    public Object right_node;

    public BinOpNode(Object left_node, Token op_tok, Object right_node) {
        this.left_node = left_node;
        this.op_tok = op_tok;
        this.right_node = right_node;
    }

    public String toString() {
        return ("(" + left_node.toString() + ", " + op_tok.toString() + ", " + right_node.toString() + ")");
    }
}