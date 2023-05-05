package Frontend;

import Backend.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class AjouterPrets extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton ajouterButton;
    private JButton annulerButton;
    private JPanel jpanel;

    public AjouterPrets(Personnel personnel , Connection connection) {
        setVisible(true);
    setContentPane(jpanel);
    pack();

    ajouterButton.addActionListener(l->{
        try {
            System.out.println(personnel.getId());
           boolean added =    personnel.ajouter_pret(textField1.getText(),textField2.getText(),textField3.getText(),textField4.getText(),Integer.parseInt(textField5.getText()),connection);
          if(added)  {
                dispose();
            }
        }catch (Exception e){
            System.out.println(e);
        }
    });
    annulerButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
               dispose();
        }
    });
}

}
