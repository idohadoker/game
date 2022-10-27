import javax.swing.*;
import java.util.ArrayList;

public class Alien extends mainclass {

    private Bomb bomb;
    private int direction;


    public Alien(int x, int y) {

        initAlien(x, y);

    }

    private void initAlien(int x, int y) {

        this.x = x;
        this.y = y;
        this.direction = -1;
        bomb = new Bomb(x, y);

        String alienImg = "src/images/alien.png";
        ImageIcon ii = new ImageIcon(alienImg);

        setImage(ii.getImage());
    }

    public void act() {

        this.x += this.direction;
    }

    public Bomb getBomb() {

        return bomb;
    }




    @Override
    public void run() {
        while (true) {

            // move each alien down depends on direction 1 moving right -1 moving left

            if (this.x+Commons.ALIEN_WIDTH/2 >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && this.direction != -1) {
                this.direction = -1;
                this.setY(this.y + Commons.GO_DOWN);
            }
            if (this.x <=Commons.BORDER_LEFT && this.direction != 1) {
                this.direction = 1;
                this.setY(this.y + Commons.GO_DOWN);
            }

            this.act();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (isDying()){
                for (ImageIcon image: image_list) {
                    setImage(image.getImage());
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
die();
            }
        }
    }
}