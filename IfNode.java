import java.lang.reflect.Field;
import java.util.List;

public class IfNode {
    public List<IfStatement> cases;
    public Object else_case;
    public Position pos_start;
    public Position pos_end;

    public IfNode(List<IfStatement> cases, Object else_case) {
        this.cases = cases;
        this.else_case = else_case;

        try {
            Field ps = this.cases.get(0).expr.getClass().getDeclaredField("pos_start");
            ps.setAccessible(true);
            this.pos_start = (Position) ps.get(this.cases.get(0).expr);
        } catch (Exception e) {
            System.err.print("Error<IfNode-ps>: ");
            System.err.print(e);
        }

        if (else_case != null) {
            try {
                Field pe = this.else_case.getClass().getDeclaredField("pos_end");
                pe.setAccessible(true);
                this.pos_end = (Position) pe.get(this.else_case);
            } catch (Exception e) {
                System.err.print("Error<IfNode>: ");
                System.err.print(e);
            }
        } else {
            try {
                Field pe = this.cases.get(cases.size()-1).expr.getClass().getDeclaredField("pos_end");
                pe.setAccessible(true);
                this.pos_end = (Position) pe.get(this.cases.get(cases.size()-1).expr);
            } catch (Exception e) {
                System.err.print("Error<IfNode>: ");
                System.err.print(e);
            }
        }

    }
}
