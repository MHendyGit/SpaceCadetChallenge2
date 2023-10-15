import java.util.ArrayList;

public class SyntaxAnalyser {

    public Command analyseTokens(ArrayList<String[]> tokens) throws BBSyntaxError{
        Command command = null;
        String[] token = tokens.get(0);
        String type = token[1];
        String value = token[0];

        if (type.equals("keyword")) {

            if (value.equals("clear") || value.equals("incr") || value.equals("decr")) {
                token = tokens.get(1);
                if (token[1].equals("identifier")) {
                    if (tokens.size() > 2) {
                        throw new BBSyntaxError(value + " must be proceded by a single variable");
                    } else {
                        command = new Command(value, token[0]);
                    }
                } else {
                    throw new BBSyntaxError(value + " cannot be proceded by " + token[1]);
                }

            } else if (value.equals("while")) {
                token = tokens.get(1);
                if (token[1].equals("identifier")) {
                    token = tokens.get(2);
                    if (token[1].equals("operator")) {
                        token = tokens.get(3);
                        if (token[1].equals("literal")) {
                            token = tokens.get(4);
                            if (token[0].equals("do")) {
                                if (tokens.size() > 5) {
                                    throw new BBSyntaxError("while statement must end with do");
                                } else {
                                    LoopData loopData = new LoopData(tokens.get(1)[0], tokens.get(3)[0]);
                                    command = new Command(value, loopData);
                                }
                            }  else throw new BBSyntaxError("while statement missing keyword do");
                        } else throw new BBSyntaxError("operator must be proceded by a literal");
                    }  else throw new BBSyntaxError("identifier must be proceded by an operator");
                } else throw new BBSyntaxError("while must be proceded by an identifier");

            } else if (value.equals("end")) {
                if (tokens.size() == 1) {
                    command = new Command(value, "");
                } else throw new BBSyntaxError("unexpected tokens found after keyword end");

            } else throw new BBSyntaxError("Cannot start statement with 'do'");

        } else {
            throw new BBSyntaxError("Cannot start statement with " + type);
        }
        return command;
    }
}