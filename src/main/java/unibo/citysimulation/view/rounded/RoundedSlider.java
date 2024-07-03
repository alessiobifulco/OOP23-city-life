package unibo.citysimulation.view.rounded;

import javax.swing.JSlider;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * A custom slider component with rounded edges.
 */
public class RoundedSlider extends JSlider {
    private static final long serialVersionUID = 1L;
    private static final int TWENTY = 20;

    /**
     * Constructs a new RoundedSlider with the specified minimum and maximum values.
     *
     * @param min the minimum value of the slider
     * @param max the maximum value of the slider
     */
    public RoundedSlider(final int min, final int max) {
        super(min, max);
        setOpaque(false);
        setBorder(new RoundedBorder(TWENTY));
    }

    /**
     * Paints the component with rounded edges.
     *
     * @param g the Graphics object to paint on
     */
    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), TWENTY, TWENTY);

        super.paintComponent(g);
        g2.dispose();
    }
}
