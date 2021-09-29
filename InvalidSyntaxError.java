public class InvalidSyntaxError extends Error {
    public Position pos_start;
    public Position pos_end;
    public String details;

    public InvalidSyntaxError(Position pos_start, Position pos_end, String details) {
        super(pos_start, pos_end, "Invalid Syntax", details);
        this.pos_start = pos_start;
        this.pos_end = pos_end;
        this.details = details;
    }
}
