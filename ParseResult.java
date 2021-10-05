public class ParseResult {
    public Error error;
    public Object node;
    public int advance_count = 0;

    public ParseResult() {
        // this.error = null;
        // this.node = null;
    }

    public Object register(Object res) {
        this.advance_count += ((ParseResult)res).advance_count;
        if (((ParseResult)res).error != null) {
            this.error = ((ParseResult)res).error;
        }
        return ((ParseResult)res).node;
    }

    public void register_advancement() {
        this.advance_count += 1;

    }

    public ParseResult success(Object node) {
        this.node = node;
        return this;
    }

    public ParseResult failure(Error error) {
        if (this.error == null || this.advance_count == 0) {
            this.error = error;
        }
        return this;
    }

}
