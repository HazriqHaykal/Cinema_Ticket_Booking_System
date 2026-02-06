
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ChangePass {

    JFrame miniFrame;
    JLabel oldPassLbl;
    JLabel newPassLbl;
    JPasswordField oldPass;
    JPasswordField newPass;
    JButton confirm;
    JButton cancel;
    JLabel phoneLbl; // modification
    JTextField phoneTxt; // modification

    public ChangePass() {
        initGUI();
    }

    public void initGUI() {
        miniFrame = new JFrame("Update Profile"); // modification
        oldPassLbl = new JLabel("Your current password:");
        newPassLbl = new JLabel("Your new password:");
        phoneLbl = new JLabel("Update Phone #:"); // modification
        phoneTxt = new JTextField(); // modification
        oldPass = new JPasswordField();
        newPass = new JPasswordField();
        confirm = new JButton("Confirm");
        cancel = new JButton("Cancel");

        oldPassLbl.setBounds(10, 30, 150, 30);
        newPassLbl.setBounds(10, 80, 150, 30);
        oldPass.setBounds(160, 30, 300, 30);
        newPass.setBounds(160, 80, 300, 30);
        phoneLbl.setBounds(10, 130, 150, 30); // modification
        phoneTxt.setBounds(160, 130, 300, 30); // modification
        cancel.setBounds(240, 180, 100, 30); // modification
        cancel.setBackground(Color.black);
        cancel.setForeground(Color.WHITE);
        confirm.setBounds(360, 180, 100, 30); // modification
        confirm.setBackground(Color.black);
        confirm.setForeground(Color.WHITE);

        miniFrame.add(phoneLbl); // modification
        miniFrame.add(phoneTxt); // modification
        miniFrame.add(oldPassLbl);
        miniFrame.add(oldPass);
        miniFrame.add(newPassLbl);
        miniFrame.add(newPass);
        miniFrame.add(cancel);
        miniFrame.add(confirm);

        miniFrame.setLayout(null);
        miniFrame.setResizable(false);
        miniFrame.setSize(500, 300); // modification
        miniFrame.setVisible(true);
    }
}
