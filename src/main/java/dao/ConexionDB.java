package dao;

import java.sql.*;

public class ConexionDB {
    private static ConexionDB conexionDB;
    private Connection conexion;


    public Connection getConexion() {
        return conexion;
    }
    private ConexionDB(String ruta){
        this.conectate(ruta);
    }

    private void conectate(String filename){
        try{
            this.conexion=DriverManager.getConnection("jdbc:sqlite:" + filename);
            this.conexion.setAutoCommit(false);
            System.out.println("Conexion realizada");

        }catch (SQLException e){
            System.out.println("Non se realizou conexion");
            e.printStackTrace();
        }
    }


    public void desconectaBD(){
        try{
            if(this.conexion!=null){
                this.conexion.close();
                System.out.println("Desconectado");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static ConexionDB getConexionDB (String ruta){
        if(conexionDB == null){
            conexionDB=new ConexionDB(ruta);
        }
        return conexionDB;
    }

}
