public class ParseResult {
    public Error error;
    public Object node;

    public ParseResult() {
        // this.error = null;
        // this.node = null;
    }

    public Object register(Object res) {
        if (res instanceof ParseResult) {
            if (((ParseResult)res).error != null) {
                this.error = ((ParseResult)res).error;
            }
            return ((ParseResult)res).node;
        }

        return res;
    }

    public ParseResult success(Object node) {
        this.node = node;
        return this;
    }

    public ParseResult failure(Error error) {
        this.error = error;
        return this;
    }

}
