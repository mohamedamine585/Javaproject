package Frontend;

import Backend.Conn;
import Backend.Personnel;

import javax.swing.*;
import java.sql.Connection;

public class AjouterLecteur extends JFrame{
    private JPanel jPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField7;
    private JButton ajouterButton;
    private JButton annulerButton;
    private JPanel jpanel;

    AjouterLecteur(Personnel personnel , Connection connection){
        setContentPane(jpanel);
        pack();
        setVisible(true);

        annulerButton.addActionListener(l->{
            dispose();
        });
        ajouterButton.addActionListener(l->{

            personnel.ajouter_lecteur(textField1.getText(),textField2.getText(),textField3.getText(),Integer.parseInt(textField7.getText()),connection);
            dispose();
        });

    }

    public static void main(String[] args) {
        new AjouterLecteur(new Personnel(),(new Conn().Connect()));
    }

}
