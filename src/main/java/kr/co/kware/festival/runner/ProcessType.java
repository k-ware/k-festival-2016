package kr.co.kware.festival.runner;

/**
 * Created by xchans on 2016. 2. 17..
 */
public enum ProcessType {
    JAVA("java"), JAVAC("javac");

    private String command;

    ProcessType(String command) {
        this.command = command;
    }

    public static ProcessType commandOf(String command) {
        for (ProcessType type : ProcessType.values()) {
            if (type.command.equals(command)) {
                return type;
            }
        }
        return null;
    }
}
