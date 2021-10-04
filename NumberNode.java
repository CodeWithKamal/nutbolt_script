public class NumberNode {
    public Token tok;
    public Position pos_start;
    public Position pos_end;

    public NumberNode(Token tok) {
        this.tok = tok;

        this.pos_start = this.tok.pos_start;
        this.pos_end = this.tok.pos_end;
    }

    public String toString() {
        return tok.toString();
    }
}