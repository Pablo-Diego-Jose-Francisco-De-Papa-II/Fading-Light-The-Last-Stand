package ui;

import javax.swing.*;

public class WaveHUD extends AbstractHUD {

    private final JButton pauseButton;
    private final JButton fastForwardButton;
    private final JButton giveUpButton;

    public WaveHUD() {
        super();

        this.pauseButton = new JButton("||");
        add(this.pauseButton);

        this.fastForwardButton = new JButton(">>");
        add(this.fastForwardButton);

        this.giveUpButton = new JButton("Give-Up");
        add(this.giveUpButton);
    }

    public JButton getPauseButton() {
        return this.pauseButton;
    }

    public JButton getFastForwardButton() {
        return this.fastForwardButton;
    }

    public JButton getGiveUpButton() {
        return this.giveUpButton;
    }
}
