import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends mainclass {


     int dx;
    int pos;
    int death;
     boolean ingame;
    String[] playerImgs = {"src/images/player1.png", "src/images/player2.png"};

    public Player() {
        ingame = true;
        this.x = 500;
        this.y = 820;
        start();
    }



    @Override
    public void run() {
        while (this.isAlive()) {
                x += this.dx;

                if (x <= 2) {

                    x = 2;
                }

                if (x + Commons.PLAYER_WIDTH / 2 >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT) {

                    x = Commons.BOARD_WIDTH - Commons.BORDER_RIGHT - Commons.PLAYER_WIDTH / 2;
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (isDying()) {
                    for (ImageIcon image : image_list) {
                        setImage(image.getImage());
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    this.setVisible(false);
                    this.ingame = false;
                }

            }
        }


    public boolean isIngame() {
        return ingame;
    }

    public void setIngame(boolean ingame) {
        this.ingame = ingame;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }
}
