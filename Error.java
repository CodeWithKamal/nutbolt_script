public class Error {
    public Position pos_start;
    public Position pos_end;
    public String error_name;
    public String details;

    public Error(Position pos_start, Position pos_end, String error_name, String details) {
        this.pos_start = pos_start;
        this.pos_end = pos_end;
        this.error_name = error_name;
        this.details = details;
    }

    public String getErrorName() {
        return error_name;
    }

    public String getDetails() {
        return details;
    }

    public String toString() {
        String result = "";
        result = (this.getErrorName() + ": " + this.getDetails());
        result += ("\nFile " + pos_start.fn + ", line " + String.valueOf(pos_start.ln + 1));
        return result;
    }
}