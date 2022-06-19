package Utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileHandler {
    public static List<String> readFile(String fileName) throws Exception {
        // ref: https://mkyong.com/java8/java-8-stream-read-a-file-line-by-line/
        List<String> fileLines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(fileLines::add);
        } catch (IOException e) {
            throw new Exception("Unable to read file.");
        }
        return fileLines;
    }
}
