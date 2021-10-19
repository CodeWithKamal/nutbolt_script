import java.util.List;

public class Parser {
    public List<Token> tokens;
    public int tok_idx;
    public Token current_tok;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.tok_idx = -1;
        this.current_tok = null;
        this.advance();
    }

    public Token advance() {
        this.tok_idx += 1;
        if (this.tok_idx < tokens.size()) {
            this.current_tok = this.tokens.get(this.tok_idx);
        }
        return this.current_tok;
    }

    public ParseResult parse() {
        ParseResult res = (ParseResult) this.expr();
        if (res.error == null && this.current_tok.type != Type.EOF) {
            return res.failure(new InvalidSyntaxError(this.current_tok.pos_start, this.current_tok.pos_end,
                    "Expected '+', '-', '*' or '/'"));
        }
        return res;
    }

    public Object atom() {
        ParseResult res = new ParseResult();
        Token tok = this.current_tok;

        if (tok.type == Type.INT || tok.type == Type.FLOAT) {
            res.register_advancement();
            this.advance();
            return res.success(new NumberNode(tok));
        } else if (tok.type == Type.IDENTIFIER) {
            res.register_advancement();
            this.advance();
            return res.success(new VarAccessNode(tok));
        } else if (tok.type == Type.LPAREN) {
            res.register_advancement();
            this.advance();
            Object expr = res.register(this.expr());
            if (res.error != null)
                return res;
            if (this.current_tok.type == Type.RPAREN) {
                res.register_advancement();
                this.advance();
                return res.success(expr);
            } else {
                return res.failure(
                        new InvalidSyntaxError(this.current_tok.pos_start, this.current_tok.pos_end, "Expected ')'"));
            }
        }

        return res.failure(new InvalidSyntaxError(tok.pos_start, tok.pos_end,
                "Expected int, float, identifier, '+', '-', or '('"));
    }

    public Object power() {
        ParseResult res = new ParseResult();
        Object left = res.register(this.atom());
        if (res.error != null)
            return res;

        while (this.current_tok.type == Type.POW) {
            Token op_tok = this.current_tok;
            res.register_advancement();
            this.advance();
            Object right = res.register(this.factor());
            if (res.error != null)
                return res;
            left = new BinOpNode(left, op_tok, right);
        }

        return res.success(left);
    }

    public Object factor() {
        ParseResult res = new ParseResult();
        Token tok = this.current_tok;

        if (tok.type == Type.PLUS || tok.type == Type.MINUS) {
            res.register_advancement();
            this.advance();
            Object factor = res.register(this.factor());
            if (res.error != null)
                return res;
            return res.success(new UnaryOpNode(tok, factor));

        }

        return this.power();
    }

    public Object term() {
        ParseResult res = new ParseResult();
        Object left = res.register(this.factor());
        if (res.error != null)
            return res;

        while (this.current_tok.type == Type.MUL || this.current_tok.type == Type.DIV) {
            Token op_tok = this.current_tok;
            res.register_advancement();
            this.advance();
            Object right = res.register(this.factor());
            if (res.error != null)
                return res;
            left = new BinOpNode(left, op_tok, right);
        }

        return res.success(left);
    }

    public Object arith_expr() {
        ParseResult res = new ParseResult();
        Object left = res.register(this.term());
        if (res.error != null)
            return res;

        while (this.current_tok.type == Type.PLUS || this.current_tok.type == Type.MINUS) {
            Token op_tok = this.current_tok;
            res.register_advancement();
            this.advance();
            Object right = res.register(this.term());
            if (res.error != null)
                return res;
            left = new BinOpNode(left, op_tok, right);
        }

        return res.success(left);
    }

    public Object comp_expr() {
        ParseResult res = new ParseResult();

        if (this.current_tok.matches(Type.KEYWORD, "not")) {
            Token op_tok = this.current_tok;
            res.register_advancement();
            this.advance();

            Object node = res.register(this.comp_expr());

            if (res.error != null) {
                return res;
            }

            return res.success(new UnaryOpNode(op_tok, node));
        }

        Object left = res.register(this.arith_expr());
        if (res.error != null)
            return res;

        while (this.current_tok.type == Type.EE || this.current_tok.type == Type.NE || this.current_tok.type == Type.LT
                || this.current_tok.type == Type.GT || this.current_tok.type == Type.LTE
                || this.current_tok.type == Type.GTE) {
            Token op_tok = this.current_tok;
            res.register_advancement();
            this.advance();
            Object right = res.register(this.factor());
            if (res.error != null)
                return res;
            left = new BinOpNode(left, op_tok, right);
        }

        Object node = res.register(res.success(left));

        if (res.error != null) {
            return res.failure(new InvalidSyntaxError(this.current_tok.pos_start, this.current_tok.pos_end,
                    "Expected int, float, identifier, '+', '-', '(', 'not'"));
        }

        return res.success(node);
    }

    public Object expr() {
        ParseResult res = new ParseResult();
        if (this.current_tok.matches(Type.KEYWORD, "let")) {
            res.register_advancement();
            this.advance();

            if (this.current_tok.type != Type.IDENTIFIER) {
                return res.failure(new InvalidSyntaxError(this.current_tok.pos_start, this.current_tok.pos_end,
                        "Expected identifier"));
            }

            Token var_name = this.current_tok;
            res.register_advancement();
            this.advance();

            if (this.current_tok.type != Type.EQ) {
                return res.failure(
                        new InvalidSyntaxError(this.current_tok.pos_start, this.current_tok.pos_end, "Expected '='"));
            }

            res.register_advancement();
            this.advance();
            Object expr = res.register(this.expr());
            if (res.error != null) {
                return res;
            }
            return res.success(new VarAssignNode(var_name, expr));
        }

        Object left = res.register(this.comp_expr());

        while (this.current_tok.matches(Type.KEYWORD, "and")
                || this.current_tok.matches(Type.KEYWORD, "or")) {
            Token op_tok = this.current_tok;
            res.register_advancement();
            this.advance();
            Object right = res.register(this.term());
            if (res.error != null)
                return res;
            left = new BinOpNode(left, op_tok, right);
        }

        Object node = res.register(res.success(left));

        if (res.error != null) {
            return res.failure(new InvalidSyntaxError(this.current_tok.pos_start, this.current_tok.pos_end,
                    "Expected 'let', int, float, identifier, '+', '-' or '('"));
        }

        return res.success(node);
    }
}