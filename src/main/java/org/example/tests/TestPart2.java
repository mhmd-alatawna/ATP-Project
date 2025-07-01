package org.example.tests;


import org.example.Client.*;
import org.example.IO.MyCompressorOutputStream;
import org.example.IO.MyDecompressorInputStream;
import org.example.IO.SimpleCompressorOutputStream;
import org.example.IO.SimpleDecompressorInputStream;
import org.example.Server.Server;
import org.example.Server.ServerStrategyGenerateMaze;
import org.example.Server.ServerStrategySolveSearchProblem;
import org.example.algorithms.mazeGenerators.AMazeGenerator;
import org.example.algorithms.mazeGenerators.Maze;
import org.example.algorithms.mazeGenerators.MyMazeGenerator;
import org.example.algorithms.search.AState;
import org.example.algorithms.search.Solution;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.*;  // ✅ already includes ExecutorService, Future, TimeoutException, etc.


public class TestPart2 {

    private static final String resultsFilePath = "./results.txt";
    private static final String logFilePath = "results.log";
    private static int Port_ServerMazeGenerating = getRandomNumber(5000, 6000);
    private static int Port_ServerSearchProblemSolver = getRandomNumber(6001, 7000);
    private static int total_test = 0;
    private static int total_pass = 0;
    private static double avg_comp;
    private static final int ServerListeningIntervalMS = 1000;
    //<editor-fold desc="General">
    public static enum TestStatus {
        Passed, Failed;
    }

    private static String getTestStatusString(boolean testPassed) {
        return testPassed ? TestStatus.Passed.toString() : TestStatus.Failed.toString();
    }

    private static void appendToFile(String text, String filePath){
        try (java.io.FileWriter fw = new java.io.FileWriter(filePath, true)) {
            fw.write(text + "\r\n");
        } catch (IOException ex) {
            System.out.println(String.format("Error appending text to file: %s", filePath));
        }
    }

    private static void appendToResultsFile(String text){
        appendToFile(text, resultsFilePath);
    }

    private static void appendToLogFile(String text){
        appendToFile(text, logFilePath);
    }

    private static void appendToResultsAndLogFiles(String text) {
        appendToResultsFile(text);
        appendToLogFile(text);
    }

    private static void appendExceptionToFile(Exception e, String filePath) {
        String message = e.getMessage();
        if (message == null)
        {
            message =  String.valueOf(e);
        }
        appendToFile(String.format("Exception: %s", message),filePath);
        if (e.getStackTrace().length > 1)
        {
            for (int i=0; i< e.getStackTrace().length; i++){
                String msg = String.valueOf(e.getStackTrace()[i]);
                appendToFile(String.format("Exception Stack Trace: %s", msg), filePath);
            }
        }
        else
        {
            int x = 1;
        }
    }
    //</editor-fold>
    private static int getRandomNumber(int from, int to) {
        if (from < to)
            return from + new Random().nextInt(Math.abs(to - from));
        return from - new Random().nextInt(Math.abs(to - from));
    }

