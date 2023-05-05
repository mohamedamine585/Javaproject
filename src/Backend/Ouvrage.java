package Backend;
import java.time.LocalDate;
public class Ouvrage {
    private int id_ouvrage;
    private String nom_ouvrage;
    private String auteur;
    private LocalDate date_entree;
    private int nb_prets;

    private int nb ;
    //constructeur avec para
    Ouvrage(int id_ouvrage, String nom_ouvrage, String auteur, LocalDate date_entree, int nb, int nb_prets
       )
    {
        this.id_ouvrage=id_ouvrage;
        this.nom_ouvrage=nom_ouvrage;
        this.auteur=auteur;
        this.date_entree=date_entree;
        this.nb = nb;
        this.nb_prets=nb_prets;
    };


   public int getId_ouvraged(){
        return  id_ouvrage;
    }
    public String getnom(){
       return  nom_ouvrage;
    }
    public  String getAuteur(){
       return  auteur;
    }
    public  LocalDate getDate_entree(){
       return  date_entree;
    }
    public  int getNb_prets(){
       return  nb_prets;
    }
    public  int getNb(){
       return  nb;
    }
}
