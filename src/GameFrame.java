import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    GameWindow gw;
    public GameFrame() {
        gw = new GameWindow();
        this.add(gw);
        this.setBackground(Color.BLACK);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Breakout Game");
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