    private static Integer getFreePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }

    public static int[][] getRowsColumnsCombinations() {
        int[][] rowsColumnsCombinations = {
                {5, 5},
                {20, 50},
                {50, 20},
                {100, 100}

        };
        return rowsColumnsCombinations;
    }

    public static void main(String[] args) {
        ArrayList<TestEntry> testEntries = new ArrayList<>();

        // Check config first
        boolean configValid = checkConfigFile();  // This was missing in your version

        try {
            appendToResultsFile("Running");

            appendToResultsFile("Start Test_CompressDecompressMaze_SimpleCompressor");
            runSimpleCompressorTests(testEntries);
            appendToResultsFile("Finished Test_CompressDecompressMaze_SimpleCompressor");

            appendToResultsFile("Start Test_CompressDecompressMaze_MyCompressor");
            runMyCompressorTests(testEntries);
            appendToResultsFile("Finished Test_CompressDecompressMaze_MyCompressor");

            appendToResultsFile("Start Test_CommunicateWithServers");
            runCommunicateTests(testEntries);
            appendToResultsFile("Finished Test_CommunicateWithServers");

            // Count passed tests
            int passed = 0;
            for (TestEntry entry : testEntries) {
                if (entry.score > 0.0) passed++;
                else if( entry.score < 0.0) {
                    appendToResultsFile("❌ Test failed: " + entry.label + " - " + entry.reason);
                }
            }

            // Final grade
            // Final grade
// Final grade
            double score = configValid ? 2.0 : 0.0;

            System.out.println("---- Score breakdown ----");
            for (TestEntry entry : testEntries) {
                System.out.println(entry.category + " | " + entry.label + " | score=" + entry.score);
                if (entry.score > 0.0) {
                    score += entry.score;
                }
            }
            System.out.println("Total computed score: " + score);
            String studentId = "unknown";

// Create and write the result JSON
// TestResult result = new TestResult(studentId, configValid, testEntries, score);



            // Get student ID from command line property
            // String studentId = "unknown";

            // Create and write the result JSON
            TestResult result = new TestResult(studentId, configValid, testEntries, score);
            writeResultAsJson(result);
            System.out.println("Results written to student_score.json");
            appendToResultsFile("Done");

        } catch (Exception e) {
            e.printStackTrace();
            appendToLogFile("Fatal error in main: " + e.getMessage());
        }
    }


    private static boolean checkConfigFile() {
        String[] possibleNames = {"config.properties", "properties.config"};
        String[] requiredKeys = {
                "threadPoolSize",
                "mazeGeneratingAlgorithm",
                "mazeSearchingAlgorithm"
        };

        for (String resourceName : possibleNames) {
            try (InputStream input = TestPart2.class.getClassLoader().getResourceAsStream(resourceName)) {
                if (input == null) continue;  // Try next file

                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                ArrayList<String> foundKeys = new ArrayList<>();
                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue; // skip comments and blanks
                    int eqIndex = line.indexOf('=');
                    if (eqIndex != -1) {
                        String key = line.substring(0, eqIndex).trim();
                        foundKeys.add(key);
                    }
                }

                // Check how many required keys are found
                int matchCount = 0;
                for (String required : requiredKeys) {
                    if (foundKeys.contains(required)) {
                        matchCount++;
                    }
                }

                if (matchCount == requiredKeys.length) {
                    appendToResultsFile("✅ Valid config found: " + resourceName);
                    return true;
                } else {
                    appendToResultsFile("❌ " + resourceName + " found but missing required keys.");
                    for (String required : requiredKeys) {
                        if (!foundKeys.contains(required)) {
                            appendToResultsFile("   - Missing key: " + required);
                        }
                    }
                    return false;
                }

            } catch (IOException e) {
                appendToLogFile("⚠️ Error reading " + resourceName + ": " + e.getMessage());
            }
        }

        appendToResultsFile("❌ No valid config file found (checked: config.properties, properties.config).");
        return false;
    }



    private static void writeResultAsJson(TestResult result) {
        try (FileWriter writer = new FileWriter("student_score.json")) {
            writer.write("{\n");
            writer.write("  \"student_id\": \"" + escapeJson(result.student_id) + "\",\n");
            writer.write("  \"config_valid\": " + result.config_valid + ",\n");

            writer.write("  \"test_results\": [\n");
            for (int i = 0; i < result.test_results.length; i++) {
                TestEntry entry = result.test_results[i];
                writer.write("    {\n");
                writer.write("      \"category\": \"" + escapeJson(entry.category) + "\",\n");
                writer.write("      \"label\": \"" + escapeJson(entry.label) + "\",\n");
                writer.write("      \"score\": " + entry.score + ",\n");
                writer.write("      \"reason\": \"" + escapeJson(entry.reason) + "\"");

                if (entry.compression_ratio != null) {
                    writer.write(",\n      \"compression_ratio\": " + entry.compression_ratio);
                }
                writer.write("\n    }");

                if (i < result.test_results.length - 1) writer.write(",");
                writer.write("\n");
            }
            writer.write("  ],\n");

            writer.write("  \"final_score\": " + result.final_score + "\n");
            writer.write("}\n");

        } catch (IOException e) {
            appendToLogFile("Failed to write JSON manually: " + e.getMessage());
        }
    }
    private static String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    private static void runSimpleCompressorTests(ArrayList<TestEntry> testEntries) {
        int[][] sizes = getRowsColumnsCombinations();
        for (int i = 0; i < sizes.length; i++) {
            int rows = sizes[i][0];
            int cols = sizes[i][1];
            String label = rows + "x" + cols;

            try {
                Maze maze = new MyMazeGenerator().generate(rows, cols);
                byte[] original = maze.toByteArray();

                try (OutputStream out = new SimpleCompressorOutputStream(new FileOutputStream("savedMaze.maze"))) {
                    out.write(original);
                }

                byte[] loaded = new byte[original.length];
                try (InputStream in = new SimpleDecompressorInputStream(new FileInputStream("savedMaze.maze"))) {
                    in.read(loaded);
                }

                Maze loadedMaze = new Maze(loaded);

                if (Arrays.equals(original, loadedMaze.toByteArray())) {
                    testEntries.add(new TestEntry("SimpleCompressor", label, 4.25, "ok"));
                } else {
                    testEntries.add(new TestEntry("SimpleCompressor", label, 0.0, "mismatch"));
                }

            } catch (Exception e) {
                testEntries.add(new TestEntry("SimpleCompressor", label,-1, e.toString()));
                appendExceptionToFile(e, resultsFilePath);
            }
        }
    }




    private static void runMyCompressorTests(ArrayList<TestEntry> testEntries) {
        int[][] sizes = getRowsColumnsCombinations();
        double[] compressRatesOut = new double[sizes.length];

        for (int i = 0; i < sizes.length; i++) {
            int rows = sizes[i][0];
            int cols = sizes[i][1];
            String label = rows + "x" + cols;

            try {
                appendToResultsFile("Testing MyCompressor: " + label);

                String mazeFileName = "savedMaze.maze";
                AMazeGenerator generator = new MyMazeGenerator();
                Maze maze = generator.generate(rows, cols);
                byte[] originalBytes = maze.toByteArray();

                // Compress with MyCompressor
                try (OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName))) {
                    out.write(originalBytes);
                }

                // Decompress
                byte[] loadedBytes = new byte[originalBytes.length];
                try (InputStream in = new MyDecompressorInputStream(new FileInputStream(mazeFileName))) {
                    in.read(loadedBytes);
                }

                Maze loadedMaze = new Maze(loadedBytes);

                // Compare
                boolean passed = Arrays.equals(loadedMaze.toByteArray(), originalBytes);
                double score = passed ? 6.5 : 0.0;
                String reason = passed ? "ok" : "mismatch";
                // Calculate compression ratio
                File compressedFile = new File(mazeFileName);
                double compressedSize = compressedFile.length();
                double originalSize = originalBytes.length;
                double compressionRate = compressedSize / originalSize;
                compressRatesOut[i] = compressionRate;

                testEntries.add(new TestEntry(
                        "MyCompressor",
                        label,
                        passed ? 6.5 : 0.0,
                        passed ? "ok" : "mismatch",
                        compressionRate  // ⬅️ only MyCompressor uses this
                ));
                if (!passed) {
                    appendToResultsFile("[Test MC " + label + "] ❌ Maze mismatch");
                }


            } catch (Exception e) {
                testEntries.add(new TestEntry("MyCompressor", label, -1, e.toString()));
                appendToResultsFile("[Test MC " + label + "] ❌ Exception");
                appendExceptionToFile(e, resultsFilePath);
            }
        }

        // Log average compression rate
        double total = 0;
        for (double rate : compressRatesOut) total += rate;
        double avg = total / compressRatesOut.length;
        appendToResultsFile("[AVG- MyCompressor Ratio: " + avg + "]");
    }

    private static void runCommunicateTests(ArrayList<TestEntry> testEntries) {
        int[] counters = {4, 6, 8, 10};

        for (int counter : counters) {
            Port_ServerMazeGenerating = getRandomNumber(5000, 6000);
            Port_ServerSearchProblemSolver = getRandomNumber(6001, 7000);

            try {
                Server mazeGeneratingServer = new Server(Port_ServerMazeGenerating, 1000, new ServerStrategyGenerateMaze());
                Server solveSearchProblemServer = new Server(Port_ServerSearchProblemSolver, 1000, new ServerStrategySolveSearchProblem());

                mazeGeneratingServer.start();
                solveSearchProblemServer.start();
                appendToResultsFile("Started servers for counter: " + counter);

                // Maze Generating Test
                boolean mazeGenPassed = CommunicateWithServer_MazeGenerating(counter);
                testEntries.add(new TestEntry("Communicate", "Generate_" + counter, mazeGenPassed ? 6.5 : 0.0,
                        mazeGenPassed ? "ok" : "fail"));

                // Maze Solving Test
                boolean solvePassed = CommunicateWithServer_SolveSearchProblem(counter);
                testEntries.add(new TestEntry("Communicate", "Solve_" + counter, solvePassed ? 6.5 : 0.0,
                        solvePassed ? "ok" : "fail"));

                mazeGeneratingServer.stop();
                solveSearchProblemServer.stop();

            } catch (Exception e) {
                testEntries.add(new TestEntry("Communicate", "Generate_" + counter, -1, e.toString()));
                testEntries.add(new TestEntry("Communicate", "Solve_" + counter, -1, e.toString()));
                appendExceptionToFile(e, resultsFilePath);
            }
        }
    }


    //<editor-fold desc="Test_CompressDecompressMaze">
    private static void Test_CompressDecompressMaze_MyCompressor() throws Exception{
        int[][] rowsColumnsCombinations = getRowsColumnsCombinations();
        double[] compresses = new double[rowsColumnsCombinations.length];
        int rows;
        int columns;
        for (int i = 0; i < rowsColumnsCombinations.length; i++) {
            rows = rowsColumnsCombinations[i][0];
            columns = rowsColumnsCombinations[i][1];
            System.out.println(String.valueOf(rows) + "_" + String.valueOf(columns));
            appendToResultsFile(String.valueOf(rows) + "_" + String.valueOf(columns));
//            appendToResultsFile(String.valueOf("[" + String.valueOf(rows) + "_" + String.valueOf(columns) + "]"));
            String mazeFileName = "savedMaze.maze";
            AMazeGenerator mazeGenerator = new MyMazeGenerator();
            Maze maze = mazeGenerator.generate(rows, columns); //Generate new maze
            double mazeOriginalSize = maze.toByteArray().length;
            try {
                OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
                out.write(maze.toByteArray());
                out.flush();
                out.close();
            } catch (IOException e) {
                appendToResultsFile(String.valueOf("[" + String.valueOf(rows) + "_" + String.valueOf(columns) + "_CMP_OS_E]"));
                appendToResultsFile(e.getMessage());
            }

            byte savedMazeBytes[] = new byte[0];
            try {
                InputStream in = new MyDecompressorInputStream(new FileInputStream(mazeFileName));
                savedMazeBytes = new byte[maze.toByteArray().length];
                in.read(savedMazeBytes);
                in.close();
            } catch (IOException e) {
                appendToResultsFile(String.valueOf("[" + String.valueOf(rows) + "_" + String.valueOf(columns) + "_CMP_IS_E]"));
                appendToResultsFile(e.getMessage());
            }
            File compressed = new File("savedMaze.maze");
            double current_comp = compressed.length();
            Maze loadedMaze = new Maze(savedMazeBytes);
            boolean areMazesEquals = Arrays.equals(loadedMaze.toByteArray(), maze.toByteArray());
            if (areMazesEquals != true) {
                appendToResultsFile(String.valueOf("[" + String.valueOf(rows) + "_" + String.valueOf(columns) + "_CMP_NotEqual]"));
            }
            compresses[i] = current_comp;
        }
        double total = 0;
        int length = compresses.length;
        for (int i = 0; i < compresses.length; i++) {
            total = total + compresses[i];
        }
        appendToResultsFile("[AVG- MyCompressor: " + String.valueOf(total / length)+"]");
    }

    //</editor-fold>

    //<editor-fold desc="Test_CommunicateWithServers">
    private static void Test_CommunicateWithServers() throws Exception {
        //Initializing servers
        for (int counter = 4; counter <= 10; counter = counter + 2)
        {
            Port_ServerMazeGenerating = getRandomNumber(5000, 6000);
            Port_ServerSearchProblemSolver = getRandomNumber(6001, 7000);
            try {
                Server mazeGeneratingServer = new Server(Port_ServerMazeGenerating, 1000, new ServerStrategyGenerateMaze());
                Server solveSearchProblemServer = new Server(Port_ServerSearchProblemSolver, 1000, new ServerStrategySolveSearchProblem());

                //Starting  servers
                solveSearchProblemServer.start();
                mazeGeneratingServer.start();
                System.out.println("started servers");
                appendToResultsFile("communicate with server- Maze Generating");
                CommunicateWithServer_MazeGenerating(counter);
                appendToResultsFile("communicate with server- Solve Search Problem");
                CommunicateWithServer_SolveSearchProblem(counter);

                //Stopping all servers
                mazeGeneratingServer.stop();
                solveSearchProblemServer.stop();
            } catch (Exception e) {
                appendToResultsFile(String.valueOf("[TCWS_E]"));
                appendExceptionToFile(e, resultsFilePath);
            }

        }
    }




    private static boolean CommunicateWithServer_MazeGenerating(int i) throws Exception {
        AtomicInteger testsPassed = new AtomicInteger(0);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            try {
                new Client(InetAddress.getLocalHost(), Port_ServerMazeGenerating, new IClientStrategy() {
                    @Override
                    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                        try {
                            total_test++;
                            int size = 50 * i;
                            System.out.println("MazeGen size: " + size);
                            appendToResultsFile("MazeGen size: " + size);

                            ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                            toServer.flush();

                            int[] mazeDimensions = new int[]{size, size};
                            toServer.writeObject(mazeDimensions); // send maze dimensions to server
                            toServer.flush();

                            ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                            byte[] compressedMaze = (byte[]) fromServer.readObject(); // may hang
                            InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                            byte[] decompressedMaze = new byte[1000000];
                            is.read(decompressedMaze);

                            Maze maze = new Maze(decompressedMaze);
                            if (maze.toByteArray().length > 1000) {
                                testsPassed.incrementAndGet();
                            } else {
                                appendToResultsFile("[CG_FAIL]");
                            }
                        } catch (Exception e) {
                            appendToResultsFile("[CG_E-client]");
                            appendExceptionToFile(e, resultsFilePath);
                        }
                    }
                }).communicateWithServer();
            } catch (Exception e) {
                appendToResultsFile("[CG_E-wrapper]");
                appendExceptionToFile(e, resultsFilePath);
            }
        });

        try {
            future.get(20, TimeUnit.SECONDS); // timeout after 20 seconds
        } catch (TimeoutException e) {
            appendToResultsFile("[CG_TIMEOUT]");
            future.cancel(true);
        } catch (Exception e) {
            appendToResultsFile("[CG_E-future]");
            appendExceptionToFile(e, resultsFilePath);
        } finally {
            executor.shutdownNow();
        }

        total_pass += testsPassed.get();
        return testsPassed.get() > 0;
    }


    private static boolean CommunicateWithServer_SolveSearchProblem(int i) throws Exception {
        AtomicInteger testsPassed = new AtomicInteger(0);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            try {
                new Client(InetAddress.getLocalHost(), Port_ServerSearchProblemSolver, new IClientStrategy() {
                    @Override
                    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                        try {
                            total_test++;
                            int size = (int) (50 * (i + 1));
                            appendToResultsFile("Solve size: " + size);

                            ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                            toServer.flush();

                            MyMazeGenerator mg = new MyMazeGenerator();
                            Maze maze = mg.generate(size, size);

                            toServer.writeObject(maze); // send maze to server
                            toServer.flush();

                            ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                            Solution mazeSolution = (Solution) fromServer.readObject(); // may block
                            ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();

                            if (!mazeSolutionSteps.isEmpty()) {
                                testsPassed.incrementAndGet();
                            } else {
                                appendToResultsFile("[CS_EM]"); // Empty solution
                            }
                        } catch (Exception e) {
                            appendToResultsFile("[CS_E-client]");
                            appendToResultsFile(e.getMessage());
                        }
                    }
                }).communicateWithServer();
            } catch (Exception e) {
                appendToResultsFile("[CS_E-wrapper]");
                appendExceptionToFile(e, resultsFilePath);
            }
        });

        try {
            future.get(20, TimeUnit.SECONDS); // timeout after 20 seconds
        } catch (TimeoutException e) {
            appendToResultsFile("[CS_TIMEOUT]");
            future.cancel(true); // kill thread
        } catch (Exception e) {
            appendToResultsFile("[CS_E-future]");
            appendExceptionToFile(e, resultsFilePath);
        } finally {
            executor.shutdownNow();
        }

        total_pass += testsPassed.get();
        return testsPassed.get() > 0;
    }

}