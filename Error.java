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
        result += ("\nFile " + this.pos_start.fn + ", line " + String.valueOf(this.pos_start.ln + 1));
        result += ("\n\n" + string_with_arrows(this.pos_start.ftxt, this.pos_start, this.pos_end));
        return result;
    }

    private String string_with_arrows(String text, Position pos_start, Position pos_end) {
        String result = "";

        // Calculate indices
        int idx_start = Math.max(text.lastIndexOf("\n", 0), 0);
        int idx_end = text.indexOf("\n", idx_start + 1);
        if(idx_end < 0) idx_end = text.length();

        // Generate each line
        int line_count = pos_end.ln - pos_start.ln + 1;
        for (int i = 0; i < line_count; i++) {
            // Calculate line columns
            String line = text.substring(idx_start, idx_end);
            int col_start = (i == 0) ? pos_start.col : 0;
            int col_end = (i == line_count - 1) ? pos_end.col : line.length() - 1;
            
            // Append to result
            result += line + '\n';
            result += (" ".repeat(col_start)) + ("^".repeat((col_end - col_start)));

            // Re-calculate indices
            idx_start = idx_end;
            idx_end = text.indexOf('\n', idx_start + 1);
            if (idx_end < 0) idx_end = text.length();
        }

        return result.replaceAll("\t", "");
    }    
}