import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java LexicalAnalyzer <output_file>");
            return;
        }

        String outputFileName = args[0];

        try {
            analyzeLexemes(outputFileName);
        } catch (IOException e) {
            System.out.println("Error analyzing lexemes: " + e.getMessage());
        }
    }

    private static void analyzeLexemes(String outputFileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFileName))) {
            StringBuilder code = new StringBuilder();
            String line;

            // Reading contents of the output file
            while ((line = reader.readLine()) != null) {
                code.append(line);
            }

            String codeStr = code.toString();
            
            // Regular expression to match lexemes
            String regex = "\\b(int|float|double|char|void|boolean|true|false|return|class|public|private|protected|static|final|try|catch|throw|if|else|while|for|do|[\\{\\}\\(\\),;\\+\\-\\*/%=!<>\\&\\|\\`\\\"]|\\+\\+|\\-\\-|\\=\\=|\\!\\=|\\\"[^\"]*\\\"|\\w+)\\b";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(codeStr);

            // Process each matched item
            while (matcher.find()) {
                String token = matcher.group();
                processToken(token);
            }
        }
    }

    private static void processToken(String token) {
        System.out.println("Token: " + token);
    }
}
