package rrr;

public record User(String login, String password){

    public String toString(){
        return login + "-" + password;
    }
}
