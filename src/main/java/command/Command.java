package command;


public enum Command {
    UPDATE("u"), DELETE_BY_ID("d"), SAVE("s"), GET_BY_ID("g"), GET_ALL("a");

    public String shortCutLetter;

    Command(String shortCutLetter) {
        this.shortCutLetter = shortCutLetter;
    }

    public static Command getCommandByLetter(String letter) {
        return valueOf(letter);
    }
}
