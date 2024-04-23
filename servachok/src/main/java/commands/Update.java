package commands;

import exceptions.IllegalArgumentsException;
import managers.CollectionManager;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;

import java.util.Objects;


public class Update extends Command{
    private CollectionManager collectionManager;

    public Update( CollectionManager collectionManager){
        super("update", " id : обновить значение элемента коллекции, id которого равен заданному");

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
        class NoSuchId extends RuntimeException{

        }
        try {
            long id = Long.parseLong(request.getArgs().trim());
            if (!collectionManager.checkExist(id)) throw new NoSuchId();
           // if (Objects.isNull(request.getObject())){
            // return new Response("Для команды " + this.getName() + " требуется объект", ResponseStatus.ASK_OBJECT);
           // }
            collectionManager.editById(id, request.getObject());
            return new Response("Объект успешно обновлен", ResponseStatus.OK);
        } catch (NoSuchId err) {
            return new Response("В коллекции нет элемента с таким id", ResponseStatus.ERROR);
        } catch (NumberFormatException exception) {
            return new Response("id должно быть числом типа int", ResponseStatus.ERROR);
        }
    }
}
