package kr.co.kware.festival.web;

import lombok.Data;

@Data
public class ResultVO {
    private int ranking;

    private String name;
    private String group;
    private boolean hasError = false;

    private String source;

    private String normalOutput;
    private String errorOutput;

    private long timeMillis = 0;

    private boolean isAnswer = false;
}
