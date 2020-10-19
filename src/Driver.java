import java.io.File;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        // fileName is the outline text file
        String fileName = "";
        try {
            fileName = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please provide filename.");
            return;
        }

        File file = new File(fileName);
        String projectDir = file.getAbsoluteFile().getParent() + "/";

        ProjectReader reader = new ProjectReader(file);

        reader.readProject();

        List<Class> classes = reader.getClasses();

        ProjectWriter writer = new ProjectWriter(classes, projectDir);

        writer.writeProject();

        System.out.printf("Your Project: \"%s\" has been created successfully in the path: \"%s\"\n", reader.getProjectName(), projectDir);
    }
}
