package models;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Validator, Serializable {
    private Long x;
    private double y;

    public Coordinates(Long x, double y){
        this.x = x;
        this.y = y;
    }
    public Long getX(){
        return x;
    }
    public void setX(Long x){
        this.x = x;
    }
    public double getY(){
        return y;
    }
    public void setY(double y){
        this.y = y;
    }

    public boolean validate(){
        if(this.x == null || this.x <= -383) return false;
        return !(this.y <= -768);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Float.compare(that.x, x) == 0 && y == that.y;
    }
    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }
    @Override
    public String toString(){
        return  "(" + this.x + "," + this.y + ")";
    }

}