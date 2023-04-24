import java.io.Serializable;

public class Data implements Serializable {

    int x;
    int y;
    int pos;
    int death;
    boolean ingame;
    int health;
int shotx;
int shoty;
boolean shot_visible;
public Data(){}

    @Override
    public String toString() {
        return "Data{" +
                "x=" + x +
                ", y=" + y +
                ", pos=" + pos +
                ", death=" + death +
                ", ingame=" + ingame +
                ", shotx=" + shotx +
                ", shoty=" + shoty +
                ", shot_visible=" + shot_visible +
                '}';
    }

    public Data(int x, int y, int pos, int death, boolean ingame, int shotx, int shoty, boolean shot_visible,int health) {
        this.x = x;
        this.y = y;
        this.pos = pos;
        this.death = death;
        this.ingame = ingame;
        this.shotx = shotx;
        this.shoty = shoty;
        this.shot_visible = shot_visible;
        this.health = health;
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

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }


}
