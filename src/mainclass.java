import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class mainclass extends Thread {

    protected boolean visible;
    protected Image image;
    protected boolean dying;
    protected ArrayList<ImageIcon> image_list;
    protected int x;
    protected int y;

    public mainclass() {
        init_image_list();
        visible = true;

    }

    public void die() {

        visible = false;

    }


    public boolean isVisible() {

        return visible;
    }

    protected void setVisible(boolean visible) {

        this.visible = visible;
    }

    public void setImage(Image image) {

        this.image = image;
    }

    public Image getImage() {

        return image;
    }

    public void setX(int x) {

        this.x = x;
    }

    public void setY(int y) {

        this.y = y;
    }

    public int getY() {

        return y;
    }

    public int getX() {

        return x;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    private void init_image_list() {
        image_list = new ArrayList<>();
        image_list.add(new ImageIcon("src/images/explosion1.png"));
        image_list.add(new ImageIcon("src/images/explosion2.png"));
        image_list.add(new ImageIcon("src/images/explosion3.png"));
        image_list.add(new ImageIcon("src/images/explosion4.png"));
        image_list.add(new ImageIcon("src/images/explosion5.png"));
        image_list.add(new ImageIcon("src/images/explosion6.png"));
        image_list.add(new ImageIcon("src/images/explosion7.png"));
        image_list.add(new ImageIcon("src/images/explosion8.png"));
        image_list.add(new ImageIcon("src/images/explosion9.png"));
        image_list.add(new ImageIcon("src/images/explosion10.png"));
        image_list.add(new ImageIcon("src/images/explosion11.png"));
        image_list.add(new ImageIcon("src/images/explosion12.png"));

    }

    public boolean isDying() {

        return this.dying;
    }

}