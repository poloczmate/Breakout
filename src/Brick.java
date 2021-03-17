import java.awt.*;

public class Brick extends Rectangle {
    Color color;
    public Brick(int x, int y, int width, int height, Color color) {
        super(x,y,width,height);
        this.color = color;
    }

    public void draw(Graphics g){
        g.setColor(color);
        g.fillRect(x,y,width,height);
    }
}
