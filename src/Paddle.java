import java.awt.*;
import java.awt.event.KeyEvent;

public class Paddle extends Rectangle{
    int speed = 2;
    int xVelocity;

    public Paddle(int x, int y, int width, int height) {
        super(x,y,width,height);
    }

    public void draw(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(x,y,width,height);
    }

    public void setDirection(int v){
        xVelocity = v;
    }

    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            setDirection(speed);
            move();
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            setDirection(-speed);
            move();
        }
    }
    public void keyReleased(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            setDirection(0);
            move();
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            setDirection(0);
            move();
        }
    }

    public void move(){
        x += xVelocity;
    }
}
