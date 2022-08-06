package Server;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

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
}
