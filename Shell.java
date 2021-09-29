import java.util.Scanner;

public class Shell {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        while (true) {
            System.out.print("basic > ");
            String text = myObj.nextLine();
            Lexer lexer = new Lexer("<stdin>", text);
            LexResult result = lexer.run("<stdin>", text);

            if(result.error != null) {
                System.out.println(result.error.toString());
            } else {
                System.out.println(result.tokens.toString());
            }
        }
    }
}