package Frontend.assets;

import Backend.Personnel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import Backend.*;
    public  class Register extends JFrame{


        private JTextField textField1;
        private JTextField textField2;
        private JTextField textField3;
        private JTextField textField4;
        private JButton ajouter_pers;
        private JButton annulerButton;
        private JPanel jpanel;
        private JTextField textField5;


        public Register(Personnel personnel, Connection connection) {
            setContentPane(jpanel);
            setVisible(true);
            pack();

            ajouter_pers.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {

                         personnel.ajouter_personnel(textField1.getText(),textField2.getText(),textField3.getText(),textField4.getText(),textField5.getText(),connection);
                    } catch (Exception ex) {

                    }
                }
            });

        }


    }

