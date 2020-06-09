package dao;

import dao.interfaces.ContinenteDAO;
import dao.interfaces.PaisDAO;
import dao.interfaces.ReporteDAO;

import modelos.Continente;
import modelos.Pais;
import modelos.Reporte;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.persistence.Query;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

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
        try {
            for(Continente c : continenteHashMap.values()){
                this.session.save(c);
            }

            tran.commit();
        }catch (HibernateException e){
            System.out.println("Fallou algo cargando os datos");

        }
    }

    public void obtenMaiorque(int casos){

        Query qr = session.createQuery("SELECT p FROM Pais p");
        List<Pais> paises=qr.getResultList();

        for(Pais p : paises){
            if (p.totalCasos()>casos){
                System.out.println(p.getCountriesAndTerritories() + "\t Numero de casos: " + p.totalCasos());
            }
        }

    }

    public void oberMaximosDia(){
        Query hql1 = session.createQuery("SELECT max(r.deaths),r.dateRep,r.pais FROM Reporte r group by r.pais order by r.pais");
        List<Object[]> listaReportes=hql1.getResultList();

        for(Object[] o : listaReportes){
            System.out.println("-Data: " + o[1] + "\t Pais : " + o[2] + "\t Numero de falecidos : " + o[0]);
        }

    }


    public void paisMaisCasosDia(){
        Statement stmt = null;
        String sql = "";
    }


    public void obterPaisMaisCasosPorDia() {
        Query qr=session.createQuery("SELECT r.pais,max(r.cases),r.dateRep from Reporte r where r.cases>0 group by r.dateRep order by r.dateRep");
        List<Object[]> listado=qr.getResultList();
        for(Object[] o : listado){
            System.out.println("Data : " + o[2] + "\t Casos : " + o[1] + "\t Pais : " + o[0] );
        }

    }

    public HashMap<String , Continente> obtenContinentes(){
        HashMap<String,Continente> continentesActuais=new HashMap<String, Continente>();
        Query qr=session.createQuery("select c from Continente c");
        List<Continente> continentes=qr.getResultList();
        for(Continente c : continentes){
            continentesActuais.put(c.getContinentExp(),c);
        }
        return continentesActuais;
    }
    public void actualizaSerie(Collection<Continente> values) {
        Transaction tran= null;

        for (Continente e : values){
            tran=this.session.beginTransaction();
            session.saveOrUpdate(e);
            tran.commit();
        }



    }

    public Session getSession() {
        return session;
    }


    public void obterMaximoCrecementoPorDia(Date d) {
    }
}
