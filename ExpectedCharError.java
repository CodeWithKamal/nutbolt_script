public class ExpectedCharError extends Error {
    public Position pos_start;
    public Position pos_end;
    public String details;

    public ExpectedCharError(Position pos_start, Position pos_end, String details) {
        super(pos_start, pos_end, "Expected Character", details);
        this.pos_start = pos_start;
        this.pos_end = pos_end;
        this.details = details;
    }
}
