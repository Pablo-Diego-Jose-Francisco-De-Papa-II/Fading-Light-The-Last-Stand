import buildings.Building;
import game.PlayingArea;
import game.WaveManager;
import slimes.Slime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameGUI extends JFrame {
    private PlayingArea playingArea;
    private WaveManager waveManager;
    private JPanel gamePanel;
    private JPanel shopPanel;
    private JButton watchTowerButton;
    private JButton startWaveButton;

    public GameGUI() {
        // Initialize the playing area and wave manager
        waveManager = new WaveManager(playingArea);

        // Initialize the game frame
        setTitle("Fading Light: The Last Stand");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Game Panel for the map
        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGame(g);
            }
        };
        gamePanel.setPreferredSize(new Dimension(600, 400));
        add(gamePanel, BorderLayout.CENTER);

        // Shop Panel for building buttons
        shopPanel = new JPanel();
        shopPanel.setLayout(new GridLayout(0, 1));
        watchTowerButton = new JButton("Place Watch Tower");
        watchTowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeBuilding("Watch Tower");
            }
        });
        shopPanel.add(watchTowerButton);

        startWaveButton = new JButton("Start Wave");
        startWaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                waveManager.startNextWave();
                updateZombies();
            }
        });
        shopPanel.add(startWaveButton);

        add(shopPanel, BorderLayout.EAST);
    }

    private void drawGame(Graphics g) {
        // Draw the game map (tiles, buildings, etc.)
        // You can replace this with your actual game drawing logic.
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, gamePanel.getWidth(), gamePanel.getHeight());

        // Draw zombies
        List<Slime> slimes = waveManager.getZombies();
        for (Slime slime : slimes) {
            g.setColor(Color.RED);
            g.fillRect(slime.getX() * 20, slime.getY() * 20, 20, 20); // Example size for zombies
        }
    }

    private void placeBuilding(String buildingType) {
        // You can implement logic to place the building on the map at a specific location.
        Building building = Building.createBuilding(buildingType, playingArea, 5, 5); // Example placing at (5, 5)
        System.out.println("Placed " + buildingType + " at (5, 5)");
    }

    private void updateZombies() {
        // Update the game state: move zombies, check for collisions, etc.
        for (Slime slime : waveManager.getZombies()) {
            slime.move();
        }
        gamePanel.repaint();
    }

}
