package commands;


import exceptions.*;
import managers.CollectionManager;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;

import java.util.Objects;

/**
 * Команда 'remove_by_id'
 * Удаляет по id объекта колеекции
 */
public class RemoveById extends Command implements CollectionEditor {

    private CollectionManager collectionManager;

    public RemoveById( CollectionManager collectionManager){
        super("remove_by_id", " {id} : удалить элемент из коллекции по его id");

        this.collectionManager = collectionManager;
    }
    /**
     * Исполнить команду
     * @param args аргументы команды
     * @throws IllegalArgumentsException неверные аргументы команды
     */




    /**
     * Исполнить команду
     * @param request запрос клиента
     * @throws IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException{
        if (request.getArgs().isBlank()) throw new IllegalArgumentsException();
        class NoSuchId extends RuntimeException {
        }
        try {
            int id = Integer.parseInt(request.getArgs().trim());
            if (!collectionManager.checkExist(id)) throw new NoSuchId();
            collectionManager.removeElement(collectionManager.getById(id));
            return new Response("Объект удален успешно", ResponseStatus.OK);
        } catch (NoSuchId err) {
            return new Response("В коллекции нет элемента с таким id", ResponseStatus.ERROR);
        } catch (NumberFormatException exception) {
            return new Response("id должно быть числом типа int", ResponseStatus.WRONG_ARGUMENTS);
        }
    }
}
