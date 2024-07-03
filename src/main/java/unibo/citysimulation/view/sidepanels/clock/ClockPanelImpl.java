package unibo.citysimulation.view.sidepanels.clock;

import unibo.citysimulation.utilities.ConstantAndResourceLoader;
import unibo.citysimulation.view.StyledPanel;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import unibo.citysimulation.view.rounded.RoundedButton;

/**
 * Panel for displaying the clock and controlling simulation speed.
 */
public class ClockPanelImpl extends StyledPanel implements ClockPanel {
    private static final long serialVersionUID = 1L;
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 35;
    private static final int FONT_SIZE = 14;
    private static final Color BUTTON_BG_COLOR = new Color(0xA7C4D4);
    private static final Color BUTTON_HOVER_BG_COLOR = new Color(0x87B2C8);
    private static final Color BUTTON_FG_COLOR = Color.BLACK;
    private static final Color LABEL_FG_COLOR = Color.BLACK;

    private final JLabel timeDay = new JLabel("Day: 1", SwingConstants.CENTER);
    private final JLabel timeHour = new JLabel("Hour: 00:00", SwingConstants.CENTER);
    private final RoundedButton speedButton;
    private final RoundedButton pauseButton;
    private final List<Integer> speeds = ConstantAndResourceLoader.SPEEDS;
    private int currentSpeedIndex;

    /**
     * Constructs a ClockPanel with the specified background color.
     *
     * @param bgColor The background color of the panel.
     */
    public ClockPanelImpl(final Color bgColor) {
        super(bgColor);
        speedButton = createButton("1x");
        pauseButton = createButton("Pause");

        final JPanel timePanel = new JPanel(new GridLayout(2, 1));
        timePanel.setBackground(bgColor);
        timeDay.setFont(new Font("Roboto", Font.BOLD, ConstantAndResourceLoader.CLOCK_PANEL_FONT_SIZE));
        timeHour.setFont(new Font("Roboto", Font.BOLD, ConstantAndResourceLoader.CLOCK_PANEL_FONT_SIZE));
        timeDay.setForeground(LABEL_FG_COLOR);
        timeHour.setForeground(LABEL_FG_COLOR);
        timePanel.add(timeDay);
        timePanel.add(timeHour);

        setLayout(new BorderLayout());
        add(speedButton, BorderLayout.WEST);
        add(timePanel, BorderLayout.CENTER);
        add(pauseButton, BorderLayout.EAST);
    }

    private RoundedButton createButton(final String text) {
        final RoundedButton button = new RoundedButton(text);
        button.setForeground(BUTTON_FG_COLOR);
        button.setBackground(BUTTON_BG_COLOR);
        button.setFont(new Font("Roboto", Font.PLAIN, FONT_SIZE));
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setFocusPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_BG_COLOR);
            }

            @Override
            public void mouseExited(final MouseEvent evt) {
                button.setBackground(BUTTON_BG_COLOR);
            }
        });
        return button;
    }

    /**
     * Sets the text of the clock label.
     *
     * @param dayText  The text for the day label.
     * @param hourText The text for the hour label.
     */
    @Override
    public void setClockText(final String dayText, final String hourText) {
        timeDay.setText("Day: " + dayText);
        timeHour.setText("Hour: " + hourText);
    }

    /**
     * Updates the text of the pause button based on the simulation state.
     *
     * @param isPaused Boolean indicating if simulation is paused.
     */
    @Override
    public void updatePauseButton(final boolean isPaused) {
        pauseButton.setText(isPaused ? "Resume" : "Pause");
    }

    /**
     * Changes the simulation speed and updates the speed button text.
     *
     * @return The new simulation speed.
     */
    @Override
    public int changeSpeed() {
        currentSpeedIndex = (currentSpeedIndex + 1) % speeds.size();
        final int newSpeed = speeds.get(currentSpeedIndex);
        speedButton.setText(newSpeed + "x");
        return newSpeed;
    }

    /**
     * Sets the preferred size of the clock panel.
     *
     * @param width  The width to set.
     * @param height The height to set.
     */
    @Override
    public void setPreferredSize(final int width, final int height) {
        this.setPreferredSize(new Dimension(width, height));
    }

    /**
     * Adds an action listener to the pause button.
     */
    @Override
    public void addPauseButtonActionListener(final ActionListener actionListener) {
        pauseButton.addActionListener(actionListener);
    }

    /**
     * Adds an action listener to the speed button.
     */
    @Override
    public void addSpeedButtonActionListener(final ActionListener actionListener) {
        speedButton.addActionListener(actionListener);
    }

    /**
     * Sets the pause button enabled state.
     *
     * @param enabled Boolean indicating if the pause button is enabled.
     */
    @Override
    public void setPauseButtonEnabled(final boolean enabled) {
        pauseButton.setEnabled(enabled);
    }
}
