public class Command {
    private String name;
    private String arg;
    private LoopData data;
    public Command(String name, String arg) {
        this.name = name;
        this.arg = arg;
    }

    public Command(String name, LoopData arg) {
        this.name = name;
        this.data = arg;
    }

    public String getName() {
        return name;
    }

    public String getArg() {
        return arg;
    }

    public LoopData getData() {
        return data;
    }
}
