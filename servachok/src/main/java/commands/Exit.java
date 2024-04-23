package commands;


import exceptions.ExitObliged;
import exceptions.IllegalArgumentsException;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;

public class Exit extends Command{
    /**
     * Команда 'exit'
     * завершить программу (без сохранения)
     */
    public Exit(){
        super("exit", ":заверщение программы без сохранения");
    }


    /**
     * Исполнить команду
     * @param request аргументы команды
     * @throws ExitObliged нужен выход из программы
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        return new Response(ResponseStatus.EXIT);
    }
}
