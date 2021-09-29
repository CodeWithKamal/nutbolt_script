public class NumberNode {
    public Token tok;

    public NumberNode(Token tok) {
        this.tok = tok;
    }

    public String toString() {
        return tok.toString();
    }
}