package unibo.citysimulation.view.rounded;

import java.awt.Graphics;
import javax.swing.border.Border;
import java.awt.Insets;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Component;

/**
 * A custom border implementation that creates a rounded border for Swing
 * components.
 */
public final class RoundedBorder implements Border {
    private final int radius;

    /**
     * Constructs a RoundedBorder with the specified radius.
     *
     * @param radius the radius of the rounded corners
     */
    public RoundedBorder(final int radius) {
        this.radius = radius;
    }

    /**
     * Returns the insets of the border.
     *
     * @param c the component for which this border insets value applies
     * @return the insets of the border
     */
    @Override
    public Insets getBorderInsets(final Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 1, this.radius + 1);
    }

    /**
     * Returns whether or not the border is opaque.
     *
     * @return true if the border is opaque, false otherwise
     */
    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    /**
     * Paints the border for the specified component with the specified position and
     * size.
     *
     * @param c      the component for which this border is being painted
     * @param g      the graphics context in which to paint
     * @param x      the x position of the border
     * @param y      the y position of the border
     * @param width  the width of the border
     * @param height the height of the border
     */
    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width,
            final int height) {
        final Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(c.getBackground());
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2.dispose();
    }
}
