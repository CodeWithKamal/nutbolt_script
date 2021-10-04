public class Context {
    public String display_name;
    public Context parent;
    public Position parent_entry_pos;
    
    public Context(String display_name) {
        this.display_name = display_name;
    }
    
    public Context(String display_name, Context parent, Position parent_entry_pos) {
        this.display_name = display_name;
        this.parent = parent;
        this.parent_entry_pos = parent_entry_pos;
    }

}
