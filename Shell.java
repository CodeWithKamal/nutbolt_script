import java.util.Scanner;

public class Shell {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        SymbolTable global_symbol_table = new SymbolTable();
        global_symbol_table.set("null", new Number(0));
        while (true) {
            System.out.print("Nutbolt-Script >>> ");
            String text = myObj.nextLine();
            if (text.equals("exit()")) {
                myObj.close();
                break;
            }

            if (text.equals("sym()")) {
                System.out.println("Global: " + global_symbol_table.symbols);
                text = "null";
            }

            Lexer lexer = new Lexer("<stdin>", text);
            LexResult lexResult = lexer.make_tokens();

            if(lexResult.error != null) {
                System.out.println(lexResult.error.toString());
            } else {
                // System.out.println(lexResult.tokens.toString());
                Parser parser = new Parser(lexResult.tokens);
                ParseResult ast = parser.parse();

                if(ast.error != null) {
                    System.out.println(ast.error.toString());
                } else {
                    // System.out.println(ast.node.toString());
                    
                    Interpreter interpreter = new Interpreter();
                    Context context = new Context("<program>");
                    context.symbol_table = global_symbol_table;
                    RunTimeResult result = (RunTimeResult)interpreter.visit(ast.node, context);

                    if (result.error != null) {
                        System.out.println(result.error.toString());
                    } else {
                        System.out.println(result.value.toString());
                    }
                }
            }
        }
    }
}