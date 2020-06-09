package dao.json;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ContConfiguracion {

    private File configJson;
    private DatosConexionHib datosConexionHib;

    public ContConfiguracion(String ruta){
        this.configJson =new File(ruta);
    }

    private String leeJson() throws IOException {
        FileReader fr = new FileReader(this.configJson);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder jsonBuilder= new StringBuilder();
        String lina;
        while ((lina=br.readLine())!=null){
            jsonBuilder.append(lina).append("\n");
        }
        br.close();
        return  jsonBuilder.toString();
    }
    private void parseaJson() throws IOException {
        Gson gx= new Gson();
        DatosConexionHib d = gx.fromJson(leeJson(),DatosConexionHib.class);
        this.datosConexionHib=d;
    }
    public DatosConexionHib getDatosConexionHib(){
        DatosConexionHib datos=null;
        try {
            this.parseaJson();
            datos=this.datosConexionHib;
        } catch (IOException e) {
            System.out.println("Erro lendo o configJson JSON");
        }
        return datos;
    }

    public static void main(String[] args) {
        ContConfiguracion c= new ContConfiguracion("config.json");

        DatosConexionHib d= c.getDatosConexionHib();
        System.out.println(
                d.getHibernate().getDialect()
        );

    }
}
