package commandLine.forms;

import commandLine.*;
import exceptions.ExceptionInFileMode;
import models.Coordinates;
import models.Location;
import models.Route;
import java.util.Date;
import utility.*;

public class RouteForm extends Form<Route>{
    private final Printable console;
    private final UserInput scanner;

    public RouteForm(Printable console){
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }
    /**
     * Сконструировать новый элемент класса {@link Route}
     * @return объект класса {@link Route}
     */

    public Route build(){
        return new Route(
                askName(),
                askCoordinates(),
                new Date(),
                askLocationFrom(),
                askLocationTo(),
                askDistance()
        );
    }




    private String askName(){
        String name;
        while (true){
            console.printLn(ConsoleColors.toColor("Введите название маршрута:", ConsoleColors.GREEN));
            name = scanner.nextLine().trim();
            if(name.isEmpty()){
                console.printError("Название маршрута не может быть пустым");
                if(Console.isFileMode()) throw new ExceptionInFileMode();
            }
            else {
                return name;
            }
        }
    }
    private Coordinates askCoordinates(){
        return new CoordinatesForm(console).build();
    }
    private Location askLocationFrom() {
        console.printLn("Место отлета:");
        return new LocationForm(console).build();
    }
    private Location askLocationTo() {
        console.printLn("Место прилета:");
        return new LocationForm(console).build();
    }
    private Integer askDistance(){
        while (true) {
            console.printLn(ConsoleColors.toColor("Введите расстояние маршрута:", ConsoleColors.GREEN));
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                console.printError("Расстояние должно быть типа Integer");
                if (Console.isFileMode()) throw new ExceptionInFileMode();
            }
        }
    }



}
