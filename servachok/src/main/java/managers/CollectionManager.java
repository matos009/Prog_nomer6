package managers;



import exceptions.InvalidForm;
import models.Route;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Класс организующий работу с коллекцией
 */

public class CollectionManager {
    private static long nextId = 0;
    /**
     * Дата создания коллекции
     */
    private final LinkedList<Route> collection = new LinkedList<>();
    /**
     * Дата последнего изменения коллекции
     */

    private LocalDateTime lastInitTime;

    private LocalDateTime lastSaveTime;
    /**
     * Класс организующий работу с коллекцией
     */

    private static final Logger collectionManagerLogger = LoggerFactory.getLogger(CollectionManager.class);



    public CollectionManager(){
        this.lastInitTime = LocalDateTime.now();
        this.lastSaveTime = null;
    }

    public static void updateId(LinkedList<Route> collection){
        nextId = (int) collection
                .stream()
                .filter(Objects::nonNull)
                .mapToDouble(Route::getId)  // mapToDouble для преобразования в примитивный double
                .max()
                .orElse(0);
        collectionManagerLogger.info("Обновлен айди на {}", nextId);
    }

    public LinkedList<Route> getCollection(){
        return collection;
    }
    /**
     * Метод для даты
     * @param localDateTime объект {@link LocalDateTime}
     * @return вывод даты
     */

    public static String timeFormatter(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        if (localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .equals(LocalDateTime.now().format(DateTimeFormatter.ofPattern(("yyyy-MM-dd"))))) {
            return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }



    public static   long getNextId(){
        nextId++;
        return nextId;
    }
    /**
     * Метод для даты
     * @param dateToConvert объект {@link Date}
     * @return вывод даты
     */
    public static String timeFormatter(Date dateToConvert){
        LocalDateTime localDateTime = dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        if(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .equals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    }


    public Route getById(long id){
        for(Route element : collection){
            if(element.getId() == id) return element;
        }
        return null;
    }

    public void editById(long id, Route newElement){
        Route pastElement = this.getById(id);
        this.removeElement(pastElement);
        newElement.setId(id);
        this.addElement(newElement);
        collectionManagerLogger.info("Объект с айди {} изменен", id);

    }

    public LocalDateTime getLastInitTime(){
        return lastInitTime;
    }
    public LocalDateTime getLastSaveTime(){
        return lastSaveTime;
    }

    public String getCollectionType(){
        return collection.getClass().getName();
    }

    public int getCollectionSize(){
        return collection.size();
    }
    public void clear(){
        this.collection.clear();
        lastInitTime = LocalDateTime.now();
        collectionManagerLogger.info("Коллекция очищена");
    }

    public Route getLast(){
        return collection.getLast();
    }

    public boolean checkExist(long id){
        return collection.stream()
                .anyMatch((x) -> x.getId() == id);
    }

    public void insertElement(int index, Route route){
        if(index < 0 || index > collection.size()) throw new IndexOutOfBoundsException("Индекс вне допустимого диапазона");
        collection.add(index, route);
    }
    public void removeAt(int index){
        if(index < 0 || index > collection.size()) throw new IndexOutOfBoundsException("Индекс вне допустимого диапазона");
        collection.remove(index);
    }

    public void addElement(Route element){
        this.lastSaveTime = LocalDateTime.now();
        collection.add(element);
        collectionManagerLogger.info("Добавлен объект в коллекцию {}", element);
    }

    public void getLastElement(){
        collection.getLast();
    }


    public void addElements(Collection<Route> collection) throws InvalidForm {
        if(collection ==null) return;
        for (Route route : collection){
            this.addElement(route);
        }
    }
    public void removeElement(Route element){
        collection.remove(element);
    }
    public void removeElements(Collection<Route> elements){
        collection.removeAll(elements);
    }

    public String toString(){
        if(collection.isEmpty()) return "Коллекция пустая!";
        var last = collection.getLast();

        StringBuilder info =new StringBuilder();
        for(Route route :collection){
            info.append(route);
            if(route!=last) info.append("\n\n");
        }
        return info.toString();
    }
}
