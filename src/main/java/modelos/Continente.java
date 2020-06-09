package modelos;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "continentes")
public class Continente {
    @Transient
    private HashMap<String,Pais> paises=new HashMap<String, Pais>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true,nullable = false)
    private String continentExp;

    @OneToMany(mappedBy = "continente",cascade = CascadeType.ALL)
    private Set <Pais> listaPaises=new HashSet<Pais>();


    public Continente(){}


    public HashMap<String, Pais> getPaises() {
        return paises;
    }
    public void engadeReporte(Pais p){
        if (this.paises.containsKey(p.getCountriesAndTerritories())){
            p.getReporteAuxiliar().setPais(this.paises.get(p.getCountriesAndTerritories()));
            this.paises.get(p.getCountriesAndTerritories()).engadeReporte(p);
        }else{

            this.paises.put(p.getCountriesAndTerritories(),p);
            this.listaPaises.add(p);
        }
    }
    public int casosDia(String data){
        int casos=0;
        for (Pais p : this.paises.values()){
            casos=casos+p.casosDia(data);
        }
        return casos;
    }
    public int mortesDia(String data){
        int mortes=0;
        for(Pais p:this.paises.values()){
            mortes=mortes+p.mortesDia(data);
        }
        return mortes;
    }

    public void setPaises(HashMap<String, Pais> paises) {
        this.paises = paises;
    }

    public String getContinentExp() {
        return continentExp;
    }

    public void setContinentExp(String continentExp) {
        this.continentExp = continentExp;
    }

    public Set<Pais> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(Set<Pais> listaPaises) {
        this.listaPaises = listaPaises;
    }
    public void setListaPaises(Pais p ){
        this.listaPaises.add(p);
    }

    public void engadePais(Pais p) {
        this.listaPaises.add(p);
    }
    public boolean equals(Object o){
        if((o instanceof Continente)) return false;
        Continente c = (Continente) o;
        return this.getContinentExp().equals(((Continente) o).getContinentExp());
    }
    public void encheMapas(){

        for(Pais p : this.listaPaises){
            p.encheMapas();
            this.paises.put(p.getCountriesAndTerritories(),p);
        }
    }
}
