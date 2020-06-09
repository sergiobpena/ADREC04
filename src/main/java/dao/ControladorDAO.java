package dao;

import modelos.Continente;
import modelos.Pais;
import modelos.Reporte;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.persistence.Query;
import java.sql.Statement;
import java.util.*;

/**
 * Clase singleton para manexo das operacions en base de datos.
 * Instancia os daos dos obxecto e crea as taboas se non existen.
 */
public class ControladorDAO {

    private static ControladorDAO controlador;

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
        ArrayList<Pais> paises=new ArrayList<Pais>();
        Query qr= this.session.createQuery("select p from Pais p");
        paises.addAll(qr.getResultList());
        for (Pais p : paises){
            p.encheMapas();
            List<Reporte> listado_reportes = new ArrayList<Reporte>(p.getListaReportes());
            Collections.sort(listado_reportes);
            int casosAnterior=0;
            double crecemento=0.0;
            Iterator<Reporte> it=listado_reportes.iterator();
            while (it.hasNext()){
                Reporte r= it.next();
                if (Double.valueOf(r.getCases()-Double.valueOf(casosAnterior))==0){
                    crecemento = 0 ;
                }else if (casosAnterior == 0){
                    crecemento=0.0;
                }else{
                    crecemento=(Double.valueOf(r.getCases())-Double.valueOf(casosAnterior))/Double.valueOf(casosAnterior);
                }
                r.setCrecemento(crecemento);
                casosAnterior=r.getCases();
            }
        }

        ArrayList<CrecementoPais> listado= new ArrayList<CrecementoPais>();
        for (Pais p : paises){
            Double crecemento=0.0;
            Reporte x= p.obterPorData(d);
            if(x!=null){
                crecemento=x.getCrecemento();
            }
            listado.add(new CrecementoPais(p.getCountriesAndTerritories(),crecemento));
        }
        Collections.sort(listado);

        for(CrecementoPais c : listado){
            System.out.println("- Pais : " + c.getPais() + "\t Crecemento : " + c.getCrecemento()*100 );
        }
    }

    public class CrecementoPais implements Comparable<CrecementoPais>{
        private String pais;
        private Double crecemento;
        public CrecementoPais(String pais, Double crecemento){
            this.pais=pais;
            this.crecemento=crecemento;
        }

        public Double getCrecemento() {
            return crecemento;
        }

        public String getPais() {
            return pais;
        }

        public int compareTo(CrecementoPais o) {
            return this.crecemento.compareTo(o.getCrecemento());
        }
    }
}
