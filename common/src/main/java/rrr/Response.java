package rrr;

import models.Route;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;


public class Response implements Serializable {
   private final ResponseStatus responseStatus;
    private String response = "";

    private Collection<Route> collection;


    public Response(ResponseStatus responseStatus){
        this.responseStatus = responseStatus;
    }

    public Response(String response, ResponseStatus responseStatus){
        this.response = response;
        this.responseStatus = responseStatus;
    }

    public Response(String response,  Collection<Route> collection, ResponseStatus status){
        this.response = response;
        this.responseStatus = status;
        this.collection =collection.stream()
                .sorted(Comparator.comparing(Route::getId))
                .toList();
    }

    public String getResponse(){
        return response;
    }
    public ResponseStatus getResponseStatus(){
        return responseStatus;
    }
    public Collection<Route> getCollection(){
        return collection;
    }

    public int hashCode(){
        return Objects.hash(response, responseStatus, collection);}

    public boolean equals(Object o){
        if(this == o) return  true;
        if(!(o instanceof Response response)) return false;
        return this.responseStatus == response.responseStatus && Objects.equals(this.response, response.response)
                && Objects.equals(this.collection, response.collection);
    }

    public String toString(){
        return "Response[" +
                responseStatus +
                (response.isEmpty()
                ? ""
                        : "," + response) +
                (collection == null
                ? "]"
                        : "," + collection + "]");
    }
}
