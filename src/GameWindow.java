import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameWindow extends JPanel implements Runnable{
    public static int GAME_WIDTH = 500;
    public static int GAME_HEIGHT = 700;
    public static Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    public static int PADDLE_WIDTH = 70;
    public static int PADDLE_HEIGHT = 14;
    public static int BRICK_WIDTH = 30;
    public static int BRICK_HEIGHT = 8;
    public static int BALL_DIAMETER = 12;
    int speedOfGame = 250;
    Ball ball;
    Score score;
    Paddle paddle;
    Graphics graphics;
    Thread gameThread;
    Image image;
    Brick[][] bricks = new Brick[14][8];
    Random random = new Random();

    public GameWindow() {
        this.setPreferredSize(SCREEN_SIZE);
        this.setFocusable(true);
        this.addKeyListener(new ActionListener());
        ball = new Ball(random.nextInt(GAME_WIDTH-BALL_DIAMETER) + BALL_DIAMETER/2,600,BALL_DIAMETER);
        paddle = new Paddle((GAME_WIDTH/2)-(PADDLE_WIDTH/2),650, PADDLE_WIDTH,PADDLE_HEIGHT);
        score = new Score(GAME_WIDTH,GAME_HEIGHT);
        for (int i = 0; i < 14; i++){
            for (int j = 0; j < 8; j++){
                if (j < 2){
                    bricks[i][j] = new Brick(7 + (i * 35), 3 + (j*17), BRICK_WIDTH, BRICK_HEIGHT, Color.RED);
                }else if(j < 4 && j > 1){
                    bricks[i][j] = new Brick(7 + (i * 35), 3 + (j*17), BRICK_WIDTH, BRICK_HEIGHT, Color.ORANGE);
                }
                else if(j < 6 && j > 3){
                    bricks[i][j] = new Brick(7 + (i * 35), 3 + (j*17), BRICK_WIDTH, BRICK_HEIGHT, Color.GREEN);
                }
                else if(j < 8&& j > 5){
                    bricks[i][j] = new Brick(7 + (i * 35), 3 + (j*17), BRICK_WIDTH, BRICK_HEIGHT, Color.YELLOW);
                }
            }
        }

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void paint(Graphics g){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }

    public void draw(Graphics g){
        paddle.draw(g);
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 8; j++) {
                bricks[i][j].draw(g);
            }
        }
        ball.draw(g);
        score.draw(g);
    }

    public void checkCollision(){
        //Ball bounce back from wall
        if (ball.x <= 0){
            ball.xVelocity = -ball.xVelocity;
        }else if (ball.x >= GAME_WIDTH-BALL_DIAMETER){
            ball.xVelocity = -ball.xVelocity;
        }
        if (ball.y <= 0) ball.yVelocity = -ball.yVelocity;

        //Ball bounce back from paddle
        if (ball.intersects(paddle)) {
            ball.yVelocity = -ball.yVelocity;
            //bigger speed
            //if (ball.yVelocity < 0) ball.yVelocity -= 1;
            //else ball.yVelocity += 1;
        }

        //ball is outside
        if (ball.y >= GAME_HEIGHT-BALL_DIAMETER){
            score.ballOut = true;
        }

        //paddle leave the map
        if (paddle.x <= 0) paddle.x = 0;
        else if(paddle.x > GAME_WIDTH-PADDLE_WIDTH) paddle.x = GAME_WIDTH-PADDLE_WIDTH;

        //Ball crashes Brick
        for (int i = 0; i < 14; i++){
            for (int j = 0; j < 8; j++){
                //the top of the ball hits the bottom of the brick
                if ((ball.y == bricks[i][j].y + BRICK_HEIGHT)
                        && ((ball.x >= bricks[i][j].x && ball.x <= bricks[i][j].x + BRICK_WIDTH)
                        || (ball.x + BALL_DIAMETER >= bricks[i][j].x && ball.x + BALL_DIAMETER <= bricks[i][j].x + BRICK_WIDTH)) && bricks[i][j].isAlive){
                    ball.yVelocity = -ball.yVelocity;
                    crashBrick(i,j);
                }
                else if ((ball.x == bricks[i][j].x + BRICK_WIDTH) //the left side of the ball hits the right side of the brick
                        && ((ball.y >= bricks[i][j].y && ball.y <= bricks[i][j].y + BRICK_HEIGHT)
                        || (ball.y + BALL_DIAMETER >= bricks[i][j].y && ball.y + BALL_DIAMETER <= bricks[i][j].y + BRICK_HEIGHT)) && bricks[i][j].isAlive){
                    ball.xVelocity = Math.abs(ball.xVelocity);
                    crashBrick(i,j);
                } else if ((ball.x + BALL_DIAMETER == bricks[i][j].x) //the right side of the ball hits the left side of the brick
                        && ((ball.y >= bricks[i][j].y && ball.y <= bricks[i][j].y + BRICK_HEIGHT)
                        || (ball.y + BALL_DIAMETER >= bricks[i][j].y && ball.y + BALL_DIAMETER <= bricks[i][j].y + BRICK_HEIGHT)) && bricks[i][j].isAlive){
                    ball.xVelocity = -ball.xVelocity;
                    crashBrick(i,j);
                } else if ((ball.y + BALL_DIAMETER == bricks[i][j].y) //the bottom of the ball hits the top of the brick
                        && ((ball.x >= bricks[i][j].x && ball.x <= bricks[i][j].x + BRICK_WIDTH)
                        || (ball.x + BALL_DIAMETER >= bricks[i][j].x && ball.x + BALL_DIAMETER <= bricks[i][j].x + BRICK_WIDTH)) && bricks[i][j].isAlive){
                    ball.yVelocity = -ball.yVelocity;
                    crashBrick(i,j);
                }
            }
        }
    }

    public void crashBrick(int i, int j){
        bricks[i][j].isAlive = false;
        bricks[i][j].color = Color.BLACK;
        bricks[i][j].draw(graphics);
        score.crashedBricks++;
    }

    public void move(){
        paddle.move();
        ball.move();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double ns = 1000000000 / speedOfGame;
        double delta = 0;
        while (true){
            long now = System.nanoTime();
            delta  += (now - lastTime)/ns;
            lastTime = now;
            if (delta >= 1){
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }

    class ActionListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            paddle.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            paddle.keyReleased(e);
        }
    }
}
