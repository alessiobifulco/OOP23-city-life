package unibo.citysimulation.view;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

/**
 * Represents a styled panel with a background color.
 */
public class StyledPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int FONT_SIZE = 14;
    private static final int LINE_BORDER_THICKNESS = 2;
    private static final int EMPTY_BORDER_PADDING = 10;
    private final Color bgColor;

    /**
     * Constructs a StyledPanel object with the specified background color.
     * 
     * @param bgColor The background color.
     */
    public StyledPanel(final Color bgColor) {
        this.bgColor = bgColor;
        setLayout(new BorderLayout());
        final Border lineBorder = BorderFactory.createLineBorder(new Color(183, 193, 195), LINE_BORDER_THICKNESS);
        final Border emptyBorder = BorderFactory.createEmptyBorder(EMPTY_BORDER_PADDING, EMPTY_BORDER_PADDING,
                EMPTY_BORDER_PADDING, EMPTY_BORDER_PADDING);
        final Border compoundBorder = BorderFactory.createCompoundBorder(lineBorder, emptyBorder);
        setBorder(compoundBorder);
        setBackground(bgColor);
        setFont(new Font("Roboto", Font.PLAIN, FONT_SIZE));
    }

    /**
     * Returns the background color of the panel.
     * 
     * @return The background color.
     */
    public Color getBgColor() {
        return bgColor;
    }
}
