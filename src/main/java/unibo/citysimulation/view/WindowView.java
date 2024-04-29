package unibo.citysimulation.view;

import unibo.citysimulation.controller.MapController;
import unibo.citysimulation.model.MapModel;
import unibo.citysimulation.model.WindowModel;
import unibo.citysimulation.utilities.ConstantAndResourceLoader;
import unibo.citysimulation.view.sidePanels.ClockPanel;
import unibo.citysimulation.view.sidePanels.GraphicsPanel;
import unibo.citysimulation.view.sidePanels.InfoPanel;
import unibo.citysimulation.view.sidePanels.InputPanel;
import unibo.citysimulation.view.map.MapPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;

/**
 * Represents the main window of the application.
 */
public class WindowView extends JFrame {
    private MapModel mapModel;
    private MapController mapController;
    private WindowModel windowModel;

    private InfoPanel infoPanel = new InfoPanel(Color.GREEN);
    private ClockPanel clockPanel = new ClockPanel(Color.RED);
    private InputPanel inputPanel = new InputPanel(Color.BLUE);
    private GraphicsPanel graphicsPanel = new GraphicsPanel(Color.YELLOW);

    /**
     * Constructs a WindowView with the specified window model and map model.
     *
     * @param windowModel The model representing the main window.
     * @param mapModel    The model representing the map.
     */
    public WindowView(WindowModel windowModel, MapModel mapModel) {
        this.windowModel = windowModel;
        this.mapModel = mapModel;
        this.mapController = new MapController(mapModel, infoPanel);

        setTitle(ConstantAndResourceLoader.APPLICATION_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setFocusable(true);
        configureLayout();
        createComponents();
        setVisible(true);
    }

    /**
     * Adds a component resize listener.
     *
     * @param adapter The component adapter to add.
     */
    public void addResizeListener(ComponentAdapter adapter) {
        addComponentListener(adapter);
    }

    /**
     * Updates the size of the panels based on the window size.
     */
    public void updatePanelSize() {
        int panelWidth = windowModel.getWidth() / 4;

        inputPanel.setPreferredSize(new Dimension(panelWidth, windowModel.getHeight()));
        infoPanel.setPreferredSize(new Dimension(panelWidth, windowModel.getHeight()));
        clockPanel.setPreferredSize(new Dimension(panelWidth, windowModel.getHeight()));
        graphicsPanel.setPreferredSize(new Dimension(panelWidth, windowModel.getHeight()));

        revalidate();
        repaint();
    }

    /**
     * Configures the layout of the window.
     */
    private void configureLayout() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(windowModel.getWidth(), windowModel.getHeight()));
        pack();
    }

    /**
     * Creates the components of the window.
     */
    private void createComponents() {

        // Add the map panel to the center
        MapPanel mapPanel = new MapPanel(mapModel, infoPanel);
        add(mapPanel, BorderLayout.CENTER);

        // Add the side panels
        createSidePanels();
    }

    /**
     * Creates the side panels of the window.
     */
    private void createSidePanels() {

        int sidePanelWidth = getSize().width / 4;
        int sidePanelsHeight = getSize().height;

        JPanel leftPanel = new JPanel(new GridBagLayout());
        JPanel rightPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;


        constraints.gridy = 0;
        constraints.weighty = 0.75;
        inputPanel.setPreferredSize(new Dimension(sidePanelWidth, sidePanelsHeight / 4 * 3));
        leftPanel.add(inputPanel, constraints);

        constraints.gridy = 1;
        constraints.weighty = 0.25;
        infoPanel.setPreferredSize(new Dimension(sidePanelWidth, sidePanelsHeight / 4));
        leftPanel.add(infoPanel, constraints);


        constraints.gridy = 0;
        constraints.weighty = 0.125;
        clockPanel.setPreferredSize(new Dimension(sidePanelWidth, sidePanelsHeight / 8));
        rightPanel.add(clockPanel, constraints);

        constraints.gridy = 1;
        constraints.weighty = 0.875;
        graphicsPanel.setPreferredSize(new Dimension(sidePanelWidth, sidePanelsHeight / 8 * 7));
        rightPanel.add(graphicsPanel, constraints);


        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

}