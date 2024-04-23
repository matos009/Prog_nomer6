package commandLine;

import java.util.Scanner;

/**
 * Класс для стандартного ввода через консоль
 */

public class ConsoleInput implements UserInput{
    private static final Scanner userScanner = new Scanner(System.in);


    public String nextLine(){
        return userScanner.nextLine();

    }
}
