package utility;

import commandLine.Console;
import commandLine.Printable;
import exceptions.IllegalArgumentsException;
import rrr.Request;
import rrr.Response;
import rrr.ResponseStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;

public class Client {
    private String host;
    private int port;

    private int reconnectionTimeOut;

    private int reconnectionAttempts;

    private int maxReconnectionAttempts;
    private Printable console;

    private  Socket socket;

    private ObjectInputStream serverReader;

    private ObjectOutputStream serverWriter;

    public Client(String host, int port, int reconnectionTimeOut, int maxReconectionAttempts, Printable console){
        this.host = host;
        this.port = port;
        this.reconnectionTimeOut = reconnectionTimeOut;
        this.maxReconnectionAttempts = maxReconectionAttempts;
        this.console = console;
    }

    public Response sendAndAskResponse(Request request){
        while (true) {
            try {
                if(Objects.isNull(serverWriter) || Objects.isNull(serverReader)) throw new IOException();
                if (request.isEmpty()) return new Response( "Запрос пустой!", ResponseStatus.WRONG_ARGUMENTS);
                serverWriter.writeObject(request);
                serverWriter.flush();
                Response response = (Response) serverReader.readObject();
                this.disconnectFromServer();
                reconnectionAttempts = 0;
                return response;
            } catch (IOException e) {
                if (reconnectionAttempts == 0){
                    connectToServer();
                    reconnectionAttempts++;
                    continue;
                } else {
                    console.printError("Соединение с сервером разорвано " + e.getMessage() +  " " + e.getLocalizedMessage());
                }
                try {
                    reconnectionAttempts++;
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                        console.printError("Превышено максимальное количество попыток соединения с сервером");
                        return new Response(ResponseStatus.EXIT);
                    }
                    console.printLn("Повторная попытка через " + reconnectionTimeOut / 1000 + " секунд");
                    Thread.sleep(reconnectionTimeOut);
                    connectToServer();
                } catch (Exception exception) {
                    console.printError("Попытка соединения с сервером неуспешна");
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void connectToServer(){
        if(reconnectionAttempts > 1) console.printLn(ConsoleColors.toColor("Повторное подключение к серверу", ConsoleColors.BLUE));
        try {
            this.socket = new Socket(host, port);

            this.serverWriter = new ObjectOutputStream(socket.getOutputStream());

            this.serverReader = new ObjectInputStream(socket.getInputStream());

        } catch (IllegalArgumentsException err){
            console.printError("Адрес подключения введен неверно");
        } catch (IOException err){
            console.printError("Ошибка подключения к серверу");
        }
    }


    public void disconnectFromServer(){
        try {
            this.socket.close();
            this.serverWriter.close();
            this.serverReader.close();

        } catch (IOException err){
            console.printError("Ошибка при отключении от сервера");
        }
    }



}
