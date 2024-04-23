package commands;


import exceptions.*;
import managers.CollectionManager;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;
import utility.ConsoleColors;

import java.util.Objects;

public class Add extends Command implements CollectionEditor {
    private CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        super("add", "{element}: добавляет элемент в коллекцию");
        this.collectionManager = collectionManager;

    }

    /**
     * Исполнить команду
     *
     * @param request аргументы команды
     * @throws IllegalArgumentsException неверные аргументы команды
     */

    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
//        if(Objects.isNull(request.getObject())){
//            return new Response(ResponseStatus.ERROR);
//        }
            long nextId = CollectionManager.getNextId();
            request.getObject().setId(nextId);
            collectionManager.addElement(request.getObject());
            return new Response("Объект успешно добавлен", ResponseStatus.OK);
        }
    }



