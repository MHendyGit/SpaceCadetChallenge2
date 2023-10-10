import java.util.ArrayList;

public class Lexer {
    String[] lines;
    String[] keywords = {"clear", "incr", "decr", "while", "do", "end"};
    public Lexer(String contents) {
        lines = contents.split(";");
    }

    public ArrayList<String[]> lexLine(Integer lineNumber) {
        ArrayList<String[]> tokens = new ArrayList<String[]>();
        String line = lines[lineNumber];
        String[] words = line.split(" ");

        String word;
        for (int i = 0; i < words.length; i++) {
            word = words[i];

            if (isKeyword(word)) {
                tokens.add(new String[] {word, "keyword"});
            } else if (word.equals("not")) {
                tokens.add(new String[] {word, "operator"});
            } else if (word != "" && word != "\t") {
                if (isInteger(word)) {
                    tokens.add(new String[] {word, "literal"});
                } else {
                    tokens.add(new String[] {word, "identifier"});
                }
            }

        }

        return tokens;
    }
    
    private Boolean isKeyword(String word) {
        String keyword;
        for (int i = 0; i < keywords.length; i++) {
            keyword = keywords[i];
            if (word.equals(keyword)) return true;
        }
        return false;
    }

    private Boolean isInteger(String word) {
        try {
            Integer.parseInt(word);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Integer getProgLength() {
        return lines.length;
    }
}
