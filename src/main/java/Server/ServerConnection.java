package Server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;

public class ServerConnection extends Thread{
    Socket connection;

    public ServerConnection(Socket connection){
        this.connection = connection;
        this.start();
    }

    @Override
    public void run() {
        try(ObjectInputStream reader = new ObjectInputStream(connection.getInputStream());
            ObjectOutputStream writer = new ObjectOutputStream(connection.getOutputStream())){

            while (true){
                String message = (String) reader.readObject();
                JSONParser parser = new JSONParser();
                JSONObject object = (JSONObject) parser.parse(message);
                //дописать обработку сообщения от клиента
            }
        }catch(IOException e){
            System.err.println("Ошибка получения InputStream из объекта connection: "+e.getMessage());
        }catch (ParseException e) {
            System.out.println("Ошибка парсинга сообщения на сервере");
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка поиска файла");
        }
    }
}
