import javax.swing.*;

public class Bomb extends mainclass {

    private boolean destroyed;

    public Bomb(){

    }
    public Bomb(int x, int y) {
        initBomb(x, y);
    }

    private void initBomb(int x, int y) {

        setDestroyed(true);

        this.x = x;
        this.y = y;

        String bombImg = "src/images/bomb.png";
        ImageIcon ii = new ImageIcon(bombImg);
        setImage(ii.getImage());
        start();
    }

    public void setDestroyed(boolean destroyed) {
if (destroyed)
{
    for (ImageIcon image: image_list) {
        setImage(image.getImage());
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    String bombImg = "src/images/bomb.png";
    ImageIcon ii = new ImageIcon(bombImg);
    setImage(ii.getImage());

}
        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {

        return destroyed;
    }


    @Override
    public void run() {
while (true) {
    if (!this.isDestroyed()) {

        this.setY(this.getY() + 1);
        if (this.getY()-Commons.BOMB_HEIGHT/2 >= Commons.GROUND - Commons.BOMB_HEIGHT) {


            this.setDestroyed(true);
        }
    }

    try {
        Thread.sleep(10);
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
}
    }
}
