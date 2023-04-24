
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SwingControlDemo {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    String message;
    Toolkit tk;

    public SwingControlDemo(String message) {
        tk= Toolkit.getDefaultToolkit();

        this.message = message;
        prepareGUI();
        showDialogDemo();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Game ended");
        mainFrame.setSize(400,200);
        mainFrame.setLayout(new GridLayout(3, 3));

        mainFrame.setLocation(tk.getScreenSize().width/2 - 200,tk.getScreenSize().height/2 -300 ) ;

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }

    private void showDialogDemo() {
        headerLabel.setText(message);

        JButton okButton = new JButton("exit");

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        controlPanel.add(okButton);
        mainFrame.setVisible(true);
    }
}