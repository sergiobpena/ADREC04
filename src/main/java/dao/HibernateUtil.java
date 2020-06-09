package dao;

import dao.json.ContConfiguracion;
import dao.json.DatosConexionHib;
import modelos.Continente;
import modelos.Pais;
import modelos.Reporte;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(String rutaConfigJson){
        DatosConexionHib datosConexionHib= new ContConfiguracion(rutaConfigJson).getDatosConexionHib();
        if(sessionFactory == null){
            try {
                Configuration conf= new Configuration();

                Properties settings= new Properties();
                settings.put(Environment.DRIVER,datosConexionHib.getHibernate().getDriver() );
                String url=datosConexionHib.getDbConnection().getAddress() + ":" + datosConexionHib.getDbConnection().getPort() + "/" + datosConexionHib.getDbConnection().getName() ;
                String configuracion="?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                settings.put(Environment.URL,"jdbc:mysql://"+url+configuracion);

                settings.put(Environment.USER,datosConexionHib.getDbConnection().getUser());
                settings.put(Environment.PASS,datosConexionHib.getDbConnection().getPassword());

                settings.put(Environment.DIALECT,datosConexionHib.getHibernate().getDialect());

                settings.put(Environment.HBM2DDL_AUTO, datosConexionHib.getHibernate().getHBM2DDL_AUTO());
                settings.put(Environment.SHOW_SQL,datosConexionHib.getHibernate().isSHOW_SQL());
                conf.setProperties(settings);

                conf.addAnnotatedClass(Pais.class);
                conf.addAnnotatedClass(Continente.class);
                conf.addAnnotatedClass(Reporte.class);

                ServiceRegistry serviceRegistry= new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();

                sessionFactory=conf.buildSessionFactory(serviceRegistry);

            }catch (HibernateException e){
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

}
