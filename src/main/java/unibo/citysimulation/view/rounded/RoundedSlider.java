package unibo.citysimulation.view.rounded;

import javax.swing.JSlider;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class RoundedSlider extends JSlider {
    private static final long serialVersionUID = 1L;
    private static final int ARC_WIDTH = 20;
    private static final int ARC_HEIGHT = 20;

    public RoundedSlider(int min, int max) {
        super(min, max);
        setOpaque(false);
        setBorder(new RoundedBorder(20)); // Apply rounded border
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);

        // Paint the rest of the slider
        super.paintComponent(g);
        g2.dispose();
    }
}