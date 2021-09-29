import java.util.Scanner;

public class Shell {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        while (true) {
            System.out.print("basic > ");
            String text = myObj.nextLine();
            if (text.equals("exit()")) {
                myObj.close();
                break;
            }

            Lexer lexer = new Lexer("<stdin>", text);
            LexResult lexResult = lexer.make_tokens();

            if(lexResult.error != null) {
                System.out.println(lexResult.error.toString());
            } else {
                Parser parser = new Parser(lexResult.tokens);
                ParseResult ast = parser.parse();

                if(ast.error != null) {
                    System.out.println(ast.error.toString());
                } else {
                    System.out.println(ast.node.toString());
                }
            }
        }
        
    }
}