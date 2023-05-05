package Backend;

import java.time.LocalDate;

public class Personne {
      private int id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate date_entree;

    Personne(int id, String nom, String prenom, String email, LocalDate date_entree)
    {
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.date_entree=date_entree;
    };
    Personne()
    {
    };



  public   String getnom(){
        return  nom;
    }
    public  String getPrenom(){
        return  prenom ;
    }

    public  String getemail(){
        return  email;
    }

    public  LocalDate get_date_entree(){
        return date_entree;
    }
    public int getId(){
        return  id ;
    }
}
