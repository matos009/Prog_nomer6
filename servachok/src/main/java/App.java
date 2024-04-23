import exceptions.ExitObliged;
import managers.*;


import commands.*;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.*;

public class App extends Thread {

    public static int PORT = 7774;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    private static final Printable console = new BlankConsole();

    static final Logger rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Не указан путь к файлу. Пример использования: java Main /путь/к/файлу.xml");
            return;
        }
        String filePath = args[0];
        if(args.length > 1){
            try{
                PORT = Integer.parseInt(args[1]);
            } catch (NumberFormatException ignored) {}
        }
        CollectionManager collectionManager = new CollectionManager();
        FileManager fileManager = new FileManager(console, collectionManager, filePath);
        try{
            App.rootLogger.info("Создание объектов");
            fileManager.findFile();
            fileManager.createObjects();
            App.rootLogger.info("Создание объектов успешно завершено");
        } catch (ExitObliged e){
            console.printLn(ConsoleColors.toColor("До свидания!", ConsoleColors.YELLOW));
            App.rootLogger.error("Ошибка во времени создания объектов");
            return;
        }

        CommandManager commandManager = new CommandManager(fileManager);
        commandManager.addCommand(List.of(
                new Help(commandManager),
                new Info(collectionManager),
                new Show(collectionManager),
                new Add(collectionManager),
                new Update(collectionManager),
                new RemoveById(collectionManager),
                new Clear(collectionManager),
                new ExecuteScript(),
                new Exit(),

                new History(commandManager),
                new CountLessThanDistance(collectionManager),
                new FilterLessThanDistance(collectionManager),
                new FilterStartsWithName(collectionManager),
                new Insert(collectionManager)

        ));
        App.rootLogger.debug("Создан объект менеджера команд");
        RequestHandler requestHandler = new RequestHandler(commandManager);
        App.rootLogger.debug("Создан объект обработчика запросов");
        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler, fileManager);
        App.rootLogger.debug("Создан объект сервера");
        server.run();
    }
}