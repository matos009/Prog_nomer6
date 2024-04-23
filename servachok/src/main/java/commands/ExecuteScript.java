package commands;

import exceptions.IllegalArgumentsException;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;


/**
 * Команда 'execute_script'
 * Считатывает и исполняет скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
 */

public class ExecuteScript extends Command {


    public ExecuteScript() {
        super("execute_script", " {file name}: считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");



        }
    /**
     * Исполнить команду
     * @param request запрос клиента
     * @throws IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (request.getArgs().isBlank()) throw new IllegalArgumentsException();
        return new Response(request.getArgs(), ResponseStatus.EXECUTE_SCRIPT);
    }
}

