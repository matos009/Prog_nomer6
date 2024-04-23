package commands;


import exceptions.*;
import managers.CollectionManager;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;

import java.util.Objects;

public class Insert extends Command implements CollectionEditor{
    private CollectionManager collectionManager;
    public Insert( CollectionManager collectionManager) {
        super("insert_at", ": добавить новый элемент в заданную позицию");
        this.collectionManager = collectionManager;
    }

    /**
     * Исполнить команду
     * @param args аргументы команды
     * @throws IllegalArgumentsException неверные аргументы команды
     */

//    @Override
//    public void execute(String args) throws IllegalArgumentsException, ExitObliged, CommandRuntimeError {
//        if(args.isBlank()) throw new IllegalArgumentsException();
//        try{
//            int index = Integer.parseInt(args.trim());
//            console.println("Создание нового элемента");
//            Route insertingElement = new RouteForm(console).build();
//            collectionManager.insertElement(index, insertingElement);
//            console.println("Элемент успешно вставлен!");
//        } catch (NumberFormatException err){
//            console.printError(ConsoleColors.toColor("index должен быть типа Integer", ConsoleColors.RED));
//        } catch (IndexOutOfBoundsException err){
//            console.printError(ConsoleColors.toColor("Индекс вне зоны доступа", ConsoleColors.RED));
//        }catch (InvalidForm err){
//            console.printError(ConsoleColors.toColor("Поля объекта не валидны!", ConsoleColors.RED));
//        } catch (ExceptionInFileMode e){
//            console.printError(ConsoleColors.toColor("Поля в файле не валидны!", ConsoleColors.RED));
//        }
//    }

    /**
     * Исполнить команду
     * @param request запрос клиента
     * @throws IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (request.getArgs().isBlank()) throw new IllegalArgumentsException();
        if (Objects.isNull(request.getObject())){
            return new Response("Для команды " + this.getName() + " требуется объект", ResponseStatus.ASK_OBJECT );
        } else{
            int index = Integer.parseInt(request.getArgs().trim());
            request.getObject().setId(CollectionManager.getNextId());
            collectionManager.insertElement(index, request.getObject());
            return new Response( "Объект успешно добавлен в текущую позицию", ResponseStatus.OK);
        }
    }
}
