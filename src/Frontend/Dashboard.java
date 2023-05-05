package Frontend;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.tools.Tool;

import Backend.*;
import Frontend.assets.Register;

public class Dashboard extends JFrame {

    private JPanel contentPane;
    private JButton button1;
    private JButton button2;
    private JButton button3;

    private boolean isboxchecked  = false;
    private JCheckBox checkBox_on_avertis;

   private JMenuBar menuBar;
   private  JButton ajouterlect;
   private  JButton ajouterouv ;


   private JButton ajouterpersonnel;
   private  JButton ajouterpret;
    private  JButton logout;

     private  JButton env_alerte ;
    private  JButton deletebutton;
    private static JList<String> list;


    private JTextField SearchBar;


    private static DefaultListModel<String> Lecteurs = new DefaultListModel<>();
    private static DefaultListModel<String> Ouvrages = new DefaultListModel<>();
    private static DefaultListModel<String> Prets = new DefaultListModel<>();


    private  static  Personnel personnel;

    private  static  Connection connection;
    public Dashboard(Personnel Personnel,Connection Connection) {


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        personnel = Personnel;
        connection = Connection;

         menuBar = new JMenuBar();
         ajouterlect = new JButton("Ajouter lecteur");
         ajouterouv = new JButton("Ajouter ouvrage");
         ajouterpret = new JButton("Ajouter pret");
         ajouterpersonnel = new JButton("Ajouter personnel");
         logout = new JButton("Ajouter logout");
         menuBar.add(ajouterlect);
        menuBar.add(ajouterouv);
        menuBar.add(ajouterpret);
        menuBar.add(ajouterpersonnel);
        menuBar.add(new JLabel("                                                                                                                                                                                                                                                                                                                                                   "));
        menuBar.add(logout);
         setJMenuBar(menuBar);



        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setTitle("Dashboard");

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));





        setContentPane(contentPane);

        button1 = new JButton("Lecteurs");
        button2 = new JButton("Ouvrages");
        button3 = new JButton("Prets");
        logout = new JButton("Logout");
        env_alerte = new JButton("envoyer un alerte");

        checkBox_on_avertis= new JCheckBox("Lecteurs avertis");
        checkBox_on_avertis.addItemListener(l->{
            if(l.getStateChange() == ItemEvent.SELECTED){
                Lecteurs = fill_Lecteurs(personnel.get_lecteurs_avertis(connection));
                list.setModel(Lecteurs);
                SearchBar.setText("");
                isboxchecked = true;
            }
            if(l.getStateChange() == ItemEvent.DESELECTED){
                Lecteurs = fill_Lecteurs(personnel.get_lecteurs(connection));
                list.setModel(Lecteurs);
                SearchBar.setText("");
                isboxchecked = false;
            }
        });
        checkBox_on_avertis.setVisible(false);
        env_alerte.setVisible(false);



        SearchBar = new JTextField(30);
        deletebutton = new JButton("Supp Lect/Ouv ou Term Pret");

        JPanel buttonPanel = new JPanel() , SearchPanel = new JPanel();
        buttonPanel.add(new Label("                                                                                                    " ));
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(new JLabel("                                                             "));
         buttonPanel.add(logout);

        SearchPanel.add(SearchBar,BorderLayout.WEST);
        SearchPanel.add(deletebutton,BorderLayout.EAST);
        SearchPanel.add(checkBox_on_avertis);
        SearchPanel.add(env_alerte);

        contentPane.add(buttonPanel, BorderLayout.NORTH);
        contentPane.add(SearchPanel, BorderLayout.SOUTH);



        list = new JList<>();


        ajouterlect.addActionListener(l->{
            new AjouterLecteur(personnel,connection);

        });
        ajouterouv.addActionListener(l->{
            new AjouterOuvrage(personnel,connection);
        });
        ajouterpret.addActionListener(l->{
            new AjouterPrets(personnel,connection);
        });
        ajouterpersonnel.addActionListener(l->{
            new Register(personnel,connection);
        });

        button1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                deletebutton.setText("Delete lecteur");
                Lecteurs = fill_Lecteurs(personnel.get_lecteurs(connection));

                list.setModel(Lecteurs);
                Ouvrages.clear();
                Prets.clear();
                SearchBar.setText("");
                checkBox_on_avertis.setVisible(true);
                env_alerte.setVisible(true);
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Ouvrages = fill_Ouvrages(personnel.get_ouvrages(connection));
              deletebutton.setText("Delete ouvrage");
                Lecteurs.clear();
                Prets.clear();
                list.setModel(Ouvrages);
                SearchBar.setText("");
                checkBox_on_avertis.setVisible(false);
                env_alerte.setVisible(false);

            }
        });

        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletebutton.setText("Terminer pret");

                Prets = fill_Prets(personnel.get_pret(connection));
                 Ouvrages.clear();
                 Lecteurs.clear();
                list.setModel(Prets);
                SearchBar.setText("");
                checkBox_on_avertis.setVisible(false);
               env_alerte.setVisible(false);

            }
        });
        logout.addActionListener(l->{
         try {
                dispose();
                connection.close();
                new AuthForm();
            }catch (Exception e){
             System.out.println(e);
         }
        });

        env_alerte.addActionListener(l->{
           if(list.getSelectedIndex() >= 0) {
                personnel.envoyer_alertes(Integer.parseInt(Lecteurs.getElementAt(list.getSelectedIndex()).split(" ")[3]), connection);
                filter_items(Lecteurs, SearchBar.getText());
                if (isboxchecked)
                    Lecteurs = fill_Lecteurs(personnel.get_lecteurs_avertis(connection));
                else
                    Lecteurs = fill_Lecteurs(personnel.get_lecteurs(connection));

                list.setModel(Lecteurs);
            }
           else
               new JOptionPane().createDialog("Selectionnez un lecteur");

        });
        list.addListSelectionListener(l->{
            DefaultListModel<String> L =new DefaultListModel<String>();
            if(Lecteurs.size() != 0)
                L = Lecteurs;
            else if(Ouvrages.size() != 0)
                L = Ouvrages;
            else
                L = Prets;


        });

        deletebutton.addActionListener(l->{
         if(list.getSelectedIndex() >= 0)   {
                if (Lecteurs.size() != 0) {
                    personnel.supprimer_lecteur(Integer.parseInt(Lecteurs.getElementAt(list.getSelectedIndex()).split(" ")[3]), connection);
                    System.out.println(Integer.parseInt(Lecteurs.getElementAt(list.getSelectedIndex()).split(" ")[3]));
                    Lecteurs = fill_Lecteurs(personnel.get_lecteurs(connection));
                    SearchBar.setText(SearchBar.getText());
                    list.setModel(Lecteurs);
                } else if (Ouvrages.size() != 0) {
                    personnel.supprimer_ouvrage(Integer.parseInt(Ouvrages.getElementAt(list.getSelectedIndex()).split(" ")[3]), connection);
                    Ouvrages = fill_Ouvrages(personnel.get_ouvrages(connection));
                    SearchBar.setText(SearchBar.getText());
                    list.setModel(Ouvrages);
                }
                else if(Prets.size()!=0){

                   int idpret = Integer.parseInt(Prets.getElementAt(list.getSelectedIndex()).split(" ")[3]);
                   String nomouv = Prets.getElementAt(list.getSelectedIndex()).split(" ")[7];
                   String nomauteur = Prets.getElementAt(list.getSelectedIndex()).split(" ")[11];
                   String nomlect =  Prets.getElementAt(list.getSelectedIndex()).split(" ")[14];
                   String prenomlect = Prets.getElementAt(list.getSelectedIndex()).split(" ")[17];
                    personnel.terminer_pret(idpret,
                            nomouv,
                            nomauteur,
                            nomlect,
                            prenomlect
                            ,connection);

                    Prets = fill_Prets(personnel.get_pret(connection));
                    SearchBar.setText(SearchBar.getText());
                    list.setModel(Prets);
                }
            }
            else
                new JOptionPane().createDialog("Selectionner un item");

        });
           SearchBar.getDocument().addDocumentListener(
                   new DocumentListener() {
                       @Override
                       public void insertUpdate(DocumentEvent e) {
                           if(!(Ouvrages.isEmpty())){
                               filter_items(Ouvrages,SearchBar.getText());
                           }
                           else{
                               if(!(Lecteurs.isEmpty())){
                                   filter_items(Lecteurs,SearchBar.getText());
                               }
                               else{
                                   filter_items(Prets,SearchBar.getText());
                               }
                           }
                           System.out.println(SearchBar.getText());
                       }

                       @Override
                       public void removeUpdate(DocumentEvent e) {

                           if(!(Ouvrages.isEmpty())){
                               filter_items(Ouvrages,SearchBar.getText());
                           }
                           else{
                               if(!(Lecteurs.isEmpty())){
                                   filter_items(Lecteurs,SearchBar.getText());
                               }
                               else{
                                   filter_items(Prets,SearchBar.getText());
                               }
                           }
                           System.out.println(SearchBar.getText());
                       }

                       @Override
                       public void changedUpdate(DocumentEvent e)  {
                           if(!(Ouvrages.isEmpty())){
                               filter_items(Ouvrages,SearchBar.getText());
                           }
                           else{
                               if(!(Lecteurs.isEmpty())){
                                   filter_items(Lecteurs,SearchBar.getText());
                               }
                               else{
                                   filter_items(Prets,SearchBar.getText());
                               }
                           }
                           System.out.println(SearchBar.getText());
                       }
                   }
           );

        // Add the list to the center of the content pane
        contentPane.add(list, BorderLayout.CENTER);

        // Set the frame to be visible
        setVisible(true);
        setSize(1000,700);

    }




    static  void filter_items( DefaultListModel<String> L , String query){

        DefaultListModel<String> L0 = new DefaultListModel<>();

          try {
              for (int i = 0; i < L.size(); i++) {
                  if ((L.getElementAt(i).contains(query))) {
                      L0.addElement(L.elementAt(i));
                  }
              }
              list.setModel(L0);
          }catch (Exception e){
              System.out.println(e);
          }


    }

      DefaultListModel<String> fill_Lecteurs(ArrayList<Lecteur> lecteurs){
        DefaultListModel<String> List = new DefaultListModel<>();
        try {
            if(connection != null){


                lecteurs.forEach(element->{

                    List.addElement("Id lecteur = " + element.getId() + " Nom lecteur : "+element.getnom() + "      Prenom lecteur :        "+element.getPrenom()+ " Nombre de prets  : "+element.getNb_prets()+"           Type d'abonnement :   A"+element.getType_abonnement() + "  Date d'inscription :          "+element.get_date_entree() + "  nombre d'alertes : "+ element.getNb_alerts() );
                });

                return  List;
            }

        }catch (Exception e){
            System.out.println(e);
        }
        return  List;
    }

    DefaultListModel<String> fill_Ouvrages(ArrayList<Ouvrage> Ouvrages){
        DefaultListModel<String> List = new DefaultListModel<>();
        try {
            if(connection != null){


                Ouvrages.forEach(element->{

                    List.addElement("Id ouvrage = " + element.getId_ouvraged() +" Nom ouvrage : "+element.getnom() + " Nom auteur : "+element.getAuteur() + " Nombre total : "+element.getNb()+"       Nombre de prets : "+element.getNb_prets() + " Date d'entree : "+element.getDate_entree());
                });

                return  List;

            }
        }catch (Exception e){
            System.out.println(e);
        }
        return  List;
    }
    DefaultListModel<String> fill_Prets(ArrayList<Pret> prets){
        DefaultListModel<String> List = new DefaultListModel<>();
        try {
            if(connection != null){


                prets.forEach(element->{

                    List.addElement("Id pret : "+ element.getId()+ "  Nom Ouvrage : "+element.getNomouvrage() + " Nom auteur : "+element.getAuteur() + " Nom lecteur "+element.getNomlecteur()+" Prenom lecteur "+element.getPrenomlecteur() + " Debut pret :"+element.getDebut_pret() + " fin_pret : "+element.getFin_pret() + " termine : "+element.getTermine());
                });


                return  List;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return  List;
    }

    public static void main(String[] args) {
        new Dashboard(new Personnel(),(new Conn()).Connect());
    }

    }



