/**
 * Created by Yiannis Kiranis <yiannis.kiranis@gmail.com>
 * https://apps4net.eu
 * Date: 23/2/23
 * Time: 5:53 μ.μ.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextFileReplicator {

    public static void main(String[] args) {
        String inputFilePath = args[0]; // input file path
        String outputFilePath = args[1]; // output file path
        long desiredSize = Long.parseLong(args[2]); // desired file size in bytes

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            // Copy lines with # in the beginning of the file
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) {
                    writer.write(line + "\n");
                } else {
                    break;
                }
            }

            // Read all valid lines into a list
            List<String> validLines = new ArrayList<>();
            if (line != null) {
                validLines.add(line); // add the first valid line that was not copied to the list
            }
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("#")) {
                    validLines.add(line);
                }
            }

            // Replicate valid lines in random order until the desired file size is reached
            long currentSize = 0;
            while (currentSize < desiredSize) {
                Collections.shuffle(validLines); // shuffle the list
                for (String validLine : validLines) {
                    writer.write(validLine + "\n");
                    currentSize += (validLine.length() + 1); // add line length plus newline character to current size
                    if (currentSize >= desiredSize) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

