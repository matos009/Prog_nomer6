package commands;

import exceptions.*;
import managers.CollectionManager;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;


public class Clear extends Command implements CollectionEditor{

    private final CollectionManager collectionManager;

    public Clear( CollectionManager collectionManager){
        super("clear", ": очистить коллекцию");
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        collectionManager.clear();
        return new Response("Элементы удалены",ResponseStatus.OK);
    }
}
