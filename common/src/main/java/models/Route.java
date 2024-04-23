package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;


import utility.ConsoleColors;

interface CoordinateRange<T, S>{
    float getDistanceFromCentre(T x, S y);
}

public class Route implements Comparable<Route>, Validator, Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле может быть null
    private Location to; //Поле может быть null
    private Integer distance; //Поле не может быть null, Значение поля должно быть больше 1


    public Route(String name, Coordinates coordinates, Date creationDate, Location from, Location to, Integer distance){
        this.id = 0L;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate =creationDate;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }


    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Coordinates getCoordinates(){
        return coordinates;
    }
    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }
    public Date getCreationDate(){
        return creationDate;
    }
    public void setCreationDate(Date date){
        this.creationDate = date;
    }
    public Location getFrom(){
        return from;
    }
    public void setFrom(Location from){
        this.from =from;
    }
    public Location getTo(){
        return to;
    }
    public void setTo(Location to){
        this.to = to;
    }

    public Integer getDistance(){
        return distance;
    }

    public void setDistance(Integer distance){
        this.distance = distance;
    }


    @Override
    public int compareTo(Route route){
        if(Objects.isNull(route)) return 1;
        CoordinateRange<Long, Double> calculator = (x, y) -> (float) Math.sqrt(x*x + y*y);
        return Float.compare(calculator.getDistanceFromCentre(this.getCoordinates().getX(), this.getCoordinates().getY()),
                calculator.getDistanceFromCentre(route.getCoordinates().getX(), route.getCoordinates().getY()));
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Route inMethod = (Route) o;

        if (!Objects.equals(this.from, inMethod.from)) return false;
        if (!Objects.equals(this.to, inMethod.to)) return false;
        if (!this.name.equals(inMethod.name)) return false;
        if(this.id != inMethod.id) return false;
        if(this.to != inMethod.to) return false;
        if(this.distance != inMethod.distance) return false;
        if(this.creationDate.equals(inMethod.creationDate)) return false;
        return (this.coordinates.equals(inMethod.coordinates));
    }
    @Override
    public int hashCode(){
        int result = id.hashCode();
        result = 31 * result + from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + distance.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + coordinates.hashCode();
        return result;
    }
    public boolean validate(){
        if(this.id == null || this.id < 0) return false;
        if(this.name == null || this.name.isEmpty()) return false;
        if(coordinates == null) return false;
        if(creationDate == null) return false;
        if(from == null) return false;
        if(to == null) return false;
        return !(distance == null || distance < 1);
    }

    public static String timeFormatter(Date dateToConvert){
        if (Objects.isNull(dateToConvert)) return "";
        LocalDateTime localDateTime = dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        if (localDateTime == null) return null;
        if (localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .equals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))){
            return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


    public String toString(){
        return "Route{" + '\n' +
                ConsoleColors.toColor("id =", ConsoleColors.CYAN) + id + '\n' +
                ConsoleColors.toColor("name = ", ConsoleColors.CYAN) + name + '\n' +
                ConsoleColors.toColor("coordinates =", ConsoleColors.CYAN) + coordinates + '\n' +
                ConsoleColors.toColor("creationDate = ", ConsoleColors.CYAN) + timeFormatter(creationDate) + '\n' +
                ConsoleColors.toColor("location from = ", ConsoleColors.CYAN) + from + '\n' +
                ConsoleColors.toColor("location to = ", ConsoleColors.CYAN) + to + '\n' +
                ConsoleColors.toColor("distance=", ConsoleColors.CYAN) + distance + '\n';

    }




}


