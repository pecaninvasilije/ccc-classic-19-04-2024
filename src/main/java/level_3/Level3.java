package level_3;

import fileHandler.FileHandler;

import java.io.*;
import java.util.*;

public class Level3 {

    private static final Map<Character, int[]> DIRECTIONS = new HashMap<>();
    static {
        DIRECTIONS.put('W', new int[] {0, 1});
        DIRECTIONS.put('S', new int[] {0, -1});
        DIRECTIONS.put('A', new int[] {-1, 0});
        DIRECTIONS.put('D', new int[] {1, 0});
    }

    public static String solve(String input) {
        String[] lines = input.split("\n");
        List<String> outputs = new ArrayList<>();
        int i = 1;  // skip the first line if it's not needed

        while (i < lines.length) {
            boolean valid = true;
            String[] dimensions = lines[i].split(" ");
            int width = Integer.parseInt(dimensions[0]);
            int height = Integer.parseInt(dimensions[1]);
            int start = i + 1;
            int end = i + height + 1;
            List<String> currentLines = Arrays.asList(Arrays.copyOfRange(lines, start, end + 1));
            i = end + 1;

            List<int[]> positions = new ArrayList<>();
            positions.add(new int[]{0, 0}); // starting position
            Set<List<Integer>> treePositions = new HashSet<>();

            for (int y = 0; y < height; y++) {
                String line = currentLines.get(y);
                for (int x = 0; x < line.length(); x++) {
                    if (line.charAt(x) == 'X') {
                        treePositions.add(Arrays.asList(x, height - y - 1));
                    }
                }
            }

            for (char direction : currentLines.get(height).toCharArray()) {
                int[] lastPos = positions.get(positions.size() - 1);
                int[] move = DIRECTIONS.get(direction);
                int[] newPos = {lastPos[0] + move[0], lastPos[1] + move[1]};
                positions.add(newPos);
            }

            int minX = positions.stream().min(Comparator.comparing(pos -> pos[0])).get()[0];
            int minY = positions.stream().min(Comparator.comparing(pos -> pos[1])).get()[1];
            Set<List<Integer>> visited = new HashSet<>();

            for (int[] position : positions) {
                List<Integer> pos = Arrays.asList(position[0] - minX, position[1] - minY);

                if (visited.contains(pos) || treePositions.contains(pos) || pos.get(0) < 0 || pos.get(1) < 0 ||
                        pos.get(0) >= width || pos.get(1) >= height) {
                    valid = false;
                    break;
                }
                visited.add(pos);
            }

            if (valid && visited.size() != width * height - treePositions.size()) {
                valid = false;
            }

            outputs.add(valid ? "VALID" : "INVALID");
        }

        return String.join("\n", outputs);
    }

    public static void main(String[] args) throws IOException {
        String inputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level3\\level3_5.in"; // Path to input file
        String outputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level3\\5.txt";

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
        StringBuilder inputBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            inputBuilder.append(line).append("\n");
        }

        new FileHandler(outputFile).writeFile(solve(inputBuilder.toString()), outputFile);
    }
}
