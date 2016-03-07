package kr.co.kware.festival.runner;

import org.springframework.util.StringUtils;

import java.util.concurrent.*;

/**
 * Created by xchans on 2016. 2. 17..
 */
public class JavaRunner {
    public static final String JAVA_FILE_NAME = "Main.java";
    public static final String JAVA_CLASS_NAME = "Main";

    private ExecutorService executor = Executors.newCachedThreadPool();

    public RunnerResult run(final String source, final String output) throws ExecutionException, InterruptedException {
        Future<ProcessResult> future = executor.submit(() -> {
            Processor processor = new Processor();
            processor.saveFile(source, JAVA_FILE_NAME);

            ProcessResult p = processor.runProcess("javac", JAVA_FILE_NAME);
            if (!p.hasError()) {
                p = processor.runProcess("java", JAVA_CLASS_NAME);
            }

            return p;
        });

        ProcessResult processResult;
        try {
            processResult = future.get(2000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            processResult = new ProcessResult(ProcessType.JAVA, null, -1L);
            processResult.setErrorOutput("TimeoutException");
        }

        RunnerResult runnerResult = new RunnerResult();
        runnerResult.setLastProcessResult(processResult);

        if (processResult.getType().equals(ProcessType.JAVA)
                && !StringUtils.isEmpty(processResult.getNormalOutput())
                && output.equals(processResult.getNormalOutput())) {
            runnerResult.setAnswer(true);
        }

        return runnerResult;

    }
}
