import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle{
    int speed = 4;
    int xVelocity;
    int yVelocity;
    Random random = new Random();

    public Ball(int x, int y, int width) {
        super(x,y,width,width);
        if (random.nextBoolean()){
            setXDirection(1 * speed);
        }else{
            setXDirection(-1 * speed);
        }
        setYDirrection(-1);
    }

    public void setXDirection(int xdir){
        xVelocity = xdir;
    }
    public void setYDirrection(int ydir){
        yVelocity = ydir;
    }

    public void move(){
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillOval(x,y,width,height);
    }
}
