package Backend;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class  Personnel extends Personne {
    private String grade;
    //constructeur avec para
    Personnel(int code_pers,String nom_pers,String prenom_pers,String email_pers,LocalDate date_entree_pers,
              String grade)
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
            PreparedStatement statement=connection.prepareStatement("select * from personnel where nom_pers= ? and prenom_pers=?");
            statement.setString(1,nom);
            statement.setString(2,Prenom);
            ResultSet resultSet =    statement.executeQuery();
            if(resultSet.first()){
                return new Personnel(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDate(5).toLocalDate(),
                        resultSet.getString(6)
                );
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        };
        return null;

    }

    public void terminer_pret(pret pret,Connection connection){
        try {
           PreparedStatement preparedStatement =  connection.prepareStatement("update ouvrage set nb_prets = nb_prets - 1 where idouvrage = ?");
           preparedStatement.setInt(1,pret.getId_ouvrage());
           preparedStatement.executeUpdate();
           preparedStatement = connection.prepareStatement("update lecteur set nb_prets_actuel = nb_prets_actuel - 1 where id_lec = ? ");
           preparedStatement.setInt(1,pret.getId_lec());
           preparedStatement = connection.prepareStatement("update pret set fin_pret = ? where code_pret = ?");
           preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
           preparedStatement.setInt(2,pret.getCode_pret());

        }catch (Exception e){
            System.out.println(e);
        }
   }
    public  void ajouter_pret(String nomlecteur, String prenomlecteur , String nomouvrage , String nomauteur , int days , Connection connection){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select nb,nb_prets,id_ouvrage from ouvrage where nomouvrage = ? and prenomouvrage = ?");
            preparedStatement.setString(1,nomouvrage);
            preparedStatement.setString(2,nomauteur);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean existe = resultSet.first();
            int nb = resultSet.getInt(1);
            if(existe)  {
                int nb_prets = resultSet.getInt(2);
                int idouvrage = resultSet.getInt(3);
                if (nb - nb_prets > 0) {
                    preparedStatement = connection.prepareStatement("select id_lec from lecteur where nomlecteur = ? and prenomlecteur = ? ");
                    preparedStatement.setString(1, nomlecteur);
                    preparedStatement.setString(2, prenomlecteur);
                    resultSet = preparedStatement.executeQuery();
                    existe = resultSet.first();
                    if(existe){
                        int idlecteur = resultSet.getInt(1);
                        connection.createStatement().executeQuery("update ouvrage set nb_prets = nb_prets + 1 where id_ouvrage "+"'"+idouvrage+"'");
                        connection.createStatement().executeQuery("update lecteur set nb_prets = nb_prets + 1 , nb_prets_actuel = nb_prets_actuel + 1 where id_lec "+"'"+idlecteur+"'");
                        connection.prepareStatement("insert into pret(id_lec,id_ouvrage,id_personnel,debut_pret,fin_pret) values (?,?,?,?,?)" );
                        preparedStatement.setInt(1,idlecteur);
                        preparedStatement.setInt(2,idouvrage);
                        preparedStatement.setInt(3,getId());
                        preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));
                        preparedStatement.setDate(5, Date.valueOf(LocalDate.now().plusDays(days)));
                        preparedStatement.executeQuery();
                    }



                }
            }
        }catch (Exception e){

        }
    }
    void supprimer_ouvrage(int id_ouvrage , Connection connection){
        try {
            PreparedStatement statement=connection.prepareStatement("delete from ouvrage where id_ouvrage = ?");
            statement.setInt(1,id_ouvrage);
            statement.executeUpdate();
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
    public void ajouter_personnel(String nom , String prenom , String email , String  grade, Connection connection){
        try{
            PreparedStatement statement=connection.prepareStatement("insert into personnel(nom_pers,prenom_pers,email_pers,date_entree_pers,nb_prets,nb_alertes,type_abonnement) values(?,?,?,?,0,0,?)");
            statement.setString(1,nom);
            statement.setString(2,prenom);
            statement.setString(3,email);
            statement.setDate(4, Date.valueOf(LocalDate.now()));
            statement.setString(5,grade);
            statement.executeQuery();




        }
        catch(Exception e)
        {
            System.out.println(e);
        };
    };


    public ArrayList<Ouvrage> get_ouvrages(Connection connection){
      try {
          Statement statement = connection.createStatement();
          ResultSet resultSet=statement.executeQuery("select id_ouvrage , nomouvrage , nomlecteur,date_entree,nb,nb_prets,categorie from ouvrage");
          ArrayList<Ouvrage> ouvrages = null;
          while(resultSet.next()){
              ouvrages.add(new Ouvrage(
                      resultSet.getInt(1),
                      resultSet.getString(2),
                      resultSet.getString(3),
                      resultSet.getDate(4).toLocalDate(),
                      resultSet.getInt(5),
                      resultSet.getInt(6),
                      resultSet.getString(7)
              ));
          }
      }
      catch(Exception e)
      {
          System.out.println(e);
      };
      return  null;
  }

    public void ajouter_ouvrage(String nomouvrage , String nomauteur , int nb , String categorie , Connection connection){

    try {
       PreparedStatement preparedStatement = connection.prepareStatement("select id_ouvrage from ouvrage where nomouvrage = ? and nomauteur = ?");
        preparedStatement.setString(1,nomouvrage);
        preparedStatement.setString(2,nomauteur);
        ResultSet resultSet =  preparedStatement.executeQuery();
        boolean cursor = resultSet.first();
        int id_ouvrage ;
        if(cursor ){
            id_ouvrage = resultSet.getInt(1);
            preparedStatement = connection.prepareStatement("update ouvrage set nb = nb + ? where id_ouvrage = ?");
            preparedStatement.setInt(1,nb);
            preparedStatement.setInt(2,id_ouvrage);
            preparedStatement.executeUpdate();
        }
        else{
            preparedStatement = connection.prepareStatement("insert into  ouvrage(nomouvrage,nomauteur,categorie,nb,nb_prets,date_entree) values (?,?,?,?,0,?)");
            preparedStatement.setString(1,nomouvrage);
            preparedStatement.setString(2,nomauteur);
            preparedStatement.setString(3,categorie);
            preparedStatement.setInt(4,nb);
            preparedStatement.setDate(5, Date.valueOf(LocalDate.now()));
            preparedStatement.executeUpdate();

        }

    }catch (Exception e ){

    }


   }

    public ArrayList<Lecteur> get_lecteurs(Connection connection)
    {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery("select id_lec,nom_lec,prenom_lec,email_lec,date_entree_lec,nb_prets,nb_prets_actuel,nb_alertes,type_abonnement from lecteur");
            ArrayList<Lecteur> lecteurs = null;
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
        }
        catch(Exception e)
        {
            System.out.println(e);
        };
        return  null;
    }






    public void ajouter_lecteur(String nom,String prenom,String email, int abonn,Connection connection)
    {
        try{
            PreparedStatement statement=connection.prepareStatement("insert into lecteur(nom_lec,prenom_lec,email_lec,date_entree_lec,nb_prets,nb_alertes,type_abonnement) values(?,?,?,?,0,0,?)");
            statement.setString(1,nom);
            statement.setString(2,prenom);
            statement.setString(3,email);
            statement.setDate(4, Date.valueOf(LocalDate.now()));
            statement.setInt(5,abonn);
           statement.executeQuery();




        }
        catch(Exception e)
        {
            System.out.println(e);
        };
    };

}
