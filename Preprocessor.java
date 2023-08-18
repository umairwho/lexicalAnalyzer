import java.io.*;
import java.util.Scanner;

class Preprocessor {

    public static void main(String[] args) {
        String input_file;

        if (args.length < 1) {
            System.err.println("Usage: java ClassName <input_file>");

            Scanner sc = new Scanner(System.in);
            while (true) {
                System.err.print("Please input a valid filename present in the current directory: ");
                input_file = sc.nextLine();
                File curr_file = new File(input_file);

                if (curr_file.exists()) {
                    sc.close();
                    break;  // Exit the loop if the file exists
                } else {
                    System.out.println("File not found.");
                }
            }
        } else {
            input_file = args[0];
            System.out.println(input_file);
        }

        try {
            removeImportsAnnotationsAndSaveJavaFile(input_file);  // Call the method to remove and save the output file
            printOutputFileContents(); // Call the method to print the output file contents
        } catch (IOException e) {
            System.out.println("Error writing output file: " + e.getMessage());
        }

        //System.out.println("here: " + input_file);
    }

    // Method to remove import statements, annotations, and save a Java file
    private static void removeImportsAnnotationsAndSaveJavaFile(String inputFileName) throws IOException {
        String outputFileName = "out1.txt"; // Name of the output file

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {

            String line;
            boolean inBlockComment = false;

            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();

                if (!inBlockComment) {
                    int blockCommentStart = trimmedLine.indexOf("/*");
                    int blockCommentEnd = trimmedLine.indexOf("*/");
                    int lineComment = trimmedLine.indexOf("//");

                    if (blockCommentStart >= 0 && blockCommentEnd > blockCommentStart) {
                        inBlockComment = false;
                        trimmedLine = trimmedLine.substring(0, blockCommentStart) + trimmedLine.substring(blockCommentEnd + 2);
                    } else if (blockCommentStart >= 0 && blockCommentEnd < 0) {
                        inBlockComment = true;
                        trimmedLine = trimmedLine.substring(0, blockCommentStart);
                    } else if (lineComment >= 0) {
                        trimmedLine = trimmedLine.substring(0, lineComment);
                    }
                } else {
                    int blockCommentEnd = trimmedLine.indexOf("*/");
                    if (blockCommentEnd >= 0) {
                        inBlockComment = false;
                        trimmedLine = trimmedLine.substring(blockCommentEnd + 2);
                    } else {
                        trimmedLine = "";
                    }
                }

                // Remove import statements and annotations
                if (!trimmedLine.isEmpty() && !trimmedLine.startsWith("import") && !trimmedLine.startsWith("@")) {
                    String formattedLine = trimmedLine.replaceAll("\t", " "); // Replace tabs with spaces
                    formattedLine = formattedLine.replaceAll(" +", " "); // Replace multiple spaces with single space
                    writer.write(formattedLine);
                    writer.newLine();
                }
            }
        }
    }
    
    // Method to print the contents of the output file to the console
    private static void printOutputFileContents() throws IOException {
        String outputFileName = "out1.txt";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
