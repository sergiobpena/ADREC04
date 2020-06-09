import java.io.File;

public class Main {
    public static void main(String[] args) {
        String xml="coronavirus.xml";
        String configJson="config.json";
        String xml_actualizado="coronavirus_actualizado.xml";
        Interfaz intfaz= Interfaz.getInterfaz(configJson,xml,xml_actualizado);
        intfaz.selector();
    }
}
