import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        this.setTitle("Snake");
        this.add(new GamePanel());
        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}