public class RunTimeError extends Error {

    public Position pos_start;
    public Position pos_end;
    public String details;
    public Context context;

    public RunTimeError(Position pos_start, Position pos_end, String details, Context context) {
        super(pos_start, pos_end, "Runtime Error", details);
        this.pos_start = pos_start;
        this.pos_end = pos_end;
        this.details = details;
        this.context = context;
    }

    @Override
    public String toString() {
        String result = "";
        result = this.generate_traceback();
        result += (this.getErrorName() + ": " + this.getDetails());
        result += ("\n\n" + string_with_arrows(this.pos_start.ftxt, this.pos_start, this.pos_end));
        return result;
    }

    public String generate_traceback() {
        String result = "";
        Position pos = this.pos_start;
        Context ctx = this.context;

        while (ctx != null) {
            result += "  File " + pos.fn + ", line " + String.valueOf(pos.ln + 1) + ", in " + ctx.display_name + "\n" + result;
            pos = ctx.parent_entry_pos;
            ctx = ctx.parent;
        }

        return "Traceback (most recent call last):\n"+result;
    }
}
