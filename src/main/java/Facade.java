import dao.ControladorDAO;
import dao.xml.ConversorXML;
import modelos.Continente;

import java.util.HashMap;

public class Facade {
    private static Facade facade;
    private String rutaDb;
    private String rutaXML;
    private String rutaXML_actualizado;
    private Facade (String rutaDb , String rutaXML , String rutaXML_actualizado){
        this.rutaDb=rutaDb;
        this.rutaXML=rutaXML;
        this.rutaXML_actualizado=rutaXML_actualizado;
    }
    public void cargaEgarda(){
        ConversorXML conversorXML= new ConversorXML(rutaXML);
        HashMap<String, Continente> continenteHashMap=conversorXML.getContinentes();

            ControladorDAO controladorDAO = ControladorDAO.getControladorDao(rutaDb);
            controladorDAO.gardaContinentes(continenteHashMap);

    }
    public static Facade getFacade(String rutadb, String rutaXML, String rutaXML_actualizado){
        if(facade==null){
            facade=new Facade(rutadb,rutaXML,rutaXML_actualizado);
        }
        return facade;
    }
    public void listaListaPaisesNumero(int numero){
        ControladorDAO controladorDAO= ControladorDAO.getControladorDao(this.rutaDb);
        controladorDAO.obtenMaiorque(numero);
    }
    public void paisMaisCasosDia(){
        ControladorDAO controladorDAO= ControladorDAO.getControladorDao(this.rutaDb);
        controladorDAO.oberMaximosDia();
    }

    public void paisMaisCasosPorDia() {
        ControladorDAO.getControladorDao(this.rutaDb).obterPaisMaisCasosPorDia();
    }
    public void actualizarSerie(){
        ConversorXML conversorXML= new ConversorXML(this.rutaXML_actualizado);
        HashMap<String, Continente> continenteHashMap=conversorXML.getContinentes();
        ControladorDAO.getControladorDao(rutaDb).actualizaSerie(continenteHashMap.values());
    }
}
