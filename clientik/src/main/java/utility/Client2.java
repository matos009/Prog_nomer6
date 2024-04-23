package utility;

import commandLine.Console;
import exceptions.IllegalArgumentsException;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class Client2 {
    String host;

    Console console;

    Socket socket;

    int port;

    int reconectionAttempts;

    int maxReconectionAttempts;

    int timeOut;

    ObjectInputStream reader;

    ObjectOutputStream writer;

    public Client2(String host, int port, int maxReconectionAttempts, int timeOut, Console console) {
        this.host = host;
        this.port = port;
        this.timeOut = timeOut;
        this.maxReconectionAttempts = maxReconectionAttempts;
        this.console = console;
    }


    public Response sendGetR(Request request) {
        while (true) {
            try {
                if (Objects.isNull(reader) || Objects.isNull(writer)) throw new IOException();
                if (request.isEmpty()) throw new IllegalArgumentsException();
                writer.writeObject(request);
                writer.flush();
                Response response = (Response) reader.readObject();
                disconectfromServak();
                reconectionAttempts = 0;
                return response;
            } catch (IOException err) {
                if (reconectionAttempts == 0) {
                    connectToServer();
                    reconectionAttempts++;
                    continue;
                } else {
                    console.printError("Соединение разорвано");}

                try {
                    if (reconectionAttempts >= maxReconectionAttempts){
                        console.printError("Превышено максимальное количсетво попыток");
                        return new Response(ResponseStatus.EXIT);
                    } else {
                        console.printLn("Повторная попытка через" + timeOut / 1000);
                        reconectionAttempts++;
                        Thread.sleep(timeOut);
                        connectToServer();
                    }
                } catch (Exception exception){
                    console.printError("Unsuccseful recontecion attempt");
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void connectToServer() {
        if (reconectionAttempts > 1) console.printLn("Повторное подключение к серверу");
        try {
            this.socket = new Socket(host, port);

            this.writer = new ObjectOutputStream(socket.getOutputStream());

            this.reader = new ObjectInputStream(socket.getInputStream());
        } catch (IllegalArgumentException err){
            console.printError("Адрес введен неверно");
        } catch (IOException err){
            console.printError("Ошибка подключения");
        }
    }

    private void disconectfromServak() {
        try {
            this.socket.close();

            this.reader.close();
            this.writer.close();
        } catch (IOException err) {
            console.printError("Ошибка отключения от сервера");
        }


    }







}