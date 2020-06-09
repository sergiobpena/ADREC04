package modelos;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "continentes")
public class Continente {
    @Transient
    private HashMap<String,Pais> paises=new HashMap<String, Pais>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String continentExp;

    @OneToMany(mappedBy = "continente",cascade = CascadeType.ALL)
    private Set <Pais> listaPaises=new HashSet<Pais>();



    public void enchePaises(){
        for (Pais p:paises.values()){
            p.setFk_continentExp(this.getContinentExp());
            this.listaPaises.add(p);
        }
    }



    public HashMap<String, Pais> getPaises() {
        return paises;
    }
    public void engadeReporte(Pais p){
        if (this.paises.containsKey(p.getCountriesAndTerritories())){
            this.paises.get(p.getCountriesAndTerritories()).engadeReporte(p);
        }else{
            Pais x= new Pais(p);
            this.paises.put(x.getCountriesAndTerritories(),x);
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


}
