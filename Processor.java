import java.io.*;

class Processor {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java Processor <output_file>");
            return;
        }

        String outputFileName = args[0];

        try {
            processOutputFile(outputFileName);
        } catch (IOException e) {
            System.out.println("Error processing output file: " + e.getMessage());
        }
    }

    private static void processOutputFile(String outputFileName) throws IOException {
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line.trim()); // Remove leading/trailing whitespace
            }
        }

        buffer.append('$'); // Add sentinel value ($)

        String newOutputFileName = "out2.txt"; // New output file name

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newOutputFileName))) {
            writer.write(buffer.toString());
        }

        displayOutputFileContents(newOutputFileName);
    }

    private static void displayOutputFileContents(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
