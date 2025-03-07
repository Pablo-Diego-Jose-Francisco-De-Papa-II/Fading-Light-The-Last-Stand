import javax.swing.*;
import java.awt.*;

public class Menu {
    public void setUpGUI() {
        JFrame frame = new JFrame("Fading Light: The Last Stand");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(860, 540);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);

        Image image = new  ImageIcon("C:/Users/UX3405/Pictures/Saved Pictures/idwin.png").getImage();
        frame.setIconImage(image);

        // new game, load, bio, quit
        JButton newGameButton = createButton("New game", 75);
        frame.add(newGameButton);

        JButton loadGameButton = createButton("Load game", 175);
        frame.add(loadGameButton);

        JButton survivalGuideButton = createButton("Survival guide", 275);
        frame.add(survivalGuideButton);

        JButton quitButton = createButton("Quit to desktop", 375);
        frame.add(quitButton);

        frame.setVisible(true);
    }

    private static JButton createButton(String text, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBackground(Color.LIGHT_GRAY);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBounds(650, y, 134, 50);
        return button;
    }

    public static void main(String[] args) {
        new Menu().setUpGUI();
    }
}
