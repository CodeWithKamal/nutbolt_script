import java.util.HashMap;

public class SymbolTable {
    public HashMap<Object, Object> symbols = new HashMap<Object, Object>();
    public SymbolTable parent;

    public SymbolTable() {
        // empty
    }

    public Object get(Object name) {
        Object value = this.symbols.get(name);
        if (value == null && this.parent != null) {
            return this.parent.get(name);
        }
        return value;
    }

    public void set(Object name, Object value) {
        this.symbols.put(name, value);
    }

    public void remove(Object name) {
        this.symbols.remove(name);
    }
}
