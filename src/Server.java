import java.io.*;
import java.net.*;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        try {
            // Create socket on agreed upon port
            ServerSocket serverSocket = new ServerSocket(4242);
            System.out.printf("Now serving on port %d at %s\n", 4242, serverSocket.getInetAddress().getHostAddress());

            // Wait for client to connect, get socket connection...
            Socket socket = serverSocket.accept();
            System.out.println("Connection Successful");

            // Open output stream to client, flush send header, then input stream
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // Receive username of user
            String username = ois.readUTF();
            // Notify client of successful connection and wait for them to continue
            String welcome = String.format("Welcome %s, you have successfully connected to ClassCreator server!\n", username);
            oos.writeUTF(welcome);
            oos.flush();

            // Receive file from client
            File file = (File) ois.readObject();

            // Receive language from client
            char language = ois.readChar();

            ProjectReader reader = null;
            if (language == 'J') {
                // Call Java Project Reader and Writer
                reader = java(file);
            } else if (language == 'C') {
                // Call C Project Reader and Writer
            } else if (language == 'E') {
                oos.writeUTF("See you next time!\n");
                oos.flush();
                socket.close();
            }

            String projectName = reader.getProjectName();
            oos.writeUTF(projectName);
            oos.flush();

            // Send list of classes read by ProjectReader

            List<Class> classes = reader.getClasses();
            int numClasses = classes.size();
            oos.writeInt(numClasses);
            oos.flush();

            for (int i = 0; i < numClasses; i++) {
                oos.writeObject(classes.get(i));
            }

            // Close streams and connection

            oos.flush();
            oos.close();
            ois.close();

            socket.close();
            System.out.printf("Successfully closed connection with %s\n", username);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private static ProjectReader java(File file) {
        ProjectReader reader = new ProjectReader(file);
        reader.readProject();

        return reader;
    }

    private static ProjectReader c(File file) {
        return null;
    }
}
