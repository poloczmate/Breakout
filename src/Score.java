import java.awt.*;

public class Score {
    public static int GAME_WIDTH;
    public static int GAME_HEIGHT;
    int crashedBricks = 0;

    public Score(int width, int height) {
        Score.GAME_WIDTH = width;
        Score.GAME_HEIGHT = height;
    }

    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.PLAIN,15));
        g.drawString("Number of crashed bricks:"+String.valueOf(crashedBricks), GAME_WIDTH/2 - 100, GAME_HEIGHT-10);
    }
}
