package Main;

import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow(GamePanel gamePanel){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,400);
        this.add(gamePanel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

}
