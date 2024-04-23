package commandLine;

import utility.ConsoleColors;

public interface Printable {
    void print(String a);
    void printLn(String a);

    default void print(String a, ConsoleColors consoleColors){
        print(a);
    }
    default void printLn(String a, ConsoleColors consoleColors){
        printLn(a);
    }

    void printError(String s);
}
