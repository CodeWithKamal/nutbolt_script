public class NotEquals {
    public Token tok;
    public Error error;

    public NotEquals(Token tok, Error error) {
        this.tok = tok;
        this.error = error;
    }

    public String toString() {
        return tok.toString();
    }
}
