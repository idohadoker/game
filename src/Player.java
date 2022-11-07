import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends mainclass {


    private int dx;
    private boolean ingame;

    public Player() {
        ingame = true;
        initPlayer();
        start();
    }

    private void initPlayer() {

        String playerImg = "src/images/player.png";
        ImageIcon ii = new ImageIcon(playerImg);
        setImage(ii.getImage());

        int START_X = 500;
        setX(START_X);

        int START_Y = 820;
        setY(START_Y);
    }



    @Override
    public void run() {
        while (this.isAlive()) {

            x += this.dx;

            if (x <= 2) {

                x = 2;
            }

            if (x+Commons.PLAYER_WIDTH/2 >= Commons.BOARD_WIDTH-Commons.BORDER_RIGHT) {

                x =Commons.BOARD_WIDTH-Commons.BORDER_RIGHT-Commons.PLAYER_WIDTH/2;
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
                die();
                this.ingame=false;
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
