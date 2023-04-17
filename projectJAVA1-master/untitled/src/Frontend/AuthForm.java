package Frontend;
import Backend.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;

public class AuthForm extends JFrame {
    private JLabel usernameLabel,lastnameLabel, passwordLabel;
    private JTextField usernameField , lastnameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel panel;

    public AuthForm() {
       Conn conn =    new Conn();
        setTitle("Login Form");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        add(panel);
        setTitle("Authentification");
        panel.setLayout(null);
        usernameLabel = new JLabel("Nom:");
        usernameLabel.setBounds(90,150,60,30);
        panel.add(usernameLabel);
        usernameField = new JTextField(45);
        usernameField.setBounds(165,150,250,30);
        panel.add(usernameField);


       lastnameLabel = new JLabel("Prenom:");
        lastnameLabel.setBounds(90,190,60,30);

        panel.add(lastnameLabel);

        lastnameField = new JTextField(45);
        panel.add(lastnameField);
          lastnameField.setBounds(165,190,250,30);
        passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);
        passwordLabel.setBounds(90,230,60,30);
        passwordField = new JPasswordField(47);
        passwordField.setBounds(165,230,250,30);
        panel.add(passwordField);

        loginButton = new JButton("Login");


        loginButton.setBounds(250,300,70,20);
        loginButton.addActionListener(this::actionPerformed);
        panel.add(loginButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            Personnel personnel = new Personnel();
          if(Conn.conn != null)  {
                personnel = personnel.login(usernameField.getText(), lastnameField.getText(), passwordField.getText(), Conn.conn);
                if (personnel == null) {
                    new JOptionPane("Le personnel est introuvable");
                } else {

                }
            }else{
               JOptionPane.showMessageDialog(this,"Echec de Connection");
          }

        }
    }


}
