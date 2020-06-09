package dao;

import dao.interfaces.ContinenteDAO;
import dao.interfaces.PaisDAO;
import dao.interfaces.ReporteDAO;

import modelos.Continente;
import modelos.Pais;
import modelos.Reporte;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Clase singleton para manexo das operacions en base de datos.
 * Instancia os daos dos obxecto e crea as taboas se non existen.
 */
public class ControladorDAO {

    private static ControladorDAO controlador;
    private PaisDAO pais;
    private ContinenteDAO continente;
    private ReporteDAO reporte;
    private Session session;




    private ControladorDAO(String rutaConfigJson){
        this.session=HibernateUtil.getSessionFactory(rutaConfigJson).openSession();

    }

    public static ControladorDAO getControladorDao(String ruta){
        if (controlador == null){
            controlador=new ControladorDAO(ruta);
        }
        return controlador;
    }

    public PaisDAO getPais() {

        return pais;
    }

    public ContinenteDAO getContinente() {
        return continente;
    }

    public ReporteDAO getReporte() {
        return reporte;
    }


    public void gardaContinentes(HashMap<String, Continente> continenteHashMap) {
        Transaction tran= null;
        tran=this.session.beginTransaction();
        for (Continente e : continenteHashMap.values()){
            session.save(e);
        }
        tran.commit();
    }

    public void obtenMaiorque(int casos){

    }
    public void oberMaximosDia(){


    }


    public void paisMaisCasosDia(){
        Statement stmt = null;
        String sql = "";
    }


    public void obterPaisMaisCasosPorDia() {

    }

    public void actualizaSerie(Collection<Continente> values) {
        Transaction tran= null;
        tran=this.session.beginTransaction();
        for (Continente e : values){
            session.saveOrUpdate(e);
        }
        tran.commit();


    }
}
