package Backend;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Personnel extends Personne {
    private String grade;
    private  int nb_prets;

    private String mot_de_passe;

    private
    //constructeur avec para
    Personnel(int code_pers, String nom_pers, String prenom_pers, String email_pers, LocalDate date_entree_pers,int nb_prets ,
              String grade,String mot_de_passe)
    {
        super(code_pers,nom_pers,prenom_pers,email_pers,date_entree_pers);
        this.grade=grade;
    };

    public Personnel()
    {

    };



    public Personnel login(String nom, String Prenom, String mdp, Connection connection)
    {
        try{
            PreparedStatement statement=connection.prepareStatement("select idpersonnel,nom_pers,prenom_pers,email_pers,date_entree_pers,nb_prets,grade,mot_de_passe from personnel where nom_pers= ? and prenom_pers=? and mot_de_passe = ?");
            statement.setString(1,nom);
            statement.setString(2,Prenom);
            statement.setString(3,mdp);
            ResultSet resultSet =    statement.executeQuery();
           if(resultSet.next()) {
                return new Personnel(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDate(5).toLocalDate(),
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getString(8)
                );
            }

        }
        catch(Exception e)
        {
            System.out.println(e);
        };
        return null;

    }

    public  void terminer_pret(int id_pret , String nomouvrage , String nomauteur , String nomlecteur, String prenomlecteur, Connection connection){
        try {
            System.out.println(id_pret);
          PreparedStatement  preparedStatement = connection.prepareStatement("update pret set fin_pret = ? , termine = 1 where id_pret = ?");
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
            preparedStatement.setInt(2,id_pret);
            preparedStatement.executeUpdate();

           preparedStatement = connection.prepareStatement("update lecteur set nb_prets_actuel = nb_prets_actuel - 1 where nomlecteur = ? and prenomlecetur = ? ");
           preparedStatement.setString(1,nomlecteur);
            preparedStatement.setString(2,prenomlecteur);


             preparedStatement =  connection.prepareStatement("update ouvrage set nb_prets = nb_prets - 1 where nomouvrage = ? and auteur = ?");

            preparedStatement.setString(1,nomouvrage);
            preparedStatement.setString(2,nomauteur);

            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
   }
    public  boolean ajouter_pret(String nomlecteur, String prenomlecteur , String nomouvrage , String nomauteur , int days , Connection connection){
        try {
            ArrayList<Ouvrage> ouvrages = get_ouvrages(connection);
            Ouvrage this_o = null;
            for (Ouvrage o : ouvrages){
                if(Objects.equals(o.getnom(), nomouvrage) && Objects.equals(o.getAuteur(), nomauteur)){
                    this_o = o;
                    break;
                }
            }


            if(this_o != null)  {
                int nb = this_o.getNb();
                int nb_prets = this_o.getNb_prets();
                int idouvrage = this_o.getId_ouvraged();
                if (nb - nb_prets > 0) {
                    ArrayList<Lecteur> lecteurs = get_lecteurs(connection);
                    Lecteur this_l = null;
                    for (Lecteur l : lecteurs){
                        if(Objects.equals(l.getnom(), nomlecteur) && Objects.equals(l.getPrenom(), prenomlecteur)){
                            this_l = l;
                            break;
                        }
                    }
                    if(this_l != null){
                        int idlecteur = this_l.getId();
                        System.out.println(idlecteur+" "+idouvrage + " " + getId());

                     PreparedStatement preparedStatement  =  connection.prepareStatement("update ouvrage set nb_prets = nb_prets + 1 where id_ouvrage =  ?");
                      preparedStatement.setInt(1,idouvrage);
                      preparedStatement.executeUpdate();
                      preparedStatement =  connection.prepareStatement("update lecteur set nb_prets = nb_prets + 1 , nb_prets_actuels = nb_prets_actuels + 1 where id_lec = ?");
                         preparedStatement.setInt(1,idlecteur);
                         preparedStatement.executeUpdate();
                        preparedStatement =    connection.prepareStatement("insert into pret(id_lec,id_ouvrage,id_personnel,debut_pret,fin_pret) values (?,?,?,?,?)" );
                        preparedStatement.setInt(1,idlecteur);
                        preparedStatement.setInt(2,idouvrage);
                        preparedStatement.setInt(3,getId());
                        preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));
                        preparedStatement.setDate(5, Date.valueOf(LocalDate.now().plusDays(days)));
                        preparedStatement.executeUpdate();
                        return  true;
                    }else{
                        JOptionPane.showMessageDialog(null,"Lecteur introuvable");
                    }



                }else{
                    JOptionPane.showMessageDialog(null,"Ouvrage indiponible");

                }
            }else {
                JOptionPane.showMessageDialog(null,"Ouvrage introuvable");

            }
        }catch (Exception e){
            System.out.println(e);
        }
        return  false;
    }
   public void supprimer_ouvrage(int id_ouvrage , Connection connection){
        try {
            PreparedStatement statement=connection.prepareStatement("delete from ouvrage where id_ouvrage = ?");
            statement.setInt(1,id_ouvrage);
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public ArrayList<Lecteur> get_lecteurs_avertis(Connection connection){
        try {

            ArrayList<Lecteur> lecteurs = new ArrayList<>(0);
            get_lecteurs(connection).forEach(lecteur -> {
               if(lecteur.getNb_alerts() > 0) {
                    lecteurs.add(lecteur);
                }
            });

            lecteurs.sort(new Comparator<Lecteur>() {
                @Override
                public int compare(Lecteur o1, Lecteur o2) {
                    return o1.getNb_alerts()>o2.getNb_alerts()?1:0;
                }
            });
            return  lecteurs;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public void envoyer_alertes(int id_lecteur , Connection connection){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update lecteur set nb_alertes = nb_alertes +1 where id_lec = ?");
           preparedStatement.setInt(1,id_lecteur);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }

    }
    public void supprimer_lecteur(int id_lecteur , Connection connection){
        try {
            PreparedStatement statement=connection.prepareStatement("delete from lecteur where id_lec = ?");
            statement.setInt(1,id_lecteur);
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void ajouter_personnel(String nom , String prenom , String email , String  grade, String mdp, Connection connection){
        try{
            PreparedStatement statement=connection.prepareStatement("insert into personnel(nom_pers,prenom_pers,email_pers,date_entree_pers,nb_prets,grade,mot_de_passe) values(?,?,?,?,0,?,?)");
            statement.setString(1,nom);
            statement.setString(2,prenom);
            statement.setString(3,email);
            statement.setDate(4, Date.valueOf(LocalDate.now()));
            statement.setString(5,grade);
            statement.setString(6,mdp);
            statement.executeUpdate();




        }
        catch(Exception e)
        {
            System.out.println(e);
        };
    };


    public ArrayList<Ouvrage> get_ouvrages(Connection connection){
      try {
          Statement statement = connection.createStatement();
          ResultSet resultSet=statement.executeQuery("select id_ouvrage , nomouvrage , auteur,date_entree,nb,nb_prets from ouvrage");
          ArrayList<Ouvrage> ouvrages = new ArrayList<Ouvrage>(0);
          while(resultSet.next()){
              ouvrages.add(new Ouvrage(
                      resultSet.getInt(1),
                      resultSet.getString(2),
                      resultSet.getString(3),
                      resultSet.getDate(4).toLocalDate(),
                      resultSet.getInt(5),
                      resultSet.getInt(6)
              ));
          }
          return ouvrages;
      }
      catch(Exception e)
      {
          System.out.println(e);
      };
      return  null;
  }

   public void ajouter_ouvrage(String nomouvrage, String nomauteur, int nb, Connection connection){

    try {
       PreparedStatement preparedStatement = connection.prepareStatement("select id_ouvrage from ouvrage where nomouvrage = ? and auteur = ?");
        preparedStatement.setString(1,nomouvrage);
        preparedStatement.setString(2,nomauteur);
        ResultSet resultSet =  preparedStatement.executeQuery();
        boolean cursor = resultSet.next();
        int id_ouvrage ;
        if(cursor ){
            id_ouvrage = resultSet.getInt(1);
            preparedStatement = connection.prepareStatement("update ouvrage set nb = nb + ? where id_ouvrage = ?");
            preparedStatement.setInt(1,nb);
            preparedStatement.setInt(2,id_ouvrage);
            preparedStatement.executeUpdate();
        }
        else{
            preparedStatement = connection.prepareStatement("insert into  ouvrage(nomouvrage,auteur,nb,nb_prets,date_entree) values (?,?,?,0,?)");
            preparedStatement.setString(1,nomouvrage);
            preparedStatement.setString(2,nomauteur);
            preparedStatement.setInt(3,nb);
            preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));
            preparedStatement.executeUpdate();

        }

    }catch (Exception e ){
        System.out.println(e);
    }


   }

   public  ArrayList<Pret> get_pret(Connection connection){
       try {
           Statement statement = connection.createStatement();
           ResultSet resultSet=statement.executeQuery("select id_pret,nomouvrage,auteur,nom_lec,prenom_lec,debut_pret,fin_pret,termine from pret join lecteur on pret.id_lec = lecteur.id_lec join ouvrage on pret.id_ouvrage = ouvrage.id_ouvrage");
           ArrayList<Pret> prets = new ArrayList<>(0);
           while(resultSet.next()){
               prets.add(new Pret(
                       resultSet.getInt(1),
                       resultSet.getString(2),
                       resultSet.getString(3),
                       resultSet.getString(4),
                       resultSet.getString(5),
                       resultSet.getDate(6).toLocalDate(),
                       resultSet.getDate(7).toLocalDate(),
                       resultSet.getInt(8)


                       ));
           }
           return prets;
       }
       catch(Exception e)
       {
           System.out.println(e);
       };
       return  null;
   }
    public ArrayList<Lecteur> get_lecteurs(Connection connection)
    {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery("select id_lec,nom_lec,prenom_lec,email_lec,date_entree_lec,nb_prets,nb_prets_actuels,nb_alertes,type_abonnement from lecteur");
            ArrayList<Lecteur> lecteurs = new ArrayList<>(0);
           while(resultSet.next()){
               lecteurs.add(new Lecteur(
                   resultSet.getInt(1),
                   resultSet.getString(2),
                   resultSet.getString(3),
                   resultSet.getString(4),
                   resultSet.getDate(5).toLocalDate(),
                   resultSet.getInt(6),
                   resultSet.getInt(7),
                       resultSet.getInt(8),
                       resultSet.getInt(9)
               ));
           }
           return lecteurs;
        }
        catch(Exception e)
        {
            System.out.println(e);
        };
        return  null;
    }






    public void ajouter_lecteur(String nom, String prenom, String email, int abonn, Connection connection)
    {
        try{
            PreparedStatement statement=connection.prepareStatement("insert into lecteur(nom_lec,prenom_lec,email_lec,date_entree_lec,nb_prets,nb_alertes,nb_prets_actuels,type_abonnement) values(?,?,?,?,0,0,0,?)");
            statement.setString(1,nom);
            statement.setString(2,prenom);
            statement.setString(3,email);
            statement.setDate(4, Date.valueOf(LocalDate.now()));
            statement.setInt(5,abonn);
           statement.executeUpdate();




        }
        catch(Exception e)
        {
            System.out.println(e);
        };
    };

    String get_grade(){
        return  grade;
    }

}
