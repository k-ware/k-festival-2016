package kr.co.kware.festival.runner;

import kr.co.kware.festival.JavaSourceGenerator;
import kr.co.kware.festival.repository.QuestionRepository;
import org.junit.Test;

import java.util.concurrent.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class JavaRunnerTester4Timeout {
    private static final String JAVA_FILE_NAME = "Main.java";
    private static final String JAVA_CLASS_NAME = "Main";

    private static final String RESULT = QuestionRepository.getAnswer(2);

    private final Processor processor = new Processor();



    @Test
    public void allTestAndThreadTestAndTimeout() throws ExecutionException, InterruptedException {
        JavaRunner runner = new JavaRunner();
        RunnerResult result = runner.run(JavaSourceGenerator.getTimeoutSample(), RESULT);

        assertThat(result.getLastProcessResult().getExitValue(), is(-1));
        assertThat(result.getLastProcessResult().getExecuteTimeMillis(), is(-1L));
        assertThat(result.isAnswer(), not(true));
    }


}
