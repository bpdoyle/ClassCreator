import java.io.Serializable;

public class Field implements Serializable {
    private String access;
    private String nonStatic;
    private String type;
    private String fieldName;

    public Field(String access, String nonStatic, String type, String fieldName) {
        this.access = access;
        this.nonStatic = nonStatic;
        this.type = type;
        this.fieldName = fieldName;
    }

    public static Field createField(String line) {
        String[] split = line.split(" ");
        String accessMod = "";
        String staticMod = " ";
        String typeMod;
        String fieldMod;
        if (split.length == 2) {
            typeMod = split[0];
            fieldMod = split[1];
        } else if (split.length == 3) {
            if (split[0].equals("static")) {
                staticMod = " " + split[0] + " ";
            } else {
                accessMod = split[0];
            }
            typeMod = split[1];
            fieldMod = split[2];
        } else {
            accessMod = split[0];
            staticMod = " " + split[1] + " ";
            typeMod = split[2];
            fieldMod = split[3];
        }

        return new Field(accessMod, staticMod, typeMod, fieldMod);
    }

    public String createGetter() {
        String ret = "";

        String f = this.fieldName;
        String t = this.type;
        ret = String.format("\tpublic%s%s get%s() {\n\t\treturn this.%s;\n\t}\n\n", this.nonStatic, t, f.substring(0, 1).toUpperCase() + f.substring(1), f);

        return ret;
    }

    public String createSetter() {
        String ret = "";

        String f = this.fieldName;
        String t = this.type;
        ret = String.format("\tpublic%svoid set%s(%s %s) {\n\t\tthis.%s = %s;\n\t}\n\n", this.nonStatic, f.substring(0, 1).toUpperCase() + f.substring(1), t, f, f, f);

        return ret;
    }

    public String fieldString()  {
        String ret = "";

        ret = String.format("\t%s%s%s %s;\n", this.access, this.nonStatic, this.type, this.fieldName);

        return ret;
    }
}
