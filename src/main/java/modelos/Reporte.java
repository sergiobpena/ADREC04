package modelos;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Entity(name = "reportes")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateRep;
    @Column
    private int cases;
    @Column
    private int deaths;
    @ManyToOne
    @JoinColumn(name="idPais")
    private Pais pais;
    @Transient
    private Double crecemento;
    public Reporte(){}

    public Date getDateRep() {
        return dateRep;
    }

    public void setDateRep(String dateRep) {
        try {
            this.dateRep = new SimpleDateFormat("dd/MM/yyyy").parse(dateRep);
        } catch (ParseException e) {
            System.out.println("Erro no formato de data");
            this.dateRep=null;
        }
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
}
