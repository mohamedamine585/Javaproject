package Backend;
import java.time.LocalDate;
public class Ouvrage {
    private int id_ouvrage;
    private String nom_ouvrage;
    private String auteur;
    private LocalDate date_entree;
    private int nb_prets;

    private int nb ;
    private String categorie;
    //constructeur avec para
    Ouvrage( int id_ouvrage,String nom_lec,String auteur,LocalDate date_entree,int nb,int nb_prets,
             String categorie)
    {
        this.id_ouvrage=id_ouvrage;
        this.nom_ouvrage=nom_ouvrage;
        this.auteur=auteur;
        this.date_entree=date_entree;
        this.nb_prets=nb_prets;
        this.categorie=categorie;
    };
}
