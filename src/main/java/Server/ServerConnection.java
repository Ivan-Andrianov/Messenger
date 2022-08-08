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
                parse(message);
            }
        }catch(IOException e){
            System.err.println("Ошибка получения InputStream из объекта connection: "+e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка поиска файла");
        } catch (ParseException e) {
            System.out.println("Ошибка парсинга сообщения");
        }
    }

    public static void parse(String jsonTest) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject message = (JSONObject) parser.parse(jsonTest);

        switch ((String) message.get("action")){
            case ("send"):
                String text = (String) message.get("text");
                Integer id = Integer.parseInt((String) message.get("id"));
                Server.getServer().send(text);
                break;
        }


    }
}
