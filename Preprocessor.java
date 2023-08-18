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
            formatAndSaveJavaFile(input_file);  // Call the method to format and save the output file
        } catch (IOException e) {
            System.out.println("Error writing output file: " + e.getMessage());
        }

        System.out.println("here: " + input_file);
    }

    // Method to format and save a Java file
    private static void formatAndSaveJavaFile(String inputFileName) throws IOException {
        String outputFileName = inputFileName + "_output.java"; // Create a new name for the output file

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {

            String line;
            int indentationLevel = 0;

            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();
                if (!trimmedLine.isEmpty()) {
                    // Adjust indentation
                    if (trimmedLine.endsWith("}") || trimmedLine.endsWith(");")) {
                        indentationLevel--;
                    }

                    for (int i = 0; i < indentationLevel; i++) {
                        writer.write("\t"); // Use tabs for indentation
                    }

                    writer.write(line);
                    writer.newLine();

                    if (trimmedLine.endsWith("{")) {
                        indentationLevel++;
                    }
                }
            }
        }
    }
}
