import java.io.*;
import java.util.*;

public class BBInterpreter {
    public static void main(String[] args) throws Exception {
        BBInterpreter self = new BBInterpreter();

        System.out.println("Enter filepath of code:");
        String filePath = self.readFromConsole();

        Lexer lexer = new Lexer(self.readFile(filePath));
        SyntaxAnalyser syntaxAnalyser = new SyntaxAnalyser();
        Executor executor = new Executor(lexer, syntaxAnalyser);
        executor.run();
    }

    public String readFromConsole() throws IOException {
        InputStreamReader i = new InputStreamReader(System.in);
        BufferedReader b = new BufferedReader(i);

        String filePath = b.readLine();
        i.close();
        b.close();
        return filePath;
    }

    public String readFile(String filePath) throws FileNotFoundException{
        String contents = "";
        File file = new File(filePath);
        Scanner fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            contents = contents + fileReader.nextLine();
        }
        fileReader.close();
        return contents;
    }
}