public class VarAssignNode {
    public Token var_name_tok;
    public Object value_node;
    public Position pos_start;
    public Position pos_end;

    public VarAssignNode(Token var_name_tok, Object value_node) {
        this.var_name_tok = var_name_tok;
        this.value_node = value_node;

        this.pos_start = this.var_name_tok.pos_start;
        if (this.value_node.getClass().getName().equals("NumberNode")) {
            this.pos_end = ((NumberNode)this.value_node).pos_end;
        } else if (this.value_node.getClass().getName().equals("UnaryOpNode")) {
            this.pos_end = ((UnaryOpNode)this.value_node).pos_end;
        }  else if (this.value_node.getClass().getName().equals("BinOpNode")){
            this.pos_end = ((BinOpNode)this.value_node).pos_end;
        } else if (this.value_node.getClass().getName().equals("VarAssignNode")) {
            this.pos_end = ((VarAssignNode)this.value_node).pos_end;
        } else if (this.value_node.getClass().getName().equals("VarAccessNode")) {
            this.pos_end = ((VarAccessNode)this.value_node).pos_end;
        } else if (this.value_node.getClass().getName().equals("IfNode")) {
            this.pos_end = ((IfNode)this.value_node).pos_end;
        } else {
            this.pos_end = ((NumberNode)this.value_node).pos_end;
        }
    }


    public String toString() {
        return "<"+this.var_name_tok.toString()+", "+this.value_node.toString()+">";
    }
}
