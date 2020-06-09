package dao.json;

public class DatosConexionHib {
    private DbConnection dbConnection;
    private Hibernate hibernate;

    public DatosConexionHib(){}

    public DbConnection getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Hibernate getHibernate() {
        return hibernate;
    }

    public void setHibernate(Hibernate hibernate) {
        this.hibernate = hibernate;
    }
}
