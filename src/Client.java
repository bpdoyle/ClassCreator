import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        // Prompt user for ip address and port
        Scanner scanner = new Scanner(System.in);
        System.out.print("Welcome to the ClassCreator Client.\n\n");
        System.out.println("Would you like to use the default port and ip address or specify your own?\n");
        System.out.println("0: Default\n1: Specify my own\n2: Exit\nPlease select an option... ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String ip = null;
        int port = 0;

        if (choice == 0) {
            ip = "0.0.0.0";
            port = 4242;
        } else if (choice == 1) {
            System.out.print("IP Address: ");
            ip = scanner.nextLine();
            System.out.print("Port: ");
            port = scanner.nextInt();
            scanner.nextLine();
        }

        try {
            // Create socket with ip and port:
            Socket socket = new Socket(ip, port);

            // Create input and output streams
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // Send server username of user
            oos.writeUTF(System.getProperty("user.name"));
            oos.flush();

            // Receive welcome message from server
            System.out.println("\n" + ois.readUTF());

            // Get the client's outline file's name
            System.out.println("What is the name of the file that contains the outline for your project?");
            String fileName = scanner.nextLine();

            File file = new File(fileName);
            String projectDir = file.getAbsoluteFile().getParent() + "/";
            oos.writeObject(file);
            oos.flush();

            // Get the language being used
            System.out.println("What language are you using the ClassCreator for?\n0: Java\n1: C\n2. Cancel");
            int language = scanner.nextInt();

            if (language == 0) {
                oos.writeChar('J');
            } else if (language == 1) {
                oos.writeChar('C');
            } else if (language == 2) {
                oos.writeChar('E');
                oos.flush();
                System.out.println(ois.read());

                oos.flush();
                oos.close();
                ois.close();
                return;
            }
            oos.flush();

            // Receive project name from server

            String projectName = ois.readUTF();

            // Receive list of classes back from server

            int numClasses = ois.readInt();

            List<Class> classes = new ArrayList<>();
            for (int i = 0; i < numClasses; i++) {
                classes.add((Class) ois.readObject());
            }

            // Write files to system
            ProjectWriter writer = new ProjectWriter(classes, projectDir);

            writer.writeProject();

            System.out.printf("Your Project: \"%s\" has been created successfully in the path: \"%s\"\n", projectName, projectDir);

            oos.writeUTF("Success");

            System.out.println("You have now been disconnected from the ClassCreator server.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
