package managers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.security.AnyTypePermission;
import exceptions.ExitObliged;
import exceptions.InvalidForm;
import models.Route;
import utility.Console;
import utility.ConsoleColors;
import utility.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collection;

public class FileManager {
    private String text;
    private final String pathToFile;
    private final Printable console;
    private final CollectionManager collectionManager;
    private final XStream xStream = new XStream();

    static final Logger fileManagerLogger = LoggerFactory.getLogger(FileManager.class);
    /**
     * В конструкторе задаются алиасы для библиотеки {@link XStream} которая используется для работы с xml
     * @param console Пользовательский ввод-вывод
     * @param collectionManager Работа с коллекцией
     * @param fileToPath путь до файла
     */
    public FileManager(Printable console, CollectionManager collectionManager, String fileToPath){
        this.console = console;
        this.collectionManager = collectionManager;
        this.pathToFile = fileToPath;

        xStream.alias("Route", Route.class);
        xStream.alias("Array",CollectionManager.class);
        xStream.addPermission(AnyTypePermission.ANY);
        xStream.addImplicitCollection(CollectionManager.class,"collection");
        fileManagerLogger.info("Созданы алиасы для xstream");
    }
    /**
     * Метод проверяющий валидность файла
     * @throws ExitObliged если путь - null или отсутствует программа заканчивает выполнение
     */
    public boolean findFile() throws ExitObliged {
        File file = new File(pathToFile);
        if ((file == null)) {
            console.printError("Путь должен быть в аргументах командной строки");
            fileManagerLogger.error("Нет пути в переменных окружения");
            throw new ExitObliged();
        }
        else console.printLn(ConsoleColors.toColor("Дорога получен, фсее нармально, начальника!", ConsoleColors.PURPLE));
        fileManagerLogger.info("Путь получен успешно");
        BufferedInputStream bufferedInputStream;
        FileInputStream fileInputStream;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fileInputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            fileManagerLogger.info("Отрыто соединение с файлом");
            while (bufferedInputStream.available() > 0){
                stringBuilder.append((char)bufferedInputStream.read());
            }
            fileInputStream.close();
            bufferedInputStream.close();
            fileManagerLogger.info("Файл прочитан");
            if (stringBuilder.isEmpty()){
                console.printError("Передан пустой файл");
                fileManagerLogger.info("Файл пустой");
                this.text = "</Array>";
                return false;
            }
            this.text = stringBuilder.toString();
        } catch (FileNotFoundException e) {
            console.printError("Файл не найден");
            fileManagerLogger.error("Такого файла не найдено");
            throw new ExitObliged();
        } catch (IOException e) {
            console.printError("Ошибка ввода/вывода");
            fileManagerLogger.error("Ошибка ввода/вывода" + e);
            throw new ExitObliged();
        }
        return true;
    }


    /**
     * Метод для считывания коллекции в формате XML(из файла) и перевода в LinkedList коллекцию
     * @throws ExitObliged Если объекты в файле невалидны выходим из программы
     */
    public void createObjects() throws ExitObliged{
        try {
            XStream xStreamCreator = new XStream();
            xStreamCreator.alias("Route",Route.class);
            xStreamCreator.alias("Array", CollectionManager.class);
            xStreamCreator.addPermission(AnyTypePermission.ANY);
            xStreamCreator.addImplicitCollection(CollectionManager.class,"collection");
            fileManagerLogger.info("Сконфигурирован xstream для чтения из файла");

            xStreamCreator.registerConverter(new DateConverter("yyyy-MM-dd HH:mm:ss.SSSSSSSSS", new String[]{"yyyy-MM-dd'T'HH:mm:ss.SX"}));


            CollectionManager collectionWithObjects = (CollectionManager) xStreamCreator.fromXML(text.trim());


            if (collectionWithObjects.getCollection() == null){
                console.printError("Файл содержет пустую коллекцию");
                fileManagerLogger.error("Пустая коллекция в файле!");

                return;
            }
            for (Route route: collectionWithObjects.getCollection()){
                if (collectionManager.checkExist(route.getId())){
                    console.printError("В файле имеются повторяющиеся id");
                    fileManagerLogger.error("В файле повторяются айди!");
                    throw new ExitObliged();
                }
                if(!route.validate()) throw new InvalidForm();
                this.collectionManager.addElement(route);
                fileManagerLogger.info("Добавлен объект", route);
            }
        } catch (InvalidForm | StreamException exception){
            console.printError("Объекты из файла не валидны");
            fileManagerLogger.error("Объекты в файле не валидны");
        } catch (ConversionException e){
            console.printError("Неверная структура XML файла. Переделайте его и попробуйте снова" + e.getMessage());
            fileManagerLogger.error("Неверная структура XML файла.");
        }
        CollectionManager.updateId(collectionManager.getCollection());
    }
    /**
     * Метод для сохранения коллекции в файл
     */
    public void saveObjects(){
        String file_path = pathToFile;
        if (file_path == null || file_path.isEmpty()){
            console.printError("Вы ничего не ввели");
            fileManagerLogger.error("Отсутствует путь в переменных окружения");
            return;
        } else console.printLn(ConsoleColors.toColor("Путь к файлу успешно получен",ConsoleColors.CYAN));
        fileManagerLogger.info(ConsoleColors.toColor("Путь получен успешно", ConsoleColors.PURPLE));

        try {
            BufferedOutputStream bis = new BufferedOutputStream(new FileOutputStream(file_path));
            bis.write(xStream.toXML(collectionManager).getBytes(StandardCharsets.UTF_8));
            bis.close();
            fileManagerLogger.info("Файл записан");
        } catch (FileNotFoundException e){
            console.printError("Файл не найден");
            fileManagerLogger.error("Файл не существует");
        } catch (IOException ioException){
            console.printError("Ошибка ввода/вывода");
            fileManagerLogger.error("Ошибка ввода вывода");
        }
    }

}







