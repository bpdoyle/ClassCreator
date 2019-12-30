import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ProjectWriter {
    private String projectDir;
    private List<Class> classes;

    public ProjectWriter(List<Class> classes, String projectDir) {
        this.classes = classes;
        this.projectDir = projectDir;
    }

    public void writeProject() {
        String line = "";
        for (Class c : classes){
            String fileName = c.getName();
            try (PrintWriter pw = new PrintWriter(new FileWriter(projectDir + fileName + ".java"))) {
                // Class Declaration
                line = "public class " + fileName + " {\n";
                pw.print(line);

                // Field Declarations
                for (Field f : c.getFields()) {
                    line = f.fieldString();
                    pw.print(line);
                }

                // Default Constructor
                line = "\n\tpublic " + fileName + "() {\n\n\t}\n\n";
                pw.print(line);

                // Getter and Setter Methods
                for (Field f : c.getFields()) {
                    line = f.createGetter();
                    pw.print(line);
                    line = f.createSetter();
                    pw.print(line);
                }

                // Method Creation
                for (Method m : c.getMethods()) {
                    line = m.methdodString();
                    pw.print(line);
                }

                // toString
                line = "\tpublic String toString() {\n\t\tString ret = \"\";\n\n\t\tret = String.format();\n\n\t\treturn ret;\n\t}\n";
                pw.print(line);
                pw.print("}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
