package unibo.citysimulation.view.rounded;

import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * A custom JButton with rounded corners.
 */
public final class RoundedButton extends JButton {
    private static final long serialVersionUID = 1L;
    private static final int TWENTY = 20;

    /**
     * Constructs a new RoundedButton with the specified text.
     *
     * @param text the text to be displayed on the button
     */
    public RoundedButton(final String text) {
        super(text);
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorder(new RoundedBorder(TWENTY));
    }

    /**
     * Paints the component with rounded corners.
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
