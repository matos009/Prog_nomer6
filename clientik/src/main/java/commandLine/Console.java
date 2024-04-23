package commandLine;

import utility.ConsoleColors;

public class Console implements Printable{

    private static boolean fileMode = false;


    public static boolean isFileMode(){
        return fileMode;
    }

    public static void setFileMode(boolean fileMode){
        Console.fileMode = fileMode;
    }

    public void printLn(String s){
        System.out.println(s);
    }
    public void printLn(String s, ConsoleColors consoleColors){
        this.printLn(ConsoleColors.toColor(s, consoleColors));
    }

    public void print(String s){
        System.out.print(s);
    }
    public void print(String s, ConsoleColors consoleColors){
        this.print(ConsoleColors.toColor(s, consoleColors));
    }

    public void printError(String s){
        System.out.println(ConsoleColors.RED + s + ConsoleColors.RESET);
    }
}
