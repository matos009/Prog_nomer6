package commands;



import exceptions.IllegalArgumentsException;
import managers.CollectionManager;
import models.Route;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;
import utility.ConsoleColors;

import java.util.Collection;

/**
 * Команда 'show'
 *  Выводит в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class Show extends Command{

    private CollectionManager manager;

    public Show(CollectionManager manager){
        super("show", ": вывести в стандартный поток вывода все элементы коллекции в строковом представлении");

        this.manager = manager;
    }



    /**
     * Исполнить команду
     * @param request аргументы команды
     * @throws IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException{
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        Collection<Route> collection = manager.getCollection();
        if (collection == null || collection.isEmpty()) {
            return new Response("Коллекция еще не инициализирована", ResponseStatus.ERROR);
        }
        return new Response( "Коллекция: ", collection, ResponseStatus.OK);
    }
}
