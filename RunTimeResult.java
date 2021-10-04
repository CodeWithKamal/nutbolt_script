public class RunTimeResult {
    public Object value;
    public Error error;

    public RunTimeResult() {
        // empty
    }

    public RunTimeResult(Object value, Error error) {
        this.value = value;
        this.error = error;
    }

    public Object register(Object res) {
        if (((RunTimeResult)res).error != null) {
            this.error = ((RunTimeResult)res).error;
        }
        return ((RunTimeResult)res).value;
    }

    public RunTimeResult success(Object node) {
        this.value = node;
        return this;
    }

    public RunTimeResult failure(Error error) {
        this.error = error;
        return this;
    }
}
