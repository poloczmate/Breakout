import java.awt.*;
import java.awt.event.KeyEvent;

public class Paddle extends Rectangle{
    int speed = 10;
    int xVelocity;
    public Paddle(int x, int y, int width, int height) {
        super(x,y,width,height);
    }

    public void draw(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(x,y,width,height);
    }

    public void keyPressed(KeyEvent e){

    }
    public void keyReleased(KeyEvent e){

    }

    public void move(){
        x += xVelocity;
    }
}
