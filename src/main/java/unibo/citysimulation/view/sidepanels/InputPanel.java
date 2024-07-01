package unibo.citysimulation.view.sidepanels;

import unibo.citysimulation.view.StyledPanel;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import unibo.citysimulation.view.rounded.RoundedButton;
import unibo.citysimulation.view.rounded.RoundedSlider;

/**
 * This class represents the input panel.
 */
public class InputPanel extends StyledPanel {
    private static final long serialVersionUID = 1L;

    private final RoundedButton startButton;
    private final RoundedButton stopButton;
    private final RoundedButton showPersonButton;
    private final RoundedSlider peopleSlider;
    private final RoundedSlider capacitySlider;
    private final RoundedSlider businessSlider;
    private static final int BUTTON_PANEL_GRID_Y = 5;
    private static final int FONT_SIZE = 14;
    private static final int MAJOR_TICK_SPACING = 20;
    private static final int MINOR_TICK_SPACING = 5;
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 50;
    private static final int BUTTON_FONT_SIZE = 14;
    private static final Color SLIDER_BG_COLOR = new Color(0xE6E6E6); // Light gray background for sliders (JFreeChart-like)

    /**
     * Constructs an InputPanel with the specified background color.
     *
     * @param bgColor The background color of the panel.
     */
    public InputPanel(final Color bgColor) {
        super(bgColor);
        setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth = 2; // Occupies two columns
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding
        gbc.gridwidth = 1; // Resets to one column for the subsequent elements
        // Create and add slider for the number of people
        peopleSlider = createSlider("Number of People %", 0, 100);
        gbc.gridy = 1;
        gbc.weighty = 0.5;
        add(peopleSlider, gbc);
        // Create and add slider for the number of businesses
        businessSlider = createSlider("Add Businesses", 0, 100); // Initialize businessSlider here
        //set zero the value of the slider
        businessSlider.setValue(0);
        gbc.gridy = 2;
        add(businessSlider, gbc);
        // Create and add slider for the transport line capacities
        capacitySlider = createSlider("Transports' Capacity", 0, 100);
        gbc.gridy = 3;
        add(capacitySlider, gbc);
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0)); // Padding
        startButton = createButton("Start", new Color(0xA7C4D4));
        buttonPanel.add(startButton);
        stopButton = createButton("Stop", new Color(0xA7C4D4));
        stopButton.setEnabled(false);
        buttonPanel.add(stopButton);
        gbc.gridy = BUTTON_PANEL_GRID_Y;
        gbc.gridwidth = 2; // Occupies two columns
        add(buttonPanel, gbc);
        
        // Add the showPersonButton below the start and stop buttons
        showPersonButton = createButton("Show Random Person", new Color(0xA7C4D4));
        gbc.gridy = BUTTON_PANEL_GRID_Y + 1;
        gbc.gridwidth = 2;
        add(showPersonButton, gbc);
    }

    /**
     * Adds an action listener to the start button.
     *
     * @param listener the action listener to be added to the start button
     */
    public void addStartButtonListener(final ActionListener listener) {
        startButton.addActionListener(listener);
    }

    /**
     * Adds an action listener to the stop button.
     *
     * @param listener the action listener to be added to the stop button
     */
    public void addStopButtonListener(final ActionListener listener) {
        stopButton.addActionListener(listener);
    }

    /**
     * Adds an action listener to the show person button.
     *
     * @param listener the action listener to be added to the show person button
     */
    public void addShowPersonButtonListener(final ActionListener listener) {
        showPersonButton.addActionListener(listener);
    }

    /**
     * Returns the value of the people slider.
     *
     * @return the value of the people slider
     */
    public int getPeopleSliderValue() {
        return peopleSlider.getValue();
    }

    /**
     * Returns the value of the capacity slider.
     *
     * @return the value of the capacity slider
     */
    public int getCapacitySliderValue() {
        return capacitySlider.getValue();
    }

    /**
     * Returns the value of the business slider.
     *
     * @return the value of the business slider
     */
    public int getBusinessSliderValue() {
        return businessSlider.getValue();
    }

    /**
     * set the start button enabled or disabled.
     * @param enabled
     */
    public void setStartButtonEnabled(final boolean enabled) {
        startButton.setEnabled(enabled);
    }

    /**
     * set the stop button enabled or disabled.
     * @param enabled
     */
    public void setStopButtonEnabled(final boolean enabled) {
        stopButton.setEnabled(enabled);
    }

    /**
     * set the sliders enabled or disabled.
     * @param enabled
     */
    public void setSlidersEnabled(final boolean enabled) {
        peopleSlider.setEnabled(enabled);
        capacitySlider.setEnabled(enabled);
        businessSlider.setEnabled(enabled);
    }

    private RoundedSlider createSlider(final String title, final int min, final int max) {
        final RoundedSlider slider = new RoundedSlider(min, max);
        final TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleColor(Color.BLACK);
        border.setTitleFont(new Font("Roboto", Font.PLAIN, FONT_SIZE));
        slider.setBorder(border);
        slider.setMajorTickSpacing(MAJOR_TICK_SPACING);
        slider.setMinorTickSpacing(MINOR_TICK_SPACING);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setForeground(Color.BLACK);
        slider.setBackground(SLIDER_BG_COLOR);
        return slider;
    }

    private RoundedButton createButton(final String text, final Color color) {
        final RoundedButton button = new RoundedButton(text);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Roboto", Font.PLAIN, BUTTON_FONT_SIZE));
        button.setFocusPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0x87B2C8));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        return button;
    }
}
