import game.Game;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;

public class Menu {
    public void setUpGUI() {
        JFrame frame = new JFrame("Fading Light: The Last Stand");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(860, 540);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);

        // Icon image
        Image iconImage = new  ImageIcon("resources/FadingLight-icon.png").getImage();
        frame.setIconImage(iconImage);

        // Layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(860, 540));

        // Background image
        ImageIcon backgroundImage = new ImageIcon("resources/FL_Background_img.png");
        Image background = backgroundImage.getImage().getScaledInstance(860, 540, Image.SCALE_SMOOTH);
        backgroundImage = new ImageIcon(background);
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 860, 540);
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        // Title name
        JLabel titleLabel = new JLabel("Fading Light");
        titleLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 40));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(85, 75, 500, 50);
        layeredPane.add(titleLabel, JLayeredPane.PALETTE_LAYER);

        JLabel titleLabel2 = new JLabel("The Last Stand");
        titleLabel2.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
        titleLabel2.setForeground(Color.WHITE);
        titleLabel2.setBounds(225, 125, 500, 30);
        layeredPane.add(titleLabel2, JLayeredPane.PALETTE_LAYER);

        // new game, load, bio, quit buttons
        JButton newGameButton = createButton("New game", 75);
        layeredPane.add(newGameButton, JLayeredPane.MODAL_LAYER);

        JButton loadGameButton = createButton("Load game", 175);
        layeredPane.add(loadGameButton, JLayeredPane.MODAL_LAYER);

        JButton survivalGuideButton = createButton("Survival guide", 275);
        layeredPane.add(survivalGuideButton, JLayeredPane.MODAL_LAYER);

        JButton quitButton = createButton("Quit to desktop", 375);
        layeredPane.add(quitButton, JLayeredPane.MODAL_LAYER);

        frame.setContentPane(layeredPane);
        frame.pack();
        frame.setVisible(true);

        newGameButton.addActionListener(e -> {
            frame.dispose();
            new Game();
        });

        loadGameButton.addActionListener(e -> {
           // todo
        });

        survivalGuideButton.addActionListener(e -> {
            // todo
        });

        quitButton.addActionListener(e -> System.exit(0));
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
}
