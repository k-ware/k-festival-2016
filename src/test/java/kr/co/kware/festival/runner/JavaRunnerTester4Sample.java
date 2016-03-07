package kr.co.kware.festival.runner;

import kr.co.kware.festival.JavaSourceGenerator;
import kr.co.kware.festival.repository.QuestionRepository;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.concurrent.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class JavaRunnerTester4Sample {
    private static final String JAVA_FILE_NAME = "Main.java";
    private static final String JAVA_CLASS_NAME = "Main";

    private static final String RESULT = QuestionRepository.getAnswer(2);

    private final Processor processor = new Processor();

    @Test
    public void saveJavaFile() throws IOException {
        assertThat(processor.saveFile(JavaSourceGenerator.getValidSample(), JAVA_FILE_NAME), is(true));
    }

    @Test
    public void saveJavaFileAndCompile() throws IOException, InterruptedException {
        processor.saveFile(JavaSourceGenerator.getValidSample(), JAVA_FILE_NAME);

        ProcessResult p = processor.runProcess("javac", JAVA_FILE_NAME);

        System.out.println(p);

        assertThat(p.getExitValue(), is(0));
    }

    @Test
    public void saveJavaFileAndCompileError() throws IOException, InterruptedException {
        processor.saveFile(JavaSourceGenerator.getValidSample().substring(10), JAVA_FILE_NAME);

        ProcessResult p = processor.runProcess("javac", JAVA_FILE_NAME);

        System.out.println("[][]" + p);

        assertThat(p.getExitValue(), is(1));
        assertThat(p.getErrorOutput(), notNullValue());
    }

    @Test
    public void saveJavaFileAndCompileAndRun() throws IOException, InterruptedException {
        processor.saveFile(JavaSourceGenerator.getValidSample(), JAVA_FILE_NAME);

        ProcessResult p = processor.runProcess("javac", JAVA_FILE_NAME);
        if (p.getExitValue() == 0) {
            p = processor.runProcess("java", JAVA_CLASS_NAME);

            System.out.println(">>>>" + p);
            assertThat(p.getExitValue(), is(0));
            assertThat(p.getNormalOutput(), is(RESULT));
        }

    }

    @Test
    public void allTest() throws IOException, InterruptedException {
        processor.saveFile(JavaSourceGenerator.getValidSample(), JAVA_FILE_NAME);

        ProcessResult p = processor.runProcess("javac", JAVA_FILE_NAME);
        if (p.getExitValue() == 0) {
            p = processor.runProcess("java", JAVA_CLASS_NAME);
        }

//        System.out.printf("%d ms", p.getExecuteTimeMillis());

        assertThat(p.getExitValue(), is(0));
        assertThat(p.getExecuteTimeMillis(), greaterThan(0L));
    }

    @Test
    public void allTestAndThreadTest() throws ExecutionException, InterruptedException {
        JavaRunner runner = new JavaRunner();
        RunnerResult result = runner.run(JavaSourceGenerator.getValidSample(), RESULT);

        assertThat(result.getLastProcessResult().getExitValue(), is(0));
        assertThat(result.getLastProcessResult().getExecuteTimeMillis(), greaterThan(0L));
        assertThat(result.isAnswer(), is(true));
    }

    @Test
    public void allTestAndThreadTestAndNotAnswer() throws ExecutionException, InterruptedException {
        JavaRunner runner = new JavaRunner();
        RunnerResult result = runner.run(JavaSourceGenerator.getValidSample(), RESULT + "11");

        assertThat(result.getLastProcessResult().getExitValue(), is(0));
        assertThat(result.getLastProcessResult().getExecuteTimeMillis(), greaterThan(0L));
        assertThat(result.isAnswer(), not(true));
    }

    @Test(expected = ExecutionException.class)
    public void allTestAndErrorAndThreadTest() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Long> future = executor.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                StopWatch stopWatch = new StopWatch();
                processor.saveFile(JavaSourceGenerator.getValidSample().substring(10), JAVA_FILE_NAME);

                ProcessResult p = processor.runProcess("javac", JAVA_FILE_NAME);
                if (p.getExitValue() == 0) {
                    stopWatch.start();
                    p = processor.runProcess("java", JAVA_CLASS_NAME);
                    stopWatch.stop();
                }

//                System.out.printf("%d ms", stopWatch.getLastTaskInfo().getTimeMillis());

                return stopWatch.getLastTaskInfo().getTimeMillis();

            }
        });

        Long result = future.get();

        assertThat(result, not(0));
    }

}
