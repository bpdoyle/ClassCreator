import java.util.ArrayList;
import java.util.List;

public class Class {
    String name;
    private List<Field> fields;
    private List<Method> methods;

    public Class(String name) {
        this.name = name;
        fields = new ArrayList<>();
        methods = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public List<Field> getFields() {
        return this.fields;
    }

    public List<Method> getMethods() {
        return this.methods;
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public void addMethod(Method method) {
        methods.add(method);
    }
}
