package commands;



import exceptions.IllegalArgumentsException;
import managers.CommandManager;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
/**
 * Команда 'history'
 * Выводит последние 5 команд (без их аргументов)
 */
public class History extends Command{

    private CommandManager commandManager;

    public History( CommandManager commandManager){
        super("history", ": вывести последние 14 команд(без аргументов)");
        this.commandManager = commandManager;

    }

//    @Override
//    public void execute(String args) throws IllegalArgumentsException {
//        if (!args.isBlank()) throw new IllegalArgumentsException();
//        List<String> history = commandManager.getCommandHistory();
//        for(String command : history.subList(Math.max(history.size() - 14, 0), history.size())){
//            console.println(ConsoleColors.toColor(command, ConsoleColors.RED));
//        }
//    }

    /**
     * Исполнить команду
     * @param request аргументы команды
     * @throws IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();

        List<String> history= commandManager.getCommandHistory();
        return new Response(
                String.join("\n",
                        history.subList(Math.max(history.size() - 5, 0), history.size())), ResponseStatus.OK);
    }

}
