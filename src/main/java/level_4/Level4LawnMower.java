package level_4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.*;

public class Level4LawnMower {

    private static final int[] dx = {1, -1, 0, 0}; // Right, Left, Down, Up movements
    private static final int[] dy = {0, 0, 1, -1};
    private static final char[] dirChars = {'D', 'A', 'S', 'W'};

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<String> inputFiles = Arrays.asList(
                "D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level4\\level4_1.in",
                "D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level4\\level4_2.in",
                "D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level4\\level4_3.in",
                "D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level4\\level4_4.in",
                "D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level4\\level4_5.in"
        );

        System.out.println("Starting application");
        ExecutorService executor = Executors.newFixedThreadPool(inputFiles.size());
        List<Future<String>> results = new ArrayList<>();

        for (String inputFile : inputFiles) {
            System.out.println("Thread started");
            results.add(executor.submit(() -> processFile(inputFile)));
        }

        for (Future<String> result : results) {
            System.out.println(result.get());
        }

        executor.shutdown();
    }

    private static String processFile(String inputFile) throws FileNotFoundException {
        System.out.println("Processing " + inputFile);
        try (Scanner scanner = new Scanner(new FileInputStream(inputFile))) {
            int N = scanner.nextInt();  // Number of lawns
            StringBuilder output = new StringBuilder();
            output.append("Results for ").append(inputFile).append(":\n");
            for (int i = 0; i < N; i++) {
                int width = scanner.nextInt();
                int height = scanner.nextInt();
                scanner.nextLine();  // Consume the remaining newline character

                char[][] lawn = new char[height][];
                for (int j = 0; j < height; j++) {
                    lawn[j] = scanner.nextLine().toCharArray();
                }

                String result = findPathForLawn(lawn, width, height, i + 1, inputFile);
                output.append(result).append("\n");
            }
            return output.toString();
        }
    }

    private static String findPathForLawn(char[][] lawn, int width, int height, int lawnIndex, String inputFile) {
        StringBuilder path = new StringBuilder();
        boolean[][] visited = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (lawn[y][x] == '.' && dfs(lawn, visited, path, x, y, width, height, 1)) {
                    return String.format("Lawn %d in %s: %s", lawnIndex, inputFile, path.toString());
                }
                path.setLength(0);
                for (boolean[] row : visited) {
                    Arrays.fill(row, false);
                }
            }
        }
        return String.format("Lawn %d in %s: INVALID", lawnIndex, inputFile);
    }

    private static boolean dfs(char[][] lawn, boolean[][] visited, StringBuilder path, int x, int y, int width, int height, int stepCount) {
        visited[y][x] = true;
        if (stepCount == width * height - numberOfTrees(lawn, width, height)) {
            return true;
        }

        Integer[] directions = {0, 1, 2, 3};
        Arrays.sort(directions, Comparator.comparingInt(dir -> countFreeNeighbours(x + dx[dir], y + dy[dir], width, height, lawn, visited)));

        for (int dir : directions) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];
            if (isValidMove(nx, ny, width, height, lawn, visited)) {
                path.append(dirChars[dir]);
                if (dfs(lawn, visited, path, nx, ny, width, height, stepCount + 1)) {
                    return true;
                }
                path.deleteCharAt(path.length() - 1);
            }
        }

        visited[y][x] = false;
        return false;
    }

    private static int numberOfTrees(char[][] lawn, int width, int height) {
        int trees = 0;
        for (char[] row : lawn) {
            for (char c : row) {
                if (c == 'X') trees++;
            }
        }
        return trees;
    }

    private static int countFreeNeighbours(int x, int y, int width, int height, char[][] lawn, boolean[][] visited) {
        int freeNeighbours = 0;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (isValidMove(nx, ny, width, height, lawn, visited)) {
                freeNeighbours++;
            }
        }
        return freeNeighbours;
    }

    private static boolean isValidMove(int x, int y, int width, int height, char[][] lawn, boolean[][] visited) {
        return x >= 0 && x < width && y >= 0 && y < height && lawn[y][x] == '.' && !visited[y][x];
    }
}
