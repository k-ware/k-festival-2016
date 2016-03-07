package kr.co.kware.festival.repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xchans on 2016. 2. 17..
 */
public class QuestionRepository {
    private static final Map<Integer, String> questionMap = new HashMap<>();
    private static final Map<Integer, String> answerMap = new HashMap<>();

    static {
        questionMap.put(1, "test");
        answerMap.put(1, "1234567890\n");

        questionMap.put(2, "n-Queen 문제");
        answerMap.put(2,
            "n = 01, solution count is 1.\n" +
            "n = 02, solution count is 0.\n" +
            "n = 03, solution count is 0.\n" +
            "n = 04, solution count is 2.\n" +
            "n = 05, solution count is 10.\n" +
            "n = 06, solution count is 4.\n" +
            "n = 07, solution count is 40.\n" +
            "n = 08, solution count is 92.\n" +
            "n = 09, solution count is 352.\n" +
            "n = 10, solution count is 724.\n" +
            "n = 11, solution count is 2680.\n" +
            "n = 12, solution count is 14200.\n" +
            "n = 13, solution count is 73712.\n"
        );
    }

    public static void add(String question, String answer) {
        int id = questionMap.size();
        questionMap.put(id, question);
        answerMap.put(id, answer);
    }

    public static String getAnswer(int questionId) {
        return answerMap.get(questionId);
    }

}
