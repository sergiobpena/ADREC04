package modelos;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "paises")
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Transient
    HashMap<Date, Reporte> reportes=new HashMap<Date, Reporte>();
    @ManyToOne
    @JoinColumn(name="idContinente")
    private Continente continente;
    @Column
    private String popData2018;
    @Column(unique = true,nullable = false)
    private String countriesAndTerritories;
    @Column
    private String countryterritoryCode;
    @Transient
    private Reporte reporteAuxiliar;
    @OneToMany(mappedBy = "pais",cascade = CascadeType.ALL)
    private Set<Reporte> listaReportes=new HashSet<Reporte>();

    public Pais(){}

    public Pais(Pais p ){
        this.reportes.put(p.getReporteAuxiliar().getDateRep(),p.getReporteAuxiliar());
        this.popData2018=p.getPopData2018();
        this.countriesAndTerritories=p.getCountriesAndTerritories();
        this.countryterritoryCode=p.getCountryterritoryCode();
    }
    public Reporte getReporteAuxiliar() {
        return reporteAuxiliar;
    }

    public void setReporteAxiliar(Reporte r) {
        this.reporteAuxiliar = r;
    }


    public HashMap<Date, Reporte> getReportes() {
        return reportes;
    }

    public void setReportes(Reporte r) {
        this.reportes.put(r.getDateRep(),r);
    }
    public int casosDia(String d){
        Date data= null;

        try {
            data = new SimpleDateFormat("dd/MM/yyyy").parse(d);
            return this.reportes.get(data).getCases();
        } catch (ParseException e) {
            System.out.println("Erro no formato da data");
            return 999999999;
        }
    }
    public int mortesDia(String d ){
        Date data=null;
        try {
            data=new SimpleDateFormat("dd/MM/yyyy").parse(d);
        } catch (ParseException e) {
            System.out.println("Erro no formato de data");
        }
        return this.reportes.get(data).getDeaths();
    }

    public String getPopData2018() {
        return popData2018;
    }

    public void setPopData2018(String popData2018) {
        this.popData2018 = popData2018;
    }

    public String getCountriesAndTerritories() {
        return countriesAndTerritories;
    }

    public void setCountriesAndTerritories(String countriesAndTerritories) {
        this.countriesAndTerritories = countriesAndTerritories;
    }

    public String getCountryterritoryCode() {
        return countryterritoryCode;
    }

    public void setCountryterritoryCode(String countryterritoryCode) {
        this.countryterritoryCode = countryterritoryCode;
    }

    public void engadeReporte(Pais p) {
        this.reportes.put(p.getReporteAuxiliar().getDateRep(),p.getReporteAuxiliar());
        this.listaReportes.add(p.getReporteAuxiliar());
    }

    public void engadeReporte(Reporte r) {
        this.reporteAuxiliar=r;
        this.reportes.put(r.getDateRep(),r);
        this.listaReportes.add(r);
    }

    public Continente getContinente() {
        return continente;
    }

    public void setContinente(Continente continente) {
        this.continente = continente;
    }
}
