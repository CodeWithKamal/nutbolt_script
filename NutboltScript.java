import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class NutboltScript {
    public static void main(String[] args) {
        SymbolTable global_symbol_table = new SymbolTable();
        global_symbol_table.set("null", new Number(0));
        global_symbol_table.set("true", new Number(1.0));
        global_symbol_table.set("false", new Number(0.0));
        if (args.length > 0) {
            try {
                File myObj = new File(args[0]+".nbscript");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                  String text = myReader.nextLine();
                  if (text.equals("exit()")) {
                    myReader.close();
                    System.exit(0);
                    break;
                }
    
                if (text.equals("sym()")) {
                    System.out.println("Global: " + global_symbol_table.symbols);
                    text = "null";
                }
    
                Lexer lexer = new Lexer(args[0]+".nbscript", text);
                LexResult lexResult = lexer.make_tokens();
    
                if (lexResult.error != null) {
                    System.out.println(lexResult.error.toString());
                } else {
                    // System.out.println(lexResult.tokens.toString());
                    Parser parser = new Parser(lexResult.tokens);
                    ParseResult ast = parser.parse();
    
                    if (ast.error != null) {
                        System.out.println(ast.error.toString());
                    } else {
                        // System.out.println(ast.node.toString());
    
                        Interpreter interpreter = new Interpreter();
                        Context context = new Context("<program>");
                        context.symbol_table = global_symbol_table;
                        RunTimeResult result = (RunTimeResult) interpreter.visit(ast.node, context);
    
                        if (result.error != null) {
                            System.out.println(result.error.toString());
                        } else {
                            System.out.println(result.value.toString());
                        }
                    }
                }
                }
                myReader.close();
              } catch (FileNotFoundException e) {
                System.out.println("Nutbolt Script Not Found!");
              }
        } else {
            Scanner myObj = new Scanner(System.in);
            
            while (true) {
                System.out.print("Nutbolt-Script >>> ");
                String text = myObj.nextLine();
                if (text.equals("exit()")) {
                    myObj.close();
                    System.exit(0);
                    break;
                }
    
                if (text.equals("sym()")) {
                    System.out.println("Global: " + global_symbol_table.symbols);
                    text = "null";
                }
    
                Lexer lexer = new Lexer("<stdin>", text);
                LexResult lexResult = lexer.make_tokens();
    
                if (lexResult.error != null) {
                    System.out.println(lexResult.error.toString());
                } else {
                    // System.out.println(lexResult.tokens.toString());
                    Parser parser = new Parser(lexResult.tokens);
                    ParseResult ast = parser.parse();
    
                    if (ast.error != null) {
                        System.out.println(ast.error.toString());
                    } else {
                        // System.out.println(ast.node.toString());
    
                        Interpreter interpreter = new Interpreter();
                        Context context = new Context("<program>");
                        context.symbol_table = global_symbol_table;
                        RunTimeResult result = (RunTimeResult) interpreter.visit(ast.node, context);
    
                        if (result.error != null) {
                            System.out.println(result.error.toString());
                        } else {
                            if (result.value != null) {
                                System.out.println(result.value.toString());
                            }
                        }
                    }
                }
            }
        }
    }
}