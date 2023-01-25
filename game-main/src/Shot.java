import javax.swing.*;
import java.util.ArrayList;

public class Shot extends mainclass {

    public Shot() {

    }

    public Shot(int x, int y) {

        initShot(x, y);
        start();
    }

    private void initShot(int x, int y) {

        String shotImg = "src/images/shot.png";
        ImageIcon ii = new ImageIcon(shotImg);
        setImage(ii.getImage());


        setX(x + 6);


        setY(y - 1);
    }

    @Override
    public void run() {
        while (this.isVisible()) {

            if (isVisible()) {
                    int y = this.y;
                    y -= 4;

                    if (y < 0) {
                        for (ImageIcon image : image_list) {
                            setImage(image.getImage());
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        setVisible(false);
                        break;
                    } else {
                        this.setY(y);
                    }

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
