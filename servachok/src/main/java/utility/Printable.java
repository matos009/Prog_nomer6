package utility;

public interface Printable {
    void printLn(String a);
    void print(String a);
    default void printLn(String a, ConsoleColors consoleColors){
        printLn(a);
    };
    default void print(String a, ConsoleColors consoleColors){
        print(a);
    };
    void printError(String a);
}
