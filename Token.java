/**
 * 
 * TOKENS
 * 
 */
public class Token {
    public Type type;
    public String value;
    public Position pos_start = null;
    public Position pos_end = null ;

    public Token(Type type, String value, Position pos_start, Position pos_end) {
       this.type = type;
       this.value = value;

       if (pos_start != null) {
           this.pos_start = pos_start.copy();
           this.pos_end = pos_start.copy();
           this.pos_end.advance(Character.MIN_VALUE);
       }

       if (pos_end != null) {
           this.pos_end = pos_end;
       }
    }

    public String toString()
    {
        if(this.value != null){
            return (this.type+ ":" + this.value);
        } else {
            return this.type.name();
        }
    }
}