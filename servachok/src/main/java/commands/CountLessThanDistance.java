package commands;



import exceptions.*;
import managers.CollectionManager;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;

import javax.swing.*;
import java.util.Objects;

/**
 * Команда 'count_less_than_distance'
 * Выводит количество элементов, значение поля distance которых меньше заданному
 */

public class CountLessThanDistance extends Command{

    private CollectionManager collectionManager;

    public CountLessThanDistance( CollectionManager collectionManager){
        super("count_less_than_distance",": distance - вывести количество элементов, значение поля distance которых меньше заданного");
        this.collectionManager = collectionManager;
    }
    /**
     * Исполнить команду
     * @param request аргументы команды
     * @throws IllegalArgumentsException неверные аргументы команды
     */


    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (request.getArgs().isBlank()) throw new IllegalArgumentsException();
        try {
            int distance = Integer.parseInt(request.getArgs().trim());
            return new Response("Количество элементов, с меньшим значением поля distance: " +
                  (collectionManager.getCollection().stream().
                            filter(Objects::nonNull)
                            .filter(r -> Integer.compare(r.getDistance(), distance) <= -1)
                            .map(Objects::toString)
                            .count()), ResponseStatus.OK);
        } catch (NumberFormatException exception) {
            return new Response("distance должно быть числом типа long", ResponseStatus.ERROR);
        }
    }
}
