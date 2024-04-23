package utility;


import exceptions.*;
import managers.CommandManager;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;

public class RequestHandler {
    private CommandManager commandManager;


    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public Response handle(Request request) {
        try {
            commandManager.addToHistory(request.getCommandName());
            return commandManager.execute(request);
        } catch (IllegalArgumentsException e) {
            return new Response(
                    "Неверное использование аргументов команды", ResponseStatus.WRONG_ARGUMENTS);
        } catch (CommandRuntimeError e) {
            return new Response(
                    "Ошибка при исполнении программы", ResponseStatus.ERROR);
        } catch (NoSuchCommand e) {
            return new Response( "Такой команды нет в списке", ResponseStatus.ERROR);
        } catch (ExitObliged e) {
            return new Response(ResponseStatus.EXIT);
        }
    }




    }



































