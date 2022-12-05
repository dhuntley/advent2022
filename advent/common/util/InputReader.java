package advent.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputReader {

    public static Integer parseInt(String line) {
        return line == null || line.isBlank() || line.isEmpty() ? null : Integer.parseInt(line);
    }

    public static List<String> readLinesFromInput(String fileName) {
        ArrayList<String> lines = new ArrayList<>();
        
        File inputFile = new File(fileName);

        try (Scanner inputScanner = new Scanner(inputFile)) {
            while (inputScanner.hasNextLine()) {
                lines.add(inputScanner.nextLine());
            }
        } catch(FileNotFoundException e) {
            System.err.println("Error reading from input file: " + e.getMessage());
            System.exit(1);
        }

        return lines;
    }

    public static List<Integer> readIntegersFromInput(String fileName) {
        List<String> lines = readLinesFromInput(fileName);
        return lines.stream().map(InputReader::parseInt).collect(Collectors.toList());
    }
}
