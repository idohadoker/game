import javax.swing.*;

public class Powerups {

    int x,y;
    int selected_powerup;
    String[] playerImgs = { "src/images/speedup.png","src/images/sppeddown.png","src/images/life_powerup.png",};
    ImageIcon icon;
    public Powerups(int x,int y,int num){
        this.x = x;
        this.y =y;
        this.selected_powerup = num;
        icon = new ImageIcon(playerImgs[num]);

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}
