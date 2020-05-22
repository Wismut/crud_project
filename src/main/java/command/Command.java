package command;


public enum Command {
    UPDATE("u"), DELETE_BY_ID("d"), SAVE("s"), GET_BY_ID("g"), GET_ALL("a"), GET_BY_CONTENT("c");

    private String shortCutLetter;

    Command(String shortCutLetter) {
        this.shortCutLetter = shortCutLetter;
    }

    public static Command getCommandByLetter(String letter) {
        if (letter == null) {
            return null;
        }
        for (Command command : values()) {
            if (letter.equals(command.shortCutLetter)) {
                return command;
            }
        }
        return null;
    }
}
