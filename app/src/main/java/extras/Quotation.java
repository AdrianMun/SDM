package extras;

/**
 * Created by admarcar on 07/02/2018.
 */

public class Quotation {

    private String cita;
    private String autor;

    public Quotation(String c, String a){
        cita = c;
        autor = a;
    }

    public String getCita(){
        return cita;
    }

    public String getAutor(){
        return autor;
    }

    public void setAutor(String a){
        autor = a;
    }

    public void setCita(String c){
        cita = c;
    }
}
