package fileHandler;

import java.io.*;

public class FileHandler {
    private String filename;

    public FileHandler(String filename) {
        this.filename = filename;
    }

    public String readFile() {
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public void writeFile(String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}