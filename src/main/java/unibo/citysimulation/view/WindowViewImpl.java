package unibo.citysimulation.view;

import unibo.citysimulation.utilities.ConstantAndResourceLoader;
import unibo.citysimulation.view.map.MapPanel;
import unibo.citysimulation.view.map.MapPanelImpl;
import unibo.citysimulation.view.sidepanels.InfoPanel;
import unibo.citysimulation.view.sidepanels.InputPanel;
import unibo.citysimulation.view.sidepanels.clock.ClockPanel;
import unibo.citysimulation.view.sidepanels.clock.ClockPanelImpl;
import unibo.citysimulation.view.sidepanels.graphics.GraphicsPanel;
import unibo.citysimulation.view.sidepanels.graphics.GraphicsPanelImpl;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;

/**
 * Class that implments the Windowview interface.
 * This manages all the Panels displayed in this frame and their sizes.
 */
@SuppressFBWarnings(value = "EI", justification = """
    Considering the basic structure of the application, we choose to 
    pass the mutable panels as parameters, because we need to keep them always updated. In every case, we pass
    interfaces of the panels.""")
public final class WindowViewImpl extends JFrame implements WindowView {
    private static final long serialVersionUID = 1L;
    private static final List<Color> COLOR_LIST = List.of(
        new Color(50, 50, 50),
        new Color(159, 226, 191),
        new Color(128, 0, 32),
        new Color(0, 123, 167)
    );
    private static final int PANEL_DIVISOR = 4;
    private static final double WEIGHT_INPUT_PANEL = 0.525;
    private static final double WEIGHT_INFO_PANEL = 0.475;
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

    public WindowViewImpl(final int width, final int height) {
        setMinimumSize(new Dimension(ConstantAndResourceLoader.SCREEN_MINIMUM_WIDTH_PIXEL,
                ConstantAndResourceLoader.SCREEN_MINIMUM_HEIGHT_PIXEL));

        setTitle(ConstantAndResourceLoader.APPLICATION_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setFocusable(true);

        setLayout(new BorderLayout());

        mapPanel = new MapPanelImpl(Color.WHITE);
        inputPanel = new InputPanel(COLOR_LIST.get(0));
        infoPanel = new InfoPanel(COLOR_LIST.get(1));
        clockPanel = new ClockPanelImpl(COLOR_LIST.get(2));
        graphicsPanel = new GraphicsPanelImpl(COLOR_LIST.get(3));

        createComponents(width, height);

        setVisible(true);
    }


    @Override
    public void addResizeListener(final ComponentAdapter adapter) {
        addComponentListener(adapter);
    }

    @Override
    public void updateFrame(final int width, final int height) {

        setSize(new Dimension(width, height));

        inputPanel.setPreferredSize(new Dimension(width / 4, height));
        infoPanel.setPreferredSize(new Dimension(width / 4, height));
        clockPanel.setPreferredSize(width / 4, height);
        graphicsPanel.setPreferredSize(width / 4, height);
        revalidate();
        repaint();
    }

    private void createComponents(final int width, final int height) {
        add((JPanel) mapPanel, BorderLayout.CENTER);

        createSidePanels(width, height);
    }

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
        clockPanel.setPreferredSize(width / 4, height);
        rightPanel.add((JPanel) clockPanel, constraints);

        constraints.gridy = 1;
        constraints.weighty = WEIGHT_GRAPHICS_PANEL;
        graphicsPanel.setPreferredSize(sidePanelWidth, (int) (sidePanelsHeight * WEIGHT_GRAPHICS_PANEL)); 
        rightPanel.add((JPanel) graphicsPanel, constraints);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    @Override
    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    @Override
    public ClockPanel getClockPanel() {
        return clockPanel;
    }

    @Override
    public InputPanel getInputPanel() {
        return inputPanel;
    }

    @Override
    public GraphicsPanel getGraphicsPanel() {
        return graphicsPanel;
    }

    @Override
    public MapPanel getMapPanel() {
        return mapPanel;
    }

    @Override
    public void showPersonInfo(String info) {
        JOptionPane.showMessageDialog(null, info, "Person Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
