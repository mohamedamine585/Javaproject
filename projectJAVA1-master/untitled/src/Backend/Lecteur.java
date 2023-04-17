package Backend;
import java.time.LocalDate;
public class Lecteur extends Personne{
    private int nb_prets;
    private int nb_prets_actuel;
    private int nb_alerts;
    private int type_abonnement;
    //constructeur avec para
    Lecteur(int id_lec,String nom_lec,String prenom_lec,String email_lec,LocalDate date_entree_lec,int nb_prets,
            int nb_prets_actuel,int nb_alerts,int type_abonnement)
    {
        super(id_lec, nom_lec, prenom_lec, email_lec, date_entree_lec);
        this.nb_prets=nb_prets;
        this.nb_prets_actuel=nb_prets_actuel;
        this.nb_alerts=nb_alerts;
        this.type_abonnement=type_abonnement;
    };


}
