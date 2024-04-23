package models;

import java.io.Serializable;
import java.util.Objects;

public class Location implements Validator, Serializable {
    private Long x;
    private double y;
    private Float z;
    private String name;

    public Location(Long x, double y, Float z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Float getZ() {
        return z;
    }

    public void setZ(Float z) {
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public boolean validate(){
        if(this.x == null) return false;
        if(this.z == null) return false;
        return !(name.length() <= 705 && name == null);
    }
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass())return false;
        Location location = (Location) o;
        if(Double.compare(location.x, y) != 0) return false;
        if(location.x != x) return false;
        if(Double.compare(location.z, z) != 0) return false;
        return Objects.equals(name, location.name);
    }

    public int hashCode(){
        int result;
        long temp;
        long temp2;
        temp2 = Float.floatToIntBits(z);
        temp = Double.doubleToLongBits(y);
        result = (int) (temp^(temp >>> 32));
        result = 31 * result + (int) (temp2^(temp2>>>32));
        result = 31 * result + (int)(x^(x>>>32));
        result =  31 * result + (name != null ? name.hashCode():0);
        return result;
    }

    public String toString(){
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z + ", name=" + name + '\'' +
                '}';
    }
}

