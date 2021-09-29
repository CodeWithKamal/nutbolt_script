import java.util.List;

public class LexResult {
    public List<Token> tokens;
    public Error error;

    public LexResult(List<Token> tokens, Error error) {
        this.tokens = tokens;
        this.error = error;
    }
} 