import java.io.Serializable;

public class Data implements Serializable {

    int x;
    int y;
    int pos;
    int death;
    boolean ingame;

public Data(){}

    public Data(int x, int y, int pos, int death, boolean ingame) {
        this.x = x;
        this.y = y;
        this.pos = pos;
        this.death = death;
        this.ingame = ingame;
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
