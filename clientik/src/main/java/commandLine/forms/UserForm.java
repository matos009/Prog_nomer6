package commandLine.forms;

import commandLine.*;
import exceptions.ExceptionInFileMode;
import exceptions.InvalidForm;
import rrr.User;
import utility.ExecuteFileManager;

import java.util.Objects;
import java.util.Scanner;

public class UserForm extends Form<User>{
    private Printable console;

    private UserInput scanner;


    public UserForm(Printable console){
        this.console = (Console.isFileMode()
                ? new BlankConsole()
                : console);
        this.scanner = (Console.isFileMode()
                ? new ExecuteFileManager()
                : new ConsoleInput());

    }


    @Override
    public User build() throws InvalidForm {
        return new User(askLogin(), askPassword());
    }

    public boolean checkExistAc(){
        while (true){
            console.printLn("У вас уже есть аккаунт ?");
            String answer = scanner.nextLine().trim();
            switch (answer){
                case "да", "yes" -> {
                    return true;
                }
                case "нет", "no" -> {
                    return false;
                }

                default -> {console.printError("Ответ не распознан");}
            }
        }
    }

    public String askLogin(){
        while (true){
            console.printLn("Введите логин: ");
            String login = scanner.nextLine().trim();
            if(login.isEmpty()){
                console.printError("Логин не может быть пустым");}
            if(Console.isFileMode()) throw new ExceptionInFileMode();
            else {
                return login;
            }
        }
    }


    public String askPassword(){
        String pass;
        while (true){
            console.printLn("Введите пароль: ");
            pass = (Objects.isNull(System.console())
            ? new String(System.console().readPassword())
                    : scanner.nextLine().trim());
            if(pass.isEmpty()){
                console.printError("Пароль не может быть пустым");
            } else {
                return pass;
            }
        }
    }



}