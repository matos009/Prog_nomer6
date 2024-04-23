package commands;


import exceptions.*;
import managers.CollectionManager;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;

/**
 * Команда 'remove_by_id'
 * Удаляет по index объекта колеекции
 */

public class RemoveByIndex extends Command implements CollectionEditor{

    private CollectionManager collectionManager;

    public  RemoveByIndex( CollectionManager collectionManager){
        super("remove_at", " {index} : удалить элемент, находящийся в заданной позиции коллекции (index)");

        this.collectionManager = collectionManager;
    }
    /**
     * Исполнить команду
     * @param args аргументы команды
     * @throws IllegalArgumentsException неправильные аргументы
     * @throws ExitObliged нужен выход из программы
     * @throws CommandRuntimeError ошибка во время выполнения
     */

    /**
     * Исполнить команду
     * @param request запрос клиента
     * @throws IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException{
        if (request.getArgs().isBlank()) throw new IllegalArgumentsException();

        try {
            int index = Integer.parseInt(request.getArgs().trim());
            collectionManager.removeAt(index);
            return new Response("Объект удален успешно", ResponseStatus.OK);
        } catch (NumberFormatException exception) {
            return new Response("index должно быть числом типа int", ResponseStatus.WRONG_ARGUMENTS);
        }
    }
}
