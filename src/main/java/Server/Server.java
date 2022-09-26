package Server;


import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    /*Добавил коментарий*/


    /**
     * Переменная, хранящая список соединений с клиентами, которые находятся онлайн.
     */
    private List<ServerConnection> ServerConnectionList;

    private Server(){
        this.ServerConnectionList = new ArrayList<>();
    }
    private static Server server;

    public static Server getServer(){
        if (server==null){
            server = new Server();
        }
        return server;
    }

    /**
     * Метод start() запускает сервер.
     */
    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(16002,20)){
            while (true) {
                Socket connection = serverSocket.accept();
                ServerConnection serverConnection = new ServerConnection(connection);
                this.addConnection(serverConnection);
                serverConnection.start();
            }

        }catch (IOException e){
            System.err.println("Ошибка создания серверного сокета: "+e.getMessage());
        }
    }

    public void addConnection(ServerConnection connection) {
        ServerConnectionList.add(connection);
    }

    public void deleteConnection(ServerConnection connection){
        ServerConnectionList.remove(connection);
    }

    /**
     * Метод send() рассылает сообщение всем членам беседы.
     */
    public void send(String text) {
        for (ServerConnection connection:ServerConnectionList){
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.connection.getOutputStream()))) {
                JSONObject response = new JSONObject();
                response.put("text",text);
                writer.write(response.toJSONString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
