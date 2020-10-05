import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 420;
    static final int SCREEN_HEIGHT = 420;
    static final int UNIT_SIZE = 15;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 100;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int snakeLength = 6;
    int foodConsumed;
    int foodX;
    int foodY;
    Direction direction = Direction.RIGHT;
    boolean running = false;
    Timer timer;
    Random random;

    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        generateFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if(running) {
            // GRID - (DEV)
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            // FOOD
            g.setColor(Color.MAGENTA);
            g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

            // SNAKE
            for (int i = 0; i < snakeLength; i++) {
                // TONGUE
                if (i == 0) {
                    g.setColor(Color.RED);
                    if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                        g.fillRect(x[i], y[i] + UNIT_SIZE / 2, UNIT_SIZE, UNIT_SIZE / 7);
                    } else {
                        g.fillRect(x[i] + UNIT_SIZE / 2, y[i], UNIT_SIZE / 7, UNIT_SIZE);
                    }
                    // HEAD
                } else if (i == 1) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    // BODY
                } else {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        }else{
            gameOver(g);
        }
    }

    public void generateFood() {
        foodX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        foodY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    public void move() {
        for (int i = snakeLength; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case UP -> y[0] = y[0] - UNIT_SIZE;
            case DOWN -> y[0] = y[0] + UNIT_SIZE;
            case LEFT -> x[0] = x[0] - UNIT_SIZE;
            case RIGHT -> x[0] = x[0] + UNIT_SIZE;
        }
    }

    public void checkFood() {

    }

    public void checkCollision() {
        // HEAD COLLIDING WITH BODY
        for (int i = snakeLength; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
                break;
            }
        }
        // HEAD COLLIDING WITH BORDERS
        if (x[0] < 0 || x[0] > SCREEN_WIDTH-UNIT_SIZE || y[0] < 0 || y[0] > SCREEN_HEIGHT-UNIT_SIZE) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFood();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != Direction.RIGHT) {
                        direction = Direction.LEFT;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != Direction.LEFT) {
                        direction = Direction.RIGHT;
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != Direction.DOWN) {
                        direction = Direction.UP;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != Direction.UP) {
                        direction = Direction.DOWN;
                    }
                    break;
            }
        }
    }
}