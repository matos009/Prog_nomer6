package utility;

import commandLine.UserInput;
import models.Route;

import java.io.*;
import java.util.LinkedList;

public class ExecuteFileManager implements UserInput {

    private static final LinkedList<String> pathList = new LinkedList<>();
    private static final LinkedList<BufferedReader> readers = new LinkedList<>();

    public static void pushFile(String path) throws FileNotFoundException {
        pathList.addFirst(new File(path).getAbsolutePath());
        readers.addFirst(new BufferedReader(new InputStreamReader(new FileInputStream(path))));
    }

    public static File getFile(){
        return new File(pathList.getFirst());
    }
    public static String readLine() throws IOException{
        return readers.getFirst().readLine();
    }

    public static void popFile() throws IOException{
        readers.getFirst().close();
        readers.pop();
        pathList.pop();
        if(pathList.size() > 1){
            pathList.pop();
        }
    }
    public static boolean repeatFile(String path){
        return pathList.contains(new File(path).getAbsolutePath());
    }
    public static void popRecursion(){
        if(pathList.size() >= 1) {
            pathList.pop();
        }
    }

    public String nextLine(){
        try {
            return readLine();
        } catch (IOException e){
            return "";
        }

    }
}
