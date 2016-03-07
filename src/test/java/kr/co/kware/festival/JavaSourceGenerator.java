package kr.co.kware.festival;

import kr.co.kware.festival.repository.QuestionRepository;

public class JavaSourceGenerator {
    private static String BASE_SOURCE = "public class Main {" +
            "    public static void main(String[] arg) {" +
            "        System.out.print(\"{TARGET}\");" +
            "    }" +
            "}";


    public static String generateSource(int questionId) {
        return generateSource(QuestionRepository.getAnswer(questionId));
    }

    public static String generateErrorSource(int questionId) {
        return generateErrorSource(QuestionRepository.getAnswer(questionId));
    }

    public static String generateSource(String output) {
        return getReplacedString(output);
    }

    public static String generateErrorSource(String output) {
        return getReplacedString(output).substring(10);
    }

    private static String getReplacedString(String output) {
        return BASE_SOURCE.replace("{TARGET}", replaceNewLine(output));
    }

    private static String replaceNewLine(String output) {
        return output.replace("\n", "\\n");
    }

    public static String getValidSample() {
        return VALID_SAMPLE;
    }

    public static String getTimeoutSample() {
        return TIMEOUT_SAMPLE;
    }

    private static String TIMEOUT_SAMPLE = "public class Main {\n" +
            "    public static void main(String[] args) {\n" +
            "        try {\n" +
            "            Thread.sleep(3000);\n" +
            "        } catch (InterruptedException e) {\n" +
            "            e.printStackTrace();\n" +
            "        }\n" +
            "    }\n" +
            "}";

    private static String VALID_SAMPLE = "public class Main {\n" +
            "    public static void main(String[] args) {\n" +
            "        NQueens1 nQueens;\n" +
            "        for (int i = 1; i < 14; i++) {\n" +
            "            nQueens = new NQueens1(i);\n" +
            "            int results = nQueens.callPlaceNQueens();\n" +
            "\n" +
            "            System.out.printf(\"n = %02d, solution count is %d.\\n\", i, results);\n" +
            "        }\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "class NQueens1 {\n" +
            "\n" +
            "    private int[] x;\n" +
            "    private int result;\n" +
            "\n" +
            "    public NQueens1(int n) {\n" +
            "        x = new int[n];\n" +
            "        result = 0;\n" +
            "    }\n" +
            "\n" +
            "    private boolean canPlaceQueen(int r, int c) {\n" +
            "        /**\n" +
            "         * Returns TRUE if a queen can be placed in row r and column c.\n" +
            "         * Otherwise it returns FALSE. x[] is a global array whose first (r-1)\n" +
            "         * values have been set.\n" +
            "         */\n" +
            "        for (int i = 0; i < r; i++) {\n" +
            "            if (x[i] == c || (i - r) == (x[i] - c) ||(i - r) == (c - x[i])) {\n" +
            "                return false;\n" +
            "            }\n" +
            "        }\n" +
            "        return true;\n" +
            "    }\n" +
            "\n" +
            "    private void printQueens(int[] x) {\n" +
            "        int N = x.length;\n" +
            "        for (int i = 0; i < N; i++) {\n" +
            "            for (int j = 0; j < N; j++) {\n" +
            "                if (x[i] == j) {\n" +
            "                    System.out.print(\"Q \");\n" +
            "                } else {\n" +
            "                    System.out.print(\"* \");\n" +
            "                }\n" +
            "            }\n" +
            "            System.out.println();\n" +
            "        }\n" +
            "        System.out.println();\n" +
            "    }\n" +
            "\n" +
            "    private void placeNQueens(int r, int n) {\n" +
            "        /**\n" +
            "         * Using backtracking this method prints all possible placements of n\n" +
            "         * queens on an n x n chessboard so that they are non-attacking.\n" +
            "         */\n" +
            "        for (int c = 0; c < n; c++) {\n" +
            "            if (canPlaceQueen(r, c)) {\n" +
            "                x[r] = c;\n" +
            "                if (r == n - 1) {\n" +
            "                    //printQueens(x);\n" +
            "                    result++;\n" +
            "                } else {\n" +
            "                    placeNQueens(r + 1, n);\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    public int callPlaceNQueens() {\n" +
            "        placeNQueens(0, x.length);\n" +
            "\n" +
            "        return result;\n" +
            "    }\n" +
            "}";
}
