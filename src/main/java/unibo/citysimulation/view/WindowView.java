package unibo.citysimulation.view;

import unibo.citysimulation.utilities.ConstantAndResourceLoader;
import unibo.citysimulation.view.map.MapPanel;
import unibo.citysimulation.view.sidepanels.ClockPanel;
import unibo.citysimulation.view.sidepanels.GraphicsPanel;
import unibo.citysimulation.view.sidepanels.InfoPanel;
import unibo.citysimulation.view.sidepanels.InputPanel;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;

/**
 * Represents the main window of the application.
 */
public class WindowView extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final List<Color> COLOR_LIST = List.of(
        new Color(50, 50, 50),
        new Color(159, 226, 191),
        new Color(128, 0, 32),
        new Color(0, 123, 167)
    );
    private static final int PANEL_DIVISOR = 4;
    private static final double WEIGHT_INPUT_PANEL = 0.625;
    private static final double WEIGHT_INFO_PANEL = 0.375;
    private static final double WEIGHT_CLOCK_PANEL = 0.1;
    private static final double WEIGHT_GRAPHICS_PANEL = 0.9;

    private final MapPanel mapPanel;
    private final InfoPanel infoPanel;
    private final ClockPanel clockPanel;
    private final InputPanel inputPanel;
    private final GraphicsPanel graphicsPanel;

    /**
     * Constructs a WindowView with the specified window width and height.
     *
     * @param width the width of the window.
     * @param height the height of the window.
     */

    public WindowView(final int width, final int height) {
        setMinimumSize(new Dimension(ConstantAndResourceLoader.SCREEN_MINIMUM_WIDTH_PIXEL,
                ConstantAndResourceLoader.SCREEN_MINIMUM_HEIGHT_PIXEL));

        setTitle(ConstantAndResourceLoader.APPLICATION_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setFocusable(true);

        setLayout(new BorderLayout());

        mapPanel = new MapPanel(Color.WHITE);
        inputPanel = new InputPanel(COLOR_LIST.get(0));
        infoPanel = new InfoPanel(COLOR_LIST.get(1));
        clockPanel = new ClockPanel(COLOR_LIST.get(2));
        graphicsPanel = new GraphicsPanel(COLOR_LIST.get(3));

        createComponents(width, height);

        setVisible(true);

        validate();
        pack();
    }

    /**
     * Adds a component resize listener.
     *
     * @param adapter The component adapter to add.
     */
    public void addResizeListener(final ComponentAdapter adapter) {
        addComponentListener(adapter);
    }

    /**
     * Updates the size of the panels based on the window size.
     *
     * @param width the new width of the window.
     * @param height the new height of the window.
     */
    public void updateFrame(final int width, final int height) {

        setSize(new Dimension(width, height));

        inputPanel.setPreferredSize(new Dimension(width / 4, height));
        infoPanel.setPreferredSize(new Dimension(width / 4, height));
        clockPanel.setPreferredSize(new Dimension(width / 4, height));
        graphicsPanel.setPreferredSize(new Dimension(width / 4, height));
        mapPanel.setPreferredSize(new Dimension(width / 2, height));

        revalidate();
        repaint();
    }

    /**
     * Creates the components of the window.
     *
     * @param width the width of the window.
     * @param height the height of the window.
     */
    private void createComponents(final int width, final int height) {
        add(mapPanel, BorderLayout.CENTER);

        createSidePanels(width, height);
    }

    /**
     * Creates the side panels of the window.
     *
     * @param width the width of the window.
     * @param height the height of the window.
     */
    private void createSidePanels(final int width, final int height) {
        final int sidePanelWidth = width / PANEL_DIVISOR;
        final int sidePanelsHeight = height;

        final JPanel leftPanel = new JPanel(new GridBagLayout());
        final JPanel rightPanel = new JPanel(new GridBagLayout());

        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;

        constraints.gridy = 0;
        constraints.weighty = WEIGHT_INPUT_PANEL;
        inputPanel.setPreferredSize(new Dimension(sidePanelWidth, (int) (sidePanelsHeight * WEIGHT_INPUT_PANEL)));
        leftPanel.add(inputPanel, constraints);

        constraints.gridy = 1;
        constraints.weighty = WEIGHT_INFO_PANEL;
        infoPanel.setPreferredSize(new Dimension(sidePanelWidth, (int) (sidePanelsHeight * WEIGHT_INFO_PANEL)));
        leftPanel.add(infoPanel, constraints);

        constraints.gridy = 0;
        constraints.weighty = WEIGHT_CLOCK_PANEL;
        clockPanel.setPreferredSize(new Dimension(sidePanelWidth, (int) (sidePanelsHeight * WEIGHT_CLOCK_PANEL)));
        rightPanel.add(clockPanel, constraints);

        constraints.gridy = 1;
        constraints.weighty = WEIGHT_GRAPHICS_PANEL;
        graphicsPanel.setPreferredSize(new Dimension(sidePanelWidth, (int) (sidePanelsHeight * WEIGHT_GRAPHICS_PANEL))); 
        rightPanel.add(graphicsPanel, constraints);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    /**
     * Retrieves the info panel.
     *
     * @return The info panel.
     */
    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    /**
     * Retrieves the clock panel.
     *
     * @return The clock panel.
     */
    public ClockPanel getClockPanel() {
        return clockPanel;
    }

    /**
     * Retrieves the input panel.
     *
     * @return The input panel.
     */
    public InputPanel getInputPanel() {
        return inputPanel;
    }

    /**
     * Retrieves the graphics panel.
     *
     * @return The graphics panel.
     */
    public GraphicsPanel getGraphicsPanel() {
        return graphicsPanel;
    }

    /**
     * Retrieves the map panel.
     *
     * @return The map panel.
     */
    public MapPanel getMapPanel() {
        return mapPanel;
    }
}
