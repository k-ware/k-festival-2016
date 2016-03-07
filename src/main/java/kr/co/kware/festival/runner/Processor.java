package kr.co.kware.festival.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class Processor {
    private static final String BASE_PATH = "./processor/";

    private final String runPath;

    public Processor() {
        this.runPath = getTempRandomPath();
    }

    private String getTempRandomPath() {
        return BASE_PATH + UUID.randomUUID().toString();
    }

    public boolean saveFile(String content, String fileName) throws IOException {
        File javaFile = new File(runPath, fileName);
        javaFile.getParentFile().mkdirs();
        javaFile.createNewFile();

        try (BufferedWriter fw = new BufferedWriter(new FileWriter(javaFile))) {
            fw.write(content);
        }

        return javaFile.exists();
    }

    public boolean deleteFile() {
        File dir = new File(runPath);
        return dir.delete();
    }

    public ProcessResult runProcess(String... command) throws IOException, InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ProcessBuilder pb = new ProcessBuilder();
        Process p = pb.directory(new File(runPath)).command(command).start();
        p.waitFor();

        stopWatch.stop();

        ProcessResult result = new ProcessResult(ProcessType.commandOf(command[0]), p, stopWatch.getLastTaskTimeMillis());

        p.destroy();

        return result;
    }

}
