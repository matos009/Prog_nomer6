package commands;

import exceptions.*;
import managers.CommandManager;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;

/**
 * Команда 'help'
 * вывести справку по доступным командам
 */

public class Help extends Command{
    private CommandManager commandManager;
    /**
     * Команда 'help'
     * вывести справку по доступным командам
     */

    public Help(CommandManager commandManager){
        super("help", ": вывести информацию о командах");
        this.commandManager = commandManager;

    }

    /**
     * Исполнить команду
     * @param request аргументы команды
     * @throws IllegalArgumentsException неверные аргументы команды
     */
//    @Override
//    public void execute(String args) throws IllegalArgumentsException, ExitObliged, CommandRuntimeError {
//        if(!args.isBlank()) throw new IllegalArgumentsException();
//        commandManager.getCommands()
//                .forEach(command -> console.println(ConsoleColors.toColor
//                        (command.getName(), ConsoleColors.PURPLE) + command.getDescription()));
//    }

    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        return new Response(
                String.join("\n",
                        commandManager.getCommands()
                                .stream().map(Command::toString).toList()), ResponseStatus.OK);
    }
}
