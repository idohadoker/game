import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Board extends JPanel {

    private Dimension d;


    private java.util.List<Alien> aliens;
    private Player player;
    private Shot shot;
    int rows = 1;
    private int deaths = 0;



    private String message = "Game Over";

    private Timer timer;

    public Board() {

        initBoard();
        gameInit();

    }


    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

        timer = new Timer(Commons.DELAY, new GameCycle());
        timer.start();

    }

    private void gameInit() {

        aliens = new ArrayList<Alien>();

        addaliens();

        player = new Player();
        shot = new Shot();
        shot.setVisible(false);
    }

    private void addaliens() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 6; j++) {
                Alien alien = new Alien(Commons.ALIEN_INIT_X + 100 * j,
                        Commons.ALIEN_INIT_Y + 40 * i,rows);
                aliens.add(alien);
                alien.start();
            }

        }
    }

    private void drawAliens(Graphics g) {

        for (Alien alien : aliens) {

            if (alien.isVisible()) {

                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }


        }
    }

    private void drawPlayer(Graphics g) {

        if (player.isVisible()) {

            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }


    }

    private void drawShot(Graphics g) {

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

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        g.setColor(Color.black); // game background
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);// ground

        if (player.ingame) {
            g.drawLine(0, Commons.GROUND,
                    Commons.BOARD_WIDTH, Commons.GROUND);


            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);

        } else {

            if (timer.isRunning()) {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {

        g.setColor(Color.black); // whole screen background
        g.fillRect(0, 0, Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

        g.setColor(Color.black); // message background
        g.fillRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);
        g.setColor(Color.blue); // outline
        g.drawRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white); // message text
        g.setFont(small);
        g.drawString(message, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
                Commons.BOARD_WIDTH / 2);

        gameended();
    }

    private void gameended() {
        // #TODO
        /*
         * try {
         * Thread.sleep(2000);
         * } catch (InterruptedException e) {
         * throw new RuntimeException(e);
         * }
         * String[] options = {"play again", "Exit"};
         * int response = JOptionPane.showOptionDialog(null, "Game ended ",
         * "choose option",
         * JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
         * null, options, options[0]);
         *
         *
         * switch (response) {
         * case -1:
         * System.out.println("Option Dialog Window Was Closed");
         * System.exit(0);
         * case 0:
         * getGraphics().dispose();
         *
         * new SpaceInvaders();
         * break;
         * case 1:
         * getGraphics().dispose();
         *
         * new SpaceInvaders();
         * break;
         * case 2:
         * System.exit(0);
         *
         * default:
         * break;
         * }
         */
    }

    private void update() {

        if (deaths == rows * 6) {
            deaths = 0;
            rows++;
            addaliens();
        }
        if (rows == 6) {
            player.ingame = false;
            timer.stop();
            message = "win!";
        }


        int shotX = shot.getX();
        int shotY = shot.getY();
        // hit alien
        for (Alien alien : aliens) {

            int alienX = alien.getX();
            int alienY = alien.getY();

            if (alien.isVisible() && shot.isVisible()) {
                // hit alien
                if (shotX >= (alienX)
                        && shotX <= (alienX + Commons.ALIEN_WIDTH)
                        && shotY >= (alienY)
                        && shotY <= (alienY + Commons.ALIEN_HEIGHT)) {


                    alien.setDying(true);
                    deaths++;
                    shot.die();
                }
            }
        }

        // move each alien down depends on direction 1 moving right -1 moving left

        // checks for each alien if he is touching ground
        for (Alien alien : aliens) {

            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > Commons.ALIENGROUND - Commons.ALIEN_HEIGHT) {
                    player.ingame = false;
                    timer.stop();
                    message = "Invasion!";
                    gameended();
                }


            }
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

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = player.getX();
            int playerY = player.getY();
            // player got hit by alien
            if (player.isVisible() && !bomb.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + Commons.PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + Commons.PLAYER_HEIGHT)) {


                    player.setDying(true);
                    bomb.setDestroyed(true);



                }
            }


        }
    }

    private void doGameCycle() {

        update();
        repaint();
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                player.dx = 0;
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                player.dx = 0;
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                player.dx = -2;
            }

            if (key == KeyEvent.VK_RIGHT) {

                player.dx = 2;
            }

            if (key == KeyEvent.VK_SPACE) {
                if (player.ingame) {
                    // only 1 shot each time
                    if (!shot.isVisible()) {
                        shot = new Shot(player.getX(), player.getY());
                    }
                }
            }
        }
    }
}