public class Position {
    public int idx;
    public int ln;
    public int col;
    public String fn;
    public String ftxt;

    public Position(int idx, int ln, int col, String fn, String ftxt) {
        this.idx = idx;
        this.ln = ln;
        this.col = col;
        this.fn = fn;
        this.ftxt = ftxt;
    }

    public Position advance(char current_char) {
        this.idx += 1;
        this.col += 1;

        if (current_char == '\n') {
            this.ln += 1;
            this.col = 0;
        }

        return this;
    }

    public Position copy() {
        return new Position(this.idx, this.ln, this.col, this.fn, this.ftxt);
    }
}