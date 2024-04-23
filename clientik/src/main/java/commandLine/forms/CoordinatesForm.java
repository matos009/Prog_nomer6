package commandLine.forms;

import commandLine.*;
import exceptions.ExceptionInFileMode;
import models.Coordinates;
import models.Coordinates;
import utility.*;

/**
 * Форма для формы обучения
 */

public class CoordinatesForm extends Form<Coordinates> {
    private final Printable console;
    private final UserInput scanner;



    public CoordinatesForm(Printable console){
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }
    /**
     * Сконструировать новый элемент Enum {@link CoordinatesForm}
     * @return объект Enum {@link CoordinatesForm}
     */
    public Coordinates build(){
        return new Coordinates(askX(), askY());

    }

    private Long askX(){
        while (true){
            console.printLn(ConsoleColors.toColor("Введите координату X", ConsoleColors.GREEN));
            String input = scanner.nextLine().trim();
            try{
                return Long.parseLong(input);
            } catch (NumberFormatException e){
                console.printError("X должен быть типа Long");
                if(Console.isFileMode()) throw new ExceptionInFileMode();
            }
        }
    }
    private double askY(){
        while (true){
            console.printLn(ConsoleColors.toColor("Введите координату Y", ConsoleColors.GREEN));
            String input = scanner.nextLine().trim();
            try{
                return Double.parseDouble(input);
            } catch (NumberFormatException e){
                console.printError("Y должен быть типа Long");
                if(Console.isFileMode()) throw new ExceptionInFileMode();
            }
        }
    }
}
