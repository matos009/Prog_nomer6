package commands;
import exceptions.*;
import managers.CollectionManager;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;
import utility.ConsoleColors;

import java.util.List;

public class Info extends Command{

    private CollectionManager collectionManager;

    public Info( CollectionManager collectionManager){
        super("info", ": вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");

        this.collectionManager = collectionManager;
    }
    /**
     * Исполнить команду
     * @param request аргументы команды
     * @throws IllegalArgumentsException неверные аргументы команды
     */


    @Override
    public Response execute(Request request) throws IllegalArgumentsException, ExitObliged, CommandRuntimeError {
       if(!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        String lastInitTime = (collectionManager.getLastInitTime() == null)
                ? "В сессии коллекция не инициализирована"
                : collectionManager.getLastInitTime().toString();
        String lastSaveTime = (collectionManager.getLastSaveTime() == null)
                ? "В сессии коллекция не инициализирована "
                : collectionManager.getLastSaveTime().toString();
        String stringBuilder = "Сведения о коллекции: \n" +
                ConsoleColors.toColor("Тип: ", ConsoleColors.GREEN) + collectionManager.getCollectionType() + "\n" +
                ConsoleColors.toColor("Количество элементов: ", ConsoleColors.GREEN) + collectionManager.getCollectionSize() + "\n" +
                ConsoleColors.toColor("Дата последней инициализации: ", ConsoleColors.GREEN) + lastInitTime + "\n" +
                ConsoleColors.toColor("Дата последнего изменения: ", ConsoleColors.GREEN) + lastSaveTime + "\n";
        return new Response(stringBuilder, ResponseStatus.OK);
}
}
