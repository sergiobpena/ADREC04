import dao.ControladorDAO;
import dao.xml.ConversorXML;
import modelos.Continente;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

public class Facade {
    private static Facade facade;
    private String rutaConfigJson;
    private String rutaXML;
    private String rutaXML_actualizado;

    private Facade(String rutaConfigJson, String rutaXML, String rutaXML_actualizado) {
        this.rutaConfigJson = rutaConfigJson;
        this.rutaXML = rutaXML;
        this.rutaXML_actualizado = rutaXML_actualizado;
        File arq = new File(rutaConfigJson);

        System.out.println("Cargando os datos iniciais...");
        this.cargaEgarda();

    }

    public void cargaEgarda() {
        ConversorXML conversorXML = new ConversorXML(rutaXML);
        HashMap<String, Continente> continenteHashMap = conversorXML.getContinentes();

        ControladorDAO controladorDAO = ControladorDAO.getControladorDao(rutaConfigJson);
        controladorDAO.gardaContinentes(continenteHashMap);
        System.out.println("Datos iniciais cargados");

    }

    public static Facade getFacade(String rutadb, String rutaXML, String rutaXML_actualizado) {
        if (facade == null) {
            facade = new Facade(rutadb, rutaXML, rutaXML_actualizado);
        }
        return facade;
    }

    public void listaListaPaisesNumero(int numero) {
        ControladorDAO controladorDAO = ControladorDAO.getControladorDao(this.rutaConfigJson);
        controladorDAO.obtenMaiorque(numero);
    }

    public void paisMaisCasosDia() {
        ControladorDAO controladorDAO = ControladorDAO.getControladorDao(this.rutaConfigJson);
        controladorDAO.oberMaximosDia();
    }

    public void paisMaisCasosPorDia() {
        ControladorDAO.getControladorDao(this.rutaConfigJson).obterPaisMaisCasosPorDia();
    }

    public void actualizarSerie() {
        ConversorXML conversorXML = new ConversorXML(this.rutaXML_actualizado);
        conversorXML.setContinentesBD(ControladorDAO.getControladorDao(rutaConfigJson).obtenContinentes());
        HashMap<String, Continente> continenteHashMap = conversorXML.getContinentes();
        ControladorDAO.getControladorDao(rutaConfigJson).actualizaSerie(continenteHashMap.values());
    }

    public void maximoCrecementoPorDia(Date d) {
        ControladorDAO.getControladorDao(rutaConfigJson).obterMaximoCrecementoPorDia(d);
    }

    public void pechaConexion() {
            //

    }
}