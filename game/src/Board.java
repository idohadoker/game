import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Board extends JPanel implements Runnable {
    Socket socket;

    InputStream inputStream;
    OutputStream outputStream;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    private Dimension d;
    private Alien[] aliens;
    private Player player;
    private Player opponent;
    private Shot opponent_shot;
    private Shot shot;
    boolean invasion_flag;
    private int deaths = 0;
    private String message;
    Thread t;

    public Board() {
        initsocket();
        opponent = new Player();
        opponent_shot = new Shot(0, 2);
        opponent_shot.setVisible(false);
        initBoard();
        gameInit();
    }

    private void initsocket() {
        try {
            socket = new Socket("localhost", 8888); // connect to the server on localhost on port 8888
            socket.setTcpNoDelay(true);
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(outputStream));
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(new BufferedInputStream(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

    }

    private void gameInit() {
        aliens = new Alien[24];
        addaliens();
        player = new Player();
        player.pos = -1;
        shot = new Shot(0, 2);
        shot.setVisible(false);
        shot.setVisible(false);
        t = new Thread(this);
        t.start();
    }

    private void addaliens() {
        int cnt = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                Alien alien = new Alien(Commons.ALIEN_INIT_X + 100 * j,
                        Commons.ALIEN_INIT_Y + 40 * i);
                aliens[cnt] = alien;
                cnt += 1;
            }
        }
        for (Alien alien : aliens)
            alien.start();
    }

    private void drawAliens(Graphics g) {
        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
        }
    }

    private void drawPlayer(Graphics g) {
        ImageIcon ii;
        if (opponent.ingame) {
            ii = new ImageIcon(player.playerImgs[1]);
            g.drawImage(ii.getImage(), opponent.getX(), opponent.getY(), null);
        }

        ii = new ImageIcon(player.playerImgs[0]);
        g.drawImage(ii.getImage(), player.getX(), player.getY(), null);


    }

    private void drawShot(Graphics g) {
        if (opponent_shot != null && opponent_shot.isVisible() && opponent.ingame) {
            g.drawImage(shot.getImage(), opponent_shot.getX(), opponent_shot.getY(), this);
        }

        if (shot.isVisible()) {
            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }

    }

    private void drawBombing(Graphics g) {
        for (Alien a : aliens) {
            Bomb b = a.getBomb();
            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black); // game background
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);// ground
        if (player.isIngame() || opponent.isIngame()) {
            g.drawLine(0, Commons.GROUND, Commons.BOARD_WIDTH, Commons.GROUND);
            drawAliens(g);
            drawPlayer(g);
            drawhearth(g);
            drawShot(g);
            drawBombing(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawhearth(Graphics g) {
        ImageIcon ii;
        ii = new ImageIcon("src/images/Life.png");

        for (int i = 0; i < player.health; i++) {

            g.drawImage(ii.getImage(), i * (25 + Commons.life_y), 920, 25, 25, this);
        }
        ii = new ImageIcon("src/images/life_opponent.png");

        for (int i = 0; i < opponent.health; i++) {

            g.drawImage(ii.getImage(), i * (25 + Commons.life_y) + 850, 920, 25, 25, this);

        }
    }


    private void gameended() {
        if (!invasion_flag) {
            message = deaths > opponent.death ? "Win" : "opponent win";
            System.out.println(deaths + " oppo " + opponent.death);
            if (deaths == opponent.death) {
                message = "draw";
            }
        }
        new SwingControlDemo(message);
    }

    private void update() {

        // hit alien
        for (int a = 0; a < aliens.length; a++) {
            Alien alien = aliens[a];
            if (alien.isVisible() && shot.isVisible()) {
                // hit alien
                if (shot.getX() >= (alien.getX())
                        && shot.getX() <= (alien.getX() + Commons.ALIEN_WIDTH)
                        && shot.getY() >= (alien.getY())
                        && shot.getY() <= (alien.getY() + Commons.ALIEN_HEIGHT)) {
                    player.pos = a;
                    alien.setDying(true);
                    deaths++;
                    shot.setVisible(false);
                }
            }
        }
        // move each alien down depends on direction 1 moving right -1 moving left
        // checks for each alien if he is touching ground
        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                if (alien.getY() > Commons.ALIENGROUND - Commons.ALIEN_HEIGHT) {
                    player.setIngame(false);
                    invasion_flag = true;
                    message = "Invasion!";

                }
            }
        }
//move down
        for (Alien alien : aliens) {
            int bo = 0;
            if (Alien.isBorder()) {
                bo = 1;
                for (Alien al : aliens)
                    al.setY(al.getY() + Commons.GO_DOWN);
            }
            if (bo == 1)
                Alien.setBorder(false);
        }
        // bombs
        Random generator = new Random();
        for (Alien alien : aliens) {
            int shot = generator.nextInt(15);
            Bomb bomb = alien.getBomb();
            // make sure they don't shoot at the same time
            if (shot == Commons.CHANCE && alien.isVisible() && bomb.isDestroyed()) {
                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }
            // player got hit by alien
            if (player.isVisible() && !bomb.isDestroyed()) {
                if (bomb.getX() >= (player.getX())
                        && bomb.getX() <= (player.getX() + Commons.PLAYER_WIDTH)
                        && bomb.getY() >= (player.getY())
                        && bomb.getY() <= (player.getY() + Commons.PLAYER_HEIGHT)) {
                    player.health --;
                    if(player.health == 0) {
                        player.setIngame(false);
                    }
                    bomb.setDestroyed(true);

                }
            }
        }
        Data d1, d2;
        try {
            d1 = new Data(player.x, player.y, player.pos, deaths, player.ingame, shot.x, shot.y, shot.visible,player.health);
            objectOutputStream.writeObject(d1);
            objectOutputStream.flush();
            d2 = (Data) objectInputStream.readObject();
            opponent.ingame = d2.ingame;
            if (d2.ingame) {
                if (d2.shot_visible) {
                    opponent_shot = new Shot(d2.shotx, d2.shoty);
                } else {
                    opponent_shot = null;
                }
                opponent.health = d2.health;
                opponent.x = d2.x;
                opponent.y = d2.y;
                opponent.pos = d2.pos;
                opponent.death = d2.death;
            } else {
                opponent.setDying(false);
            }


            if (opponent.pos > -1)
                aliens[opponent.pos].setDying(true);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void run() {
        while (player.ingame || opponent.ingame && (deaths + opponent.death != Commons.NUMBER_OF_ALIENS_TO_DESTROY)) {
            update();
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        gameended();
    }


    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                player.setDx(0);
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                player.setDx(0);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (player.isIngame()) {
                if (key == KeyEvent.VK_LEFT) {
                    player.setDx(-2);
                }
                if (key == KeyEvent.VK_RIGHT) {
                    player.setDx(2);
                }

                if (key == KeyEvent.VK_SPACE) {
                    // only 1 shot each time
                    if (!shot.isVisible()) {
                        shot = new Shot(player.getX(), player.getY());
                    }
                }
            }
        }
    }
}