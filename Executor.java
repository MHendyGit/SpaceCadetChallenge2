import java.util.HashMap;
import java.util.Stack;

public class Executor {
    private Lexer lexer;
    private SyntaxAnalyser syntaxAnalyser;
    private Integer programCounter = 0;
    private HashMap<String, Integer> variables = new HashMap<String, Integer>();
    private Stack<LoopData> whileStack = new Stack<LoopData>();

    public Executor(Lexer lexer, SyntaxAnalyser syntaxAnalyser) {
        this.lexer = lexer;
        this.syntaxAnalyser = syntaxAnalyser;
    }

    public void run() throws BBSyntaxError, BBRuntimeError{
        Integer eof = lexer.getProgLength();
        Command command;

        while (programCounter < eof) {
            command = syntaxAnalyser.analyseTokens(lexer.lexLine(programCounter));
            switch (command.getName()) {
                case "clear":
                    clear(command.getArg());
                    break;
                case "incr":
                    incr(command.getArg());
                    break;
                case "decr":
                    decr(command.getArg());
                    break;
                case "while":
                    _while(command.getData());
                    break;
                case "end":
                    end();
                    break;  
            }
            displayVars();
            programCounter++;
        }
    }

    private void clear(String variable) {
        variables.put(variable, 0);
    }

    private void incr(String variable) {
        Integer currentValue = variables.get(variable);
        if (currentValue == null) {
            System.out.println("was null");
            clear(variable);
            currentValue = 0;
        }
        variables.put(variable, currentValue+1);
    }

    private void decr(String variable) throws BBRuntimeError{
        Integer currentValue = variables.get(variable);
        if (currentValue == null) {
            clear(variable);
            currentValue = 0;
        }
        Integer newValue = currentValue-1;
        if (newValue >= 0) {
            variables.put(variable, newValue);
        } else {
            throw new BBRuntimeError("Value of "+variable+" reduced below 0");
        }
    }

    private void _while(LoopData loopData) {
        loopData.setLineNo(programCounter);
        whileStack.push(loopData);
    }

    private void end() throws BBRuntimeError {
        if (! whileStack.empty()) {
            LoopData loopData = whileStack.peek();
            Integer currentValue = variables.get(loopData.getVariable());
            if (currentValue != loopData.getTarget()) {
                programCounter = loopData.getLineNo();
            } else whileStack.pop();
        } else {
            throw new BBRuntimeError("end statement found where no loop was opened");
        }
    }

    private void displayVars() {
        System.out.printf("--------Line %s--------\n", programCounter+1);
        for (String i : variables.keySet()) {
            System.out.printf("Variable: %s | Value: %s\n", i, variables.get(i));
          }
    }
}
