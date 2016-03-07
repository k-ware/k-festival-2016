package kr.co.kware.festival.runner;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@Getter
@Setter
@ToString
public class ProcessResult {

    private ProcessType type;

    private int exitValue = 0;
    private long executeTimeMillis = 0;

    private String normalOutput;
    private String errorOutput;

    public ProcessResult(ProcessType type, Process process, long executeTimeMillis) {
        this.type = type;
        this.executeTimeMillis = executeTimeMillis;
        if (process != null) {
            this.exitValue = process.exitValue();

            this.normalOutput = readInputStream(process.getInputStream());
            this.errorOutput = readInputStream(process.getErrorStream());
        } else {
            this.exitValue = -1;
        }

    }

    public boolean hasError() {
        return exitValue != 0;
    }

    private String readInputStream(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        byte[] buffer = new byte[1024];
        try {
            int count = bis.read(buffer);
            for (int i = 0; i < count; i++) {
                builder.append((char) buffer[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        return builder.toString();
    }
}
