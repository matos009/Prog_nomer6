package utility;

import commandLine.Console;
import commandLine.Printable;
import commandLine.forms.RouteForm;
import commandLine.forms.UserForm;
import exceptions.ExceptionInFileMode;
import exceptions.ExitObliged;
import exceptions.InvalidForm;
import models.Route;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;
import rrr.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;


import java.util.Scanner;

/**
 * Класс обработки пользовательского ввода
 *
 */
public class RunTimeManager {
    private final Printable console;
    private final Scanner userScanner;
    private final Client client;
    private User user = null;

    public RunTimeManager(Printable console, Scanner userScanner, Client client) {
        this.console = console;
        this.userScanner = userScanner;
        this.client = client;
    }

    /**
     * Перманентная работа с пользователем и выполнение команд
     */
    public void interactiveMode(){
        while (true) {
            try{
                if (!userScanner.hasNext()) throw new ExitObliged();
                String userCommand = userScanner.nextLine().trim() + " ";
                String[] commandParts = userCommand.split(" ", 2);
                if(commandParts.length > 1){
                    commandParts[0] = commandParts[0].trim();
                    commandParts[1] = commandParts[1].trim();
                }
                if(commandParts[0].equals("add")){
                    Route route = new RouteForm(console).build();
                    if(!route.validate()) throw new InvalidForm();
                    Response response = client.sendAndAskResponse(new Request(commandParts[0], commandParts[1], route));
                    if(response.getResponseStatus() != ResponseStatus.OK){
                        console.printError(response.getResponse());
                    } else {
                        this.printResponse(response);
                    }
                } else if (commandParts[0].equals("update") && !commandParts[1].isEmpty()) {
                    Route route = new RouteForm(console).build();
                    if(!route.validate()) throw new InvalidForm();
                    Response response = client.sendAndAskResponse(new Request(commandParts[0], commandParts[1], route));
                    if(response.getResponseStatus() != ResponseStatus.OK){
                        console.printError(response.getResponse());
                    } else {
                        this.printResponse(response);
                    }
                } else{
                    Response response = client.sendAndAskResponse(new Request(commandParts[0].trim(), commandParts[1].trim()));
                    this.printResponse(response);
                    switch (response.getResponseStatus()){
                        case EXIT -> throw new ExitObliged();
                        case EXECUTE_SCRIPT -> {
                            Console.setFileMode(true);
                            this.fileExecution(response.getResponse());
                            Console.setFileMode(false);
                        }
                        default -> {}
                    }

                }

            } catch (InvalidForm err){
                console.printError("Поля не валидны! Объект не создан");
            } catch (NoSuchElementException exception) {
                console.printError("Пользовательский ввод не обнаружен!");
            } catch (ExitObliged exitObliged){
                console.printLn(ConsoleColors.toColor("До свидания!", ConsoleColors.YELLOW));
                return;
            }
        }
    }

    private void printResponse(Response response){
        switch (response.getResponseStatus()){
            case OK -> {
                if ((Objects.isNull(response.getCollection()))) {
                    console.printLn(response.getResponse());
                } else {
                    console.printLn(response.getResponse() + "\n" + response.getCollection().toString());
                }
            }
            case ERROR -> console.printError(response.getResponse());
            case WRONG_ARGUMENTS -> console.printError(response + " Неверное использование команды!");
            default -> {}
        }
    }

    private void fileExecution(String args) throws ExitObliged{
        if (args == null || args.isEmpty()) {
            console.printError("Путь не распознан");
            return;
        }
        else console.printLn(ConsoleColors.toColor("Путь получен успешно", ConsoleColors.PURPLE));
        args = args.trim();
        try {
            ExecuteFileManager.pushFile(args);
            for (String line = ExecuteFileManager.readLine(); line != null; line = ExecuteFileManager.readLine()) {
                String[] userCommand = (line + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                if (userCommand[0].isBlank()) return;
                if (userCommand[0].equals("execute_script")){
                    if(ExecuteFileManager.repeatFile(userCommand[1])){
                        console.printError("Найдена рекурсия по пути " + new File(userCommand[1]).getAbsolutePath());
                        continue;
                    }
                } else if (userCommand[0].equals("add")){
                    Route route = new RouteForm(console).build();
                    if(!route.validate()) throw new InvalidForm();
                    Response response = client.sendAndAskResponse(new Request(userCommand[0], userCommand[1], route));
                    if(response.getResponseStatus() != ResponseStatus.OK){
                        console.printError(response.getResponse());
                    } else {
                        this.printResponse(response);
                    }

                } else if (userCommand[0].equals("update") && !userCommand[1].isEmpty()) {
                    Route route = new RouteForm(console).build();
                    if(!route.validate()) throw new InvalidForm();
                    Response response = client.sendAndAskResponse(new Request(userCommand[0], userCommand[1], route));
                    if(response.getResponseStatus() != ResponseStatus.OK){
                        console.printError(response.getResponse());
                    } else {
                        this.printResponse(response);
                    }
                }

                else {
                    console.printLn(ConsoleColors.toColor("Выполнение команды " + userCommand[0], ConsoleColors.YELLOW));
                    Response response = client.sendAndAskResponse(new Request(userCommand[0].trim(), userCommand[1].trim()));
                    this.printResponse(response);
                    switch (response.getResponseStatus()){

                        case EXIT -> throw new ExitObliged();
                        case EXECUTE_SCRIPT -> {
                            this.fileExecution(response.getResponse());
                            ExecuteFileManager.popRecursion();
                        }
                        default -> {}
                    }


                }
//                console.printLn(ConsoleColors.toColor("Выполнение команды " + userCommand[0], ConsoleColors.YELLOW));
//                Response response = client.sendAndAskResponse(new Request(userCommand[0].trim(), userCommand[1].trim()));
//                this.printResponse(response);
//                switch (response.getResponseStatus()){
//
//                    case EXIT -> throw new ExitObliged();
//                    case EXECUTE_SCRIPT -> {
//                        this.fileExecution(response.getResponse());
//                        ExecuteFileManager.popRecursion();
//                    }
//                    default -> {}
//                }
            }
            ExecuteFileManager.popFile();
        } catch (FileNotFoundException fileNotFoundException){
            console.printError("Такого файла не существует");
        } catch (IOException e) {
            console.printError("Ошибка ввода вывода");
        }
    }
}


//                    case ASK_OBJECT -> {
//                        Route route;
//                        try{
//                            route = new RouteForm(console).build();
//                            if (!route.validate()) throw new ExceptionInFileMode();
//                        } catch (ExceptionInFileMode err){
//                            console.printError("Поля в файле не валидны! Объект не создан");
//                            continue;
//                        }
//                        Response newResponse = client.sendAndAskResponse(
//                                new Request(
//                                        userCommand[0].trim(),
//                                        userCommand[1].trim(),
//                                        route));
//                        if (newResponse.getResponseStatus() != ResponseStatus.OK){
//                            console.printError(newResponse.getResponse());
//                        }
//                        else {
//                            this.printResponse(newResponse);
//                        }
//                    }