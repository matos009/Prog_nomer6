package utility;

import rrr.Request;
import rrr.Response;
import exceptions.*;
import exceptions.OpeningServerException;
import managers.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;


public class Server {
    private int port;
    private int soTimeout;
    private Printable console;
    private ServerSocketChannel ss;
    private SocketChannel socketChannel;
    private RequestHandler requestHandler;

    static final Logger serverLogger = LoggerFactory.getLogger(Server.class);

    BufferedInputStream bf = new BufferedInputStream(System.in);

    BufferedReader scanner = new BufferedReader(new InputStreamReader(bf));
    private FileManager fileManager;

    public Server(int port, int soTimeout, RequestHandler handler, FileManager fileManager) {
        this.port = port;
        this.soTimeout = soTimeout;
        this.console = new BlankConsole();
        this.requestHandler = handler;
        this.fileManager = fileManager;
    }

    public void run() {
        try {
            openServerSocket();
            serverLogger.info("Создано соединение");
            while (true) {
                try {
                    if (scanner.ready()) {
                        String line = scanner.readLine();
                        if (line.equals("save") || line.equals("s")) {
                            fileManager.saveObjects();
                            serverLogger.info("Коллекция сохранена");
                        }
                    }
                } catch (IOException ignored) {
                }
                try (SocketChannel clientSocket = connectToClient()) {
                    if (!processClientRequest(clientSocket)) break;
                } catch (ConnectionException | SocketTimeoutException ignored) {
                } catch (IOException exception) {
                    console.printError("Произошла ошибка при попытке завершить соединение с клиентом!");
                    serverLogger.error("Произошла ошибка при попытке завершить соединение с клиентом!");
                }
            }
            stop();
            serverLogger.info("Соединение закрыто");
        } catch (OpeningServerException e) {
            console.printError("Сервер не может быть запущен");
            serverLogger.error("Сервер не может быть запущен");
        }
    }

    private void openServerSocket() throws OpeningServerException {
        try {
            SocketAddress socketAddress = new InetSocketAddress(port);
            serverLogger.debug("Создан сокет");
            ss = ServerSocketChannel.open();
            serverLogger.debug("Создан канал");
            ss.bind(socketAddress);
            serverLogger.debug("Открыт канал сокет");
        } catch (IllegalArgumentException exception) {
            console.printError("Порт '" + port + "' находится за пределами возможных значений!");
            serverLogger.error("Порт находится за пределами возможных значений");
            throw new OpeningServerException();
        } catch (IOException exception) {
            serverLogger.error("Произошла ошибка при попытке использовать порт");
            console.printError("Произошла ошибка при попытке использовать порт '" + port + "'!");
            throw new OpeningServerException();
        }
    }

     private boolean processClientRequest(SocketChannel clientSocket) {

        try {
            ObjectInputStream clientReader = new ObjectInputStream(clientSocket.socket().getInputStream());
            ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.socket().getOutputStream());
            serverLogger.info("Открыты потоки ввода вывода");
            do {
                Request userRequest = (Request) clientReader.readObject();
                serverLogger.info("Получен реквест с командой " + userRequest.getCommandName(), userRequest);
                console.printLn(userRequest.toString());
                Response responseToUser = requestHandler.handle(userRequest);
                clientWriter.writeObject(responseToUser);
                serverLogger.info("Отправлен ответ", responseToUser);
                clientWriter.flush();
                if (userRequest == null) {
                    console.printError("Непредвиденный разрыв соединения с клиентом!");
                    serverLogger.error("Непредвиденный разрыв соединения с клиентом!");
                } else {
                    console.printLn("Клиент успешно отключен от сервера!");
                    serverLogger.info("Клиент успешно отключен от сервера!");
                }
            } while (true);
        } catch (ClassNotFoundException exception) {
            console.printError("Произошла ошибка при чтении полученных данных!");
            serverLogger.error("Произошла ошибка при чтении полученных данных!");
        } catch (InvalidClassException | NotSerializableException exception) {
            console.printError("Произошла ошибка при отправке данных на клиент!");
            serverLogger.error("Произошла ошибка при отправке данных на клиент!");
        } catch (IOException exception) {
            console.printError("IOException: " + exception.getMessage());


        }
        return true;
    }

    private SocketChannel connectToClient() throws ConnectionException, SocketTimeoutException {
        try {
            ss.socket().setSoTimeout(100);
            socketChannel = ss.socket().accept().getChannel();
            console.printLn("Соединение с клиентом успешно установлено.");
            serverLogger.info("Соединение с клиентом успешно установлено.");
            return socketChannel;
        } catch (SocketTimeoutException exception) {
            throw new SocketTimeoutException();
        } catch (IOException exception) {
            serverLogger.error("Произошла ошибка при соединении с клиентом!");
            throw new ConnectionException();
        }
    }


    private void stop() {
        class ClosingSocketException extends Exception{}
        try{
            if (socketChannel == null) throw new ClosingSocketException();
            socketChannel.close();
            ss.close();
            serverLogger.info("Все соединения закрыты");
        } catch (ClosingSocketException exception) {
            console.printError("Невозможно завершить работу еще не запущенного сервера!");
            serverLogger.error("Невозможно завершить работу еще не запущенного сервера!");
        } catch (IOException exception) {
            console.printError("Произошла ошибка при завершении работы сервера!");
            serverLogger.error("Произошла ошибка при завершении работы сервера!");
        }
    }

}











