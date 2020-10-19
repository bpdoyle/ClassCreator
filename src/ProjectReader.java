import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectReader {
    private File file;
    private String projectName;
    private List<Class> classes;
    private int currentClass;
    private int numBraces;

    public ProjectReader(File file) {
        this.file = file;
        this.classes = new ArrayList<>();
        this.currentClass = -1;
        this.numBraces = 0;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public String getProjectName() { return projectName; }

    public void readProject() {
        String line = "";
        try (BufferedReader bfr = new BufferedReader(new FileReader(file))) {
            while ((line = bfr.readLine()) != null) {
                line = line.trim();
                if (numBraces == 0) { // Class Level
                    if (line.contains("{")) {
                        numBraces++;
                        if (numBraces == 1) {
                            String className = line.substring(1);
                            classes.add(new Class(className));
                            currentClass++;
                            bfr.readLine();
                        }
                    }
                    else {
                        projectName = line;
                    }
                }
                else if (numBraces == 1) { // Field Level
                    if (line.contains("}")) { // End of Field List
                        numBraces++;
                        bfr.readLine();
                        continue;
                    }

                    classes.get(currentClass).addField(Field.createField(line));
                }
                else if (numBraces == 2) { // Method Level
                    if (line.contains("}")) { // End of Method List
                        numBraces = 0;
                        bfr.readLine();
                        continue;
                    }
                    classes.get(currentClass).addMethod(Method.createMethod(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
