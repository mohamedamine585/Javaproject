package Backend;
import java.time.LocalDate;

public class Pret {
    private int id_pret;
    private int id_lec;
    private  String nomouvrage ;
    private  String auteur ;
    private  String nomlecteur ;
    private  String prenomlecteur ;

    private  int termine ;
    private int id_ouvrage;
    private LocalDate debut_pret;
    private LocalDate fin_pret;
    private int id_personnel;
    //constructeur avec para
    Pret(int id_pret,String nomouvrage , String auteur , String nomlecteur, String prenomlecteur , LocalDate debut_pret , LocalDate fin_pret ,int termine)
    {
        this.id_pret=id_pret;
        this.nomlecteur=nomlecteur;
        this.prenomlecteur=prenomlecteur;
        this.debut_pret=debut_pret;
        this.fin_pret=fin_pret;
            this.auteur= auteur;
            this.nomouvrage = nomouvrage;
            this.termine = termine;
    };

    int getId_lec(){
        return id_lec;
    }
    int getId_ouvrage(){
        return  id_ouvrage;

    }


    public int getTermine() {
        return termine;
    }

    public LocalDate getFin_pret() {
        return fin_pret;
    }

    public String getAuteur() {
        return auteur;
    }

    public LocalDate getDebut_pret() {
        return debut_pret;
    }

    public String getNomlecteur() {
        return nomlecteur;
    }

    public String getNomouvrage() {
        return nomouvrage;
    }


    public int getId(){
        return  id_pret;
    }
    public String getPrenomlecteur() {
        return prenomlecteur;
    }

    int getId_personnel(){
        return  id_personnel;
    }

}
