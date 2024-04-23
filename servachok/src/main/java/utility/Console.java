package utility;

public class Console implements Printable{

    private static boolean fileMode = false;

    public static boolean isFileMode() {
        return fileMode;
    }

    public static void setFileMode(boolean fileMode) {
        Console.fileMode = fileMode;
    }

    @Override
    public void printLn(String a) {
        System.out.println(a);
    }

    @Override
    public void printLn(String a, ConsoleColors consoleColors) {
        this.printLn(ConsoleColors.toColor(a, consoleColors));
    }

    @Override
    public void print(String a, ConsoleColors consoleColors) {
        this.print(ConsoleColors.toColor(a, consoleColors));
    }

    @Override
    public void print(String a) {
        System.out.print(a);
    }

    @Override
    public void printError(String a) {
        System.out.println(ConsoleColors.RED + a + ConsoleColors.RESET);
    }
}
