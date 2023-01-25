import javax.swing.*;
import java.util.ArrayList;

public class Alien extends mainclass {

    private Bomb bomb;
    private static int direction= -1;
    private  static boolean border = false;


    public Alien(int x, int y) {
        initAlien(x, y);

    }

    public static boolean isBorder() {
        return border;
    }

    public static void setBorder(boolean border) {
        Alien.border = border;
    }

    private void initAlien(int x, int y) {

        this.x = x;
        this.y = y;
        bomb = new Bomb(x, y);
        this.setVisible(true);
        String alienImg = "src/images/alien.png";
        ImageIcon ii = new ImageIcon(alienImg);

        setImage(ii.getImage());
    }

    public void act() {

        this.x += direction;
    }

    public Bomb getBomb() {

        return bomb;
    }


    @Override
    public void run() {
        while (this.isVisible()) {
                // move each alien down depends on direction 1 moving right -1 moving left

                if (this.x + Commons.ALIEN_WIDTH / 2 >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && direction != -1) {
                    direction = -1;
                    border = true;
                }
                if (this.x <= Commons.BORDER_LEFT && direction != 1) {
                    direction = 1;
                    border = true;
                }

                this.act();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (isDying()) {
                    for (ImageIcon image : image_list) {
                        setImage(image.getImage());
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    this.setVisible(false);
                }

            }
        }
    }
