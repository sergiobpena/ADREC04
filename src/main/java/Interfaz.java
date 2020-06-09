import java.util.Scanner;

public class Interfaz {
    private  Scanner sc= new Scanner(System.in);
    private Facade fc;
    private static Interfaz inf=null;
    private Interfaz(String rutaDb,String xml,String xml_actualizacion){
        this.fc= Facade.getFacade(rutaDb,xml,xml_actualizacion);
    }

    public static Interfaz getInterfaz(String absolutePath, String xml, String xml_actualizado) {
        if (inf==null){
            inf=new Interfaz(absolutePath,xml,xml_actualizado);
        }
        return inf;
    }

    public void selector(){
        Boolean flag = true;
        while (flag) {
            System.out.print("\n\nIndique a operacion que desexe realizar, introducindo o numero correspondente: \n\n"
                    + "[1]-Paises con mais casos que un determinado numero a introducir \t \n"
                    + "[2]-Pais con mais mortes segundo o dia a introducir  \n"
                    + "[3]-Listado de paises con mais casos detectados por dia \n"
                    + "[4]-Listado de paises con maior crecemento de casos detectados por dia \n"
                    + "[5]-Cargar información \n"
                    + "[6]-Actualizar información \n"
                    + "[99]-Finaliza programa \n\n[Operacion]: ");
            int operacion = this.sc.nextInt();


            switch (operacion) {
                case 1:
                    System.out.println("Introduce numero de casos detectados : ");
                    int numero=this.sc.nextInt();
                    fc.listaListaPaisesNumero(numero);
                    break;
                case 2:
                    System.out.println("Maximo de casos nun dia por pais : ");
                    fc.paisMaisCasosDia();
                    break;
                case 3 :
                    System.out.println("Listado de paises con mais casos por dia");
                    fc.paisMaisCasosPorDia();
                    break;
                case 5:
                    System.out.println("Cargando datos iniciais do arquivo coronavirus.xml");
                    fc.cargaEgarda();
                    System.out.println("Datos cargados \n");
                    break;
                case 6:
                    System.out.println("Cargando arquivo actualización");
                    fc.actualizarSerie();
                    System.out.println("Datos actualizados");
                    break;
                case 99:
                    flag=false;
                    break;
            }
        }
        System.out.println("Saindo do programa");
    }

    private String formateaData(String data) {
        String[] auxiliar=data.split("/");
        String formateada= null;
        if (auxiliar.length == 3 && auxiliar[0].length()==2 && auxiliar[1].length()==2 && auxiliar[2].length()==4 ){
            formateada= auxiliar[2]+"/" + auxiliar[1] +"/" +  auxiliar [0];
        }else{
            throw new RuntimeException("Erro no formato da data");
        }
        return formateada;
    }


    public static void main(String[] args) {
        Integer.parseInt("s");
    }
}
