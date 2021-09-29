/**
 * 
 * TOKENS
 * 
 */
public class Token {
    public Type type;
    public String value;

    public Token(Type type, String value) {
       this.type = type;
       this.value = value;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String toString()
    {
        if(this.value != null){
            return (this.getType().toString() + ":" + this.getValue());
        } else {
            return this.getType().toString();
        }
    }
}