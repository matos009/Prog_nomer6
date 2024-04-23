package rrr;

import models.Route;

import java.io.Serializable;
import java.util.Objects;

public class Request implements Serializable {
    private String commandName;
    private String args = "";
    private Route object;

    private User user;


    public Request(String commandName, String args){
        this.commandName = commandName.trim();
        this.args = args;
    }


    public Request(String commandName, Route route){
        this.commandName = commandName.trim();
        this.object = route;
    }

    public Request(String commandName, String args, Route route){
        this.commandName = commandName.trim();
        this.args =args.trim();
        this.object = route;
    }




    public boolean isEmpty(){
       return commandName.isEmpty() && args.isEmpty() && object == null;
    }
    public String getCommandName(){
       return commandName;
    }
    public String getArgs()
    {return args;}

    public Route getObject(){
       return object;
    }

    public boolean equals(Object o){
       if(this == o) return true;
       if(!(o instanceof Request request)) return false;
       return Objects.equals(this.commandName, request.commandName) && Objects.equals(this.args, request.args)
               & Objects.equals(this.object, request.object);
    }

    public int hashCode(){
       return Objects.hash(args, commandName, object);

    }


    public String toString(){
       return "Request[" + commandName +
               (args.isEmpty()
               ? ""
               : "," + args) +
               (object == null
                       ?  "]"
                       : ", " + object + "]");
    }
}
