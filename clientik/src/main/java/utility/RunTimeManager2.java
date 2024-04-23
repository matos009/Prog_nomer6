package utility;

import commandLine.Printable;
import exceptions.ExceptionInFileMode;
import exceptions.ExitObliged;
import exceptions.InvalidForm;
import rrr.Request;
import rrr.Response;
import models.Route;
import commandLine.forms.RouteForm;
import rrr.ResponseStatus;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class RunTimeManager2 {
    private Printable console;

    private Client2 client2;

    private Scanner userScanner;

    public RunTimeManager2(Printable console, Client2 client2, Scanner userScanner){
        this.console = console;
        this.client2 = client2;
        this.userScanner = userScanner;
    }


    public void interctiveMode() {
        while (true) {
            try {
                if (!userScanner.hasNext()) throw new ExitObliged();
                String command = userScanner.nextLine().trim() + " ";
                String[] commands = command.split(" ", 2);
                if(commands.length > 1){
                    commands[0] = commands[0].trim();
                    commands[1] = commands[1].trim();
                }



            } catch (ExitObliged exitObliged){
                console.printLn(ConsoleColors.toColor("До свидания!", ConsoleColors.YELLOW));
                return;
            }
        }
    }



    private void printResponse(Response response) throws ExitObliged {
        switch (response.getResponseStatus()){
            case OK -> {if(Objects.isNull(response.getCollection())){
                    console.printLn(response.getResponse());
                } else {
                console.printLn(response.getResponse() + "\n" + response.getCollection());
            }
        }
            case ERROR -> {console.printError(response.getResponse());}
            case WRONG_ARGUMENTS -> {console.printLn(response.getResponse());}}
    }




    private void fileExecution(String args) throws ExitObliged {
        if(args == null || args.isEmpty()){
            console.printError("Путь не распознан");
            return;
        } else {
            console.printLn("Путь получен успешно !");
            args.trim();
        }
        try {
            ExecuteFileManager.pushFile(args);
            for(String line = ExecuteFileManager.readLine(); line != null; ExecuteFileManager.readLine()){
                String[] command = (line + " ").split(" ", 2);
                if (command[0].isBlank()) return;
                if(command[0].equals("execute_script")){
                    if(ExecuteFileManager.repeatFile(command[1])){
                        console.printError("Найдена рекурсия по пути" + args);
                        continue;
                    }
                }
                if (command[0].equals("add")){
                    Route route;
                    try {
                         route = new RouteForm(console).build();
                        if (!route.validate()) throw new ExceptionInFileMode();
                    } catch (ExceptionInFileMode err){
                        console.printError("Поля в объекте не валидны");
                        continue;}
                    Response response = client2.sendGetR(new Request(command[0], command[1], route));
                    if(response.getResponseStatus() != ResponseStatus.OK){
                       console.printError(response.getResponse());
                    } else {
                        printResponse(response);
                    }
                } else {
                    Response response = client2.sendGetR(new Request(command[0], command[1]));
                    switch (response.getResponseStatus()){
                        case EXIT -> throw new ExitObliged();
                        case EXECUTE_SCRIPT -> {fileExecution(response.getResponse());
                        ExecuteFileManager.popRecursion();}
                    }
                }
                }
        } catch (FileNotFoundException err){
            console.printError("Файл не найден");
        } catch (IOException err){
            console.printError("Ошибка при чтении с файла");
        }


    }


}
