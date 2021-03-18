import java.awt.*;

public class Ball extends Rectangle{
    int speed = 2;
    int xVelocity;
    int yVelocity;

    public Ball(int x, int y, int width) {
        super(x,y,width,width);
    }

    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillOval(x,y,width,height);
    }
}
