import java.util.List;
import java.util.ArrayList;

/**
 * 
 * LEXER
 * 
 */
public class Lexer {
    public String text;
    public Position pos;
    public char current_char;
    public String fn;

    public Lexer(String fn, String text) {
        this.fn = fn;
        this.text = text;
        this.pos = new Position(-1, 0, -1, fn, text);
        this.current_char = Character.MIN_VALUE;
        this.advance();
    }

    public void advance() {
        this.pos.advance(this.current_char);
        if (this.pos.idx < this.text.length()) {
            this.current_char = this.text.charAt(this.pos.idx);
        } else {
            this.current_char = Character.MIN_VALUE;
        }
    }

    public LexResult make_tokens() {
        List<Token> tokens = new ArrayList<Token>();

        while (this.current_char != Character.MIN_VALUE) {
            if (this.current_char == ' ' || this.current_char == '\t') {
                this.advance();
            } else if (Character.isDigit(this.current_char)) {
                tokens.add(this.make_number());
                this.advance();
            } else if (Character.isLetterOrDigit(this.current_char)) {
                tokens.add(this.make_identifier());
                this.advance();
            } else if (this.current_char == '+') {
                tokens.add(new Token(Type.PLUS, null, this.pos, null));
                this.advance();
            } else if (this.current_char == '-') {
                tokens.add(new Token(Type.MINUS, null, this.pos, null));
                this.advance();
            } else if (this.current_char == '*') {
                tokens.add(new Token(Type.MUL, null, this.pos, null));
                this.advance();
            } else if (this.current_char == '/') {
                tokens.add(new Token(Type.DIV, null, this.pos, null));
                this.advance();
            } else if (this.current_char == '^') {
                tokens.add(new Token(Type.POW, null, this.pos, null));
                this.advance();
            } else if (this.current_char == '(') {
                tokens.add(new Token(Type.LPAREN, null, this.pos, null));
                this.advance();
            } else if (this.current_char == ')') {
                tokens.add(new Token(Type.RPAREN, null, this.pos, null));
                this.advance();
            } else if (this.current_char == '!') {
                NotEquals notEquals = this.make_not_equals();
                if (notEquals.error != null) {
                    // tokens.clear();
                    return new LexResult(tokens, notEquals.error);
                }
                tokens.add(notEquals.tok);
            } else if (this.current_char == '=') {
                tokens.add(this.make_equals());
            } else if (this.current_char == '<') {
                tokens.add(this.make_less_than());
            } else if (this.current_char == '>') {
                tokens.add(this.make_greater_than());
            } else {
                Position pos_start = this.pos.copy();
                char chare = this.current_char;
                this.advance();
                // tokens.clear();
                return new LexResult(tokens,
                        new IllegalCharError(pos_start, this.pos, "'" + Character.toString(chare) + "'"));
            }
        }

        tokens.add(new Token(Type.EOF, null, this.pos, null));
        return new LexResult(tokens, null);
    }

    public Token make_number() {
        String num_str = "";
        int dot_count = 0;
        Position pos_start = this.pos.copy();

        while ((this.current_char != Character.MIN_VALUE)
                && (Character.isDigit(this.current_char) || Character.toString(this.current_char).contains("."))) {
            if (this.current_char == '.') {
                if (dot_count == 1) {
                    break;
                }
                dot_count += 1;
                num_str += ".";
            } else {
                num_str += Character.toString(this.current_char);
            }
            this.advance();
        }

        this.pos.idx--;
        if (dot_count == 0) {
            return new Token(Type.INT, num_str, pos_start, this.pos);
        } else {
            return new Token(Type.FLOAT, num_str, pos_start, this.pos);
        }
    }

    public Token make_identifier() {
        String id_str = "";
        Position pos_start = this.pos.copy();

        while ((this.current_char != Character.MIN_VALUE)
                && ((Character.isLetterOrDigit(this.current_char)) || (this.current_char == '_'))) {
            id_str += this.current_char;
            this.advance();
        }

        this.pos.idx--;

        Type tok_type;
        if (Token.KEYWORDS.contains(id_str)) {
            tok_type = Type.KEYWORD;
        } else {
            tok_type = Type.IDENTIFIER;
        }
        return new Token(tok_type, id_str, pos_start, this.pos);
    }

    public NotEquals make_not_equals() {
        Position pos_start = this.pos.copy();
        this.advance();

        if (this.current_char == '=') {
            this.advance();
            return new NotEquals(new Token(Type.NE, "!=", pos_start, this.pos), null);
        }

        this.advance();
        return new NotEquals(null, new ExpectedCharError(pos_start, this.pos, "'=' (after '!')"));
    }

    public Token make_equals() {
        Type tok_type = Type.EQ;
        Position pos_start = this.pos.copy();
        this.advance();

        if (this.current_char == '=') {
            this.advance();
            tok_type = Type.EE;
        }

        return new Token(tok_type, null, pos_start, this.pos);
    }

    public Token make_less_than() {
        Type tok_type = Type.LT;
        Position pos_start = this.pos.copy();
        this.advance();

        if (this.current_char == '=') {
            this.advance();
            tok_type = Type.LTE;
        }

        return new Token(tok_type, null, pos_start, this.pos);
    }

    public Token make_greater_than() {
        Type tok_type = Type.GT;
        Position pos_start = this.pos.copy();
        this.advance();

        if (this.current_char == '=') {
            this.advance();
            tok_type = Type.GTE;
        }

        return new Token(tok_type, null, pos_start, this.pos);
    }
}