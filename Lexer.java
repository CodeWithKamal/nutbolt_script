import java.util.List;
import java.util.ArrayList;
/**
 * 
 *  LEXER
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
        if(this.pos.idx < this.text.length()) {
            this.current_char = this.text.charAt(this.pos.idx);
        } else {
            this.current_char = Character.MIN_VALUE;
        }
    }

    public LexResult make_tokens() {
        List<Token> tokens = new ArrayList();

        while (this.current_char != Character.MIN_VALUE) {
            if(this.current_char == ' ' || this.current_char == '\t'){
                this.advance();
            } else if(this.current_char == '+') {
                tokens.add(new Token(Type.PLUS, null, this.pos, null));
                this.advance();
            } else if(this.current_char == '-') {
                tokens.add(new Token(Type.MINUS, null, this.pos, null));
                this.advance();
            } else if(this.current_char == '*') {
                tokens.add(new Token(Type.MUL, null, this.pos, null));
                this.advance();
            } else if(this.current_char == '/') {
                tokens.add(new Token(Type.DIV, null, this.pos, null));
                this.advance();
            } else if(this.current_char == '(') {
                tokens.add(new Token(Type.LPAREN, null, this.pos, null));
                this.advance();
            } else if(this.current_char == ')') {
                tokens.add(new Token(Type.RPAREN, null, this.pos, null));
                this.advance();
            } else if(Character.isDigit(this.current_char)) {
                tokens.add(this.make_number());
                this.advance();
            } else {
                Position pos_start = this.pos.copy();
                char chare = this.current_char;
                this.advance();
                return new LexResult(tokens, new IllegalCharError(pos_start, this.pos, "'" + Character.toString(chare) + "'"));
            }
        }

        tokens.add(new Token(Type.EOF, null, this.pos, null));
        return new LexResult(tokens, null);
    }

    public Token make_number() {
        String num_str = "";
        int dot_count = 0;
        Position pos_start = this.pos.copy();

        while ((this.current_char != Character.MIN_VALUE) && (Character.isDigit(this.current_char) || Character.toString(this.current_char).contains("."))) {
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
}