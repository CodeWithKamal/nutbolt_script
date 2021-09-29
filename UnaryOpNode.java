public class UnaryOpNode {
    public Token op_tok;
    public Object node;

    public UnaryOpNode(Token op_tok, Object node) {
        this.op_tok = op_tok;
        this.node = node;
    }

    public String toString() {
        return ("("+ this.op_tok.toString() + ", " + this.node.toString() + ")");
    }
}
