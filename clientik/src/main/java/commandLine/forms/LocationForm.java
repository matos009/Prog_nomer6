package commandLine.forms;

import commandLine.*;
import exceptions.ExceptionInFileMode;
import models.Location;
import utility.*;

import java.util.FormatFlagsConversionMismatchException;

public class LocationForm extends Form<Location>{
    private final Printable console;
    private final UserInput scanner;

    LocationForm(Printable console){
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }

    /**
     * Сконструировать новый элемент класса {@link Location}
     * @return объект класса {@link Location}
     */
    @Override
    public Location build(){
        return new Location(
                askX(),
                askY(),
                askZ(),
                askName());
    }


    public Long askX(){
        while (true){
            console.printLn(ConsoleColors.toColor("Введите координату X", ConsoleColors.GREEN));
            String input = scanner.nextLine().trim();
            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                console.printError("Координата должна быть типа Long");
                if(Console.isFileMode()) throw new ExceptionInFileMode();
            }

        }

    }
    public double askY(){
        while (true){
            console.printLn(ConsoleColors.toColor("Введите координату Y", ConsoleColors.GREEN));
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e){
                console.printError("Координата должна быть типа double");
                if(Console.isFileMode()) throw new ExceptionInFileMode();
            }
        }
    }
    public float askZ(){
        while (true){
            console.printLn(ConsoleColors.toColor("Введите координату Z", ConsoleColors.GREEN));
            String input = scanner.nextLine().trim();
            try {
                return Float.parseFloat(input);
            } catch (NumberFormatException e){
                console.printError("Координата должна быть типа float");
                if(Console.isFileMode()) throw new ExceptionInFileMode();
            }
        }
    }

    public String askName(){
        while (true){
            console.printLn(ConsoleColors.toColor("Напишите название локации", ConsoleColors.GREEN));
            String input = scanner.nextLine().trim();
            if(input.isEmpty()){
                console.printError("Имя не может быть пустым");
                if (Console.isFileMode()) throw new ExceptionInFileMode();
            }
            else {
                return input;
            }
        }
    }




}
