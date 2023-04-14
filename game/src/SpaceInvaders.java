import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class SpaceInvaders extends JFrame implements Serializable {

    public SpaceInvaders() {
        initUI();
    }

    private void initUI() {
        boolean multiplayer_flag = getoption();
        if (multiplayer_flag) {
            add(new Board());
        }
        else {
            add(new Single());
        }

        setTitle("Space Invaders");
        setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    private boolean getoption() {
        int choice = JOptionPane.showOptionDialog(
                null,
                "Select game mode:",
                "Game Mode Selection",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Single Player", "Multiplayer"},
                "Single Player"
        );
        return (choice == JOptionPane.NO_OPTION);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new SpaceInvaders().setVisible(true);
        });
    }
}