public class Interpreter {

    public Object visit(Object node, Context context) {
        String method_name = node.getClass().getName();
        if (method_name.equals("NumberNode")) {
            return visit_NumberNode(node, context);
        } else if (method_name.equals("BinOpNode")) {
            return visit_BinOpNode(node, context);
        } else if (method_name.equals("UnaryOpNode")) {
            return visit_UnaryOpNode(node, context);
        } else {
            return no_visit_method(node, context);
        }
    }

    public Object no_visit_method(Object node, Context context) {
        return (Object)"No visit_" + node.getClass().getName() + " method defined";
    }

    public Object visit_NumberNode(Object node, Context context) {
        return (Object) new RunTimeResult().success(new Number(Double.parseDouble(((NumberNode)node).tok.value)).set_context(context).set_pos(((NumberNode)node).tok.pos_start, ((NumberNode)node).tok.pos_end));
    }

    public Object visit_BinOpNode(Object node, Context context) {
        RunTimeResult res = new RunTimeResult();
        Number left = (Number) res.register(this.visit(((BinOpNode)node).left_node, context));
        if (res.error != null) {
            return res;
        }
        Number right = (Number) res.register(this.visit(((BinOpNode)node).right_node, context));
        if (res.error != null) {
            return res;
        }
        Object result = null;

        if(((BinOpNode)node).op_tok.type == Type.PLUS) {
            result = left.added_to(right);
        } else if(((BinOpNode)node).op_tok.type == Type.MINUS) {
            result = left.subbed_by(right);
        } else if(((BinOpNode)node).op_tok.type == Type.MUL) {
            result = left.multed_by(right);
        } else if(((BinOpNode)node).op_tok.type == Type.DIV) {
            result = left.dived_by(right);
        }
        
        if (((RunTimeResult)result).error != null) {
            return res.failure(((RunTimeResult)result).error);
        }
        
        if (node.getClass().getName().equals("NumberNode")) {
            result = ((RunTimeResult)result).value;
            return res.success((Object)((Number)result).set_pos(((NumberNode)node).tok.pos_start, ((NumberNode)node).tok.pos_end));
        } else {
            result = ((RunTimeResult)result).value;
            return res.success((Object)((Number)result).set_pos(((BinOpNode)node).op_tok.pos_start, ((BinOpNode)node).op_tok.pos_end));
        }
    }
    
    public Object visit_UnaryOpNode(Object node, Context context) {
        RunTimeResult res = new RunTimeResult();
        Object number = res.register((Number)this.visit(((UnaryOpNode)node).node, context));

        if (res.error != null) {
            return res;
        }

        if (((UnaryOpNode)node).op_tok.type == Type.MINUS) {
            number = ((Number)number).multed_by(new Number(-1));
        }

        if (((RunTimeResult)number).error != null) {
            return res.failure(((RunTimeResult)number).error);
        }
        

        if (node.getClass().getName().equals("NumberNode")) {
            number = ((RunTimeResult)number).value;
            return res.success((Object)((Number)number).set_pos(((NumberNode)node).tok.pos_start, ((NumberNode)node).tok.pos_end));
        } else {
            number = ((RunTimeResult)number).value;
            return res.success((Object)((Number)number).set_pos(((UnaryOpNode)node).op_tok.pos_start, ((UnaryOpNode)node).op_tok.pos_end));
        }
    }

}
