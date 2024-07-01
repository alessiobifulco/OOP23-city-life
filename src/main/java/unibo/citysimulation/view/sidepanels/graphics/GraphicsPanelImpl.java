package unibo.citysimulation.view.sidepanels.graphics;

import unibo.citysimulation.view.StyledPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;
import java.util.stream.Collectors;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;

/**
 * Panel for displaying graphics.
 */
public final class GraphicsPanelImpl extends StyledPanel implements GraphicsPanel {
    private static final long serialVersionUID = 1L;
    private static final Dimension BUTTON_DIMENSIONS = new Dimension(70, 40);

    private final JButton legendButton;
    private final ChartManager chartManager;
    /**
     * Constructs a GraphicsPanel with the specified background color.
     *
     * @param bgColor The background color of the panel.
     */
    public GraphicsPanelImpl(final Color bgColor) {
        super(bgColor);
        this.chartManager = new ChartManagerImpl();

        this.legendButton = createLegendButton();

        final JPanel bottomPanel = createBottomPanel(bgColor);
        new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(bgColor);
        bottomPanel.add(this.legendButton);

        this.setLayout(new BorderLayout());
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createLegendButton() {
        final JButton button = new JButton("?");
        button.setPreferredSize(BUTTON_DIMENSIONS);
        button.setFont(new Font("Roboto", Font.PLAIN, 14));
        button.setBackground(new Color(0xA7C4D4));
        button.setForeground(Color.BLACK);
        return button;
    }

    private JPanel createBottomPanel(final Color bgColor) {
        final JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(bgColor);
        bottomPanel.add(legendButton);
        return bottomPanel;
    }

    @Override
    public void addLegendButtonActionListener(final ActionListener listener) {
        legendButton.addActionListener(listener);
    }

    @Override
    public void createGraphics(final List<String> names, final List<XYSeriesCollection> datasets,
            final List<Color> colors) {

        final List<XYPlot> plots = chartManager.createCharts(names, datasets).stream()
                .map(JFreeChart::getXYPlot)
                .peek(plot -> plot.setRenderer(chartManager.createRenderer(plot.getSeriesCount(), colors)))
                .collect(Collectors.toList());

        final JPanel chartsPanel = createChartsPanel(plots);
        this.add(chartsPanel, BorderLayout.CENTER);
    }

    private JPanel createChartsPanel(final List<XYPlot> plots) {
        final JPanel chartsPanel = new JPanel();
        chartsPanel.setBackground(Color.WHITE);
        chartsPanel.setLayout(new GridLayout(plots.size(), 1));
        plots.forEach(plot -> chartsPanel.add(new ChartPanel(plot.getChart())));
        return chartsPanel;
    }

    @Override
    public void setPreferredSize(final int width, final int height) {
        this.setPreferredSize(new Dimension(width, height));
    }
}
