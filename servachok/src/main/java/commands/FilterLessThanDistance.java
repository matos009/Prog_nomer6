package commands;

import exceptions.*;
import managers.CollectionManager;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;


import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Команда 'filter_less_than_distance'
 * Выводит  элементы меньше заданной дистанции
 */

public class FilterLessThanDistance extends Command{
    private CollectionManager collectionManager;

    public FilterLessThanDistance( CollectionManager collectionManager){
        super("filter_less_than_distance", "distance  : вывести элементы, значение поля distance которых меньше заданного");
        this.collectionManager = collectionManager;
    }



    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (request.getArgs().isBlank()) throw new IllegalArgumentsException();
        try {
            long distance = Long.parseLong(request.getArgs().trim());
            return new Response("Количество элементов, с меньшим значением поля distance: " +
                    collectionManager.getCollection().stream()
                            .filter(Objects::nonNull)
                            .filter(s -> Long.compare(s.getDistance(), distance) <= -1)
                            .map(Object::toString) // преобразовать каждый элемент в строку
                            .collect(Collectors.joining(", ")), ResponseStatus.OK);


        } catch (NumberFormatException exception) {
            return new Response("distance должно быть числом типа long", ResponseStatus.ERROR);
        }
    }
}
