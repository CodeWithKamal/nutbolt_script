public class VarAccessNode {
    public Token var_name_tok;
    public Position pos_start;
    public Position pos_end;

    public VarAccessNode(Token var_name_tok) {
        this.var_name_tok = var_name_tok;

        this.pos_start = this.var_name_tok.pos_start;
        this.pos_end = this.var_name_tok.pos_end;
    }

    public String toString() {
        return "<"+this.var_name_tok.type.toString()+":"+this.var_name_tok.value+">";
    }
}
