package commands;


import exceptions.*;
import managers.CollectionManager;
import models.Route;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Команда 'filter_starts_with_name'
 * Выводит количество элементы начинающиеся с заданной подстроки
 */

public class FilterStartsWithName extends Command{
    private CollectionManager collectionManager;

    public FilterStartsWithName( CollectionManager collectionManager){
        super("filter_starts_with_name", "{name} : вывести элементы, значение поля пате которых начинается с заданной подстроки");
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
            String subString = request.getArgs();
            List<Route> filteredRoutes = collectionManager.getCollection().stream()
                    .filter(Objects::nonNull)
                    .filter(route -> route.getName().startsWith(subString))
                    .collect(Collectors.toList());
            return new Response("Элементы с заданной подстроки : " + filteredRoutes, ResponseStatus.OK);

        } catch (NumberFormatException exception) {
            return new Response("{name} должно быть числом типа String", ResponseStatus.ERROR);
        }
    }

    }

