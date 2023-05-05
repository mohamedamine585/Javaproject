package Frontend;

import Backend.Personnel;

import javax.swing.*;
import java.sql.Connection;


public class AjouterOuvrage extends  JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton ajouterButton;
    private JButton annulerButton;
    private JPanel jpanel;

    AjouterOuvrage(Personnel personnel , Connection connection){

        setContentPane(jpanel);
        pack();
      setVisible(true);
        annulerButton.addActionListener(l->{
            dispose();
        });
      ajouterButton.addActionListener(l->{
          personnel.ajouter_ouvrage(textField1.getText(),textField2.getText(),Integer.parseInt(textField3.getText()),connection);
          dispose();
      });
  }
}
