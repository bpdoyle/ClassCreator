public class Method {
    private String access;
    private String nonStatic;
    private String returnType;
    private String methodName;
    private String methodParameters; //With parentheses

    public Method(String access, String nonStatic, String returnType, String methodName, String methodParameters) {
        this.access = access;
        this.nonStatic = nonStatic;
        this.returnType = returnType;
        this.methodName = methodName;
        this.methodParameters = methodParameters;
    }

    public static Method createMethod(String line) {
        String[] split = line.split("[(]");
        String accessMod = "";
        String staticMod = " ";
        String returnType;
        String name;
        String parameters = "(" + split[1];
        split = split[0].split(" ");
        if (split.length == 2) {
            returnType = split[0];
            name = split[1];
        } else if (split.length == 3) {
            if (split[0].equals("static")) {
                staticMod = " " + split[0] + " ";
            } else {
                accessMod = split[0];
            }
            returnType = split[1];
            name = split[2];
        } else {
            accessMod = split[0];
            staticMod = " " + split[1] + " ";
            returnType = split[2];
            name = split[3];
        }


        return new Method(accessMod, staticMod, returnType, name, parameters);
    }

    public String methdodString() {
        String ret;

        ret = String.format("\t%s%s%s %s%s {\n\n\t}\n\n", this.access, this.nonStatic, this.returnType, this.methodName, this.methodParameters);

        return ret;
    }
}
