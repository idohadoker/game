import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class mainclass extends Thread {

     boolean visible;
     Image image;
     boolean dying;
     ImageIcon[] image_list;
     int x;

     int y;

    public mainclass() {
        init_image_list();
        visible = true;
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
        image_list = new ImageIcon[12];
        image_list[0] = new ImageIcon("src/images/explosion1.png");
        image_list[1]=new ImageIcon("src/images/explosion2.png");
        image_list[2]=new ImageIcon("src/images/explosion3.png");
        image_list[3]=new ImageIcon("src/images/explosion4.png");
        image_list[4]=new ImageIcon("src/images/explosion5.png");
        image_list[5]=new ImageIcon("src/images/explosion6.png");
        image_list[6]=new ImageIcon("src/images/explosion7.png");
        image_list[7]=new ImageIcon("src/images/explosion8.png");
        image_list[8]=new ImageIcon("src/images/explosion9.png");
        image_list[9]=new ImageIcon("src/images/explosion10.png");
        image_list[10]=new ImageIcon("src/images/explosion11.png");
        image_list[11] =new ImageIcon("src/images/explosion12.png");

    }

    public boolean isDying() {

        return this.dying;
    }

}