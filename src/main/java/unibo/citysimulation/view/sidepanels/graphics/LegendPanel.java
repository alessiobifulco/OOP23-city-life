package unibo.citysimulation.view.sidepanels.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
/**
 * This class represents a legend panel for the city simulation.
 * It extends JFrame to create a separate window for the legend.
 */
public class LegendPanel extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int WINDOW_SIZE = 300;
    private static final int FONT_SIZE = 18;

     /**
     * Constructor for the LegendPanel class.
     * @param colors The list of colors to be used in the legend.
     * @param linesName The list of names for each line in the legend.
     */
    public LegendPanel(final List<Color> colors, final List<String> linesName) {
        this.setTitle("Legend");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WINDOW_SIZE, WINDOW_SIZE);

        final JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));

        final JLabel title = new JLabel("Graph Legend");
        title.setFont(new Font("Serif", Font.BOLD, FONT_SIZE));
        legendPanel.add(title);
        legendPanel.add(Box.createVerticalStrut(10)); // Spacing
        final JLabel divisionTitle = new JLabel("People State:");
        divisionTitle.setFont(new Font("Serif", Font.BOLD, FONT_SIZE));
        legendPanel.add(divisionTitle);

        legendPanel.add(createLegendItem("WORKING", Color.RED));
        legendPanel.add(createLegendItem("AT_HOME", Color.BLUE));
        legendPanel.add(createLegendItem("MOVING", Color.YELLOW));

        legendPanel.add(Box.createVerticalStrut(10)); // Spacing
        final JLabel transportTitle = new JLabel("Transport Congestion:");
        transportTitle.setFont(new Font("Serif", Font.BOLD, FONT_SIZE));
        legendPanel.add(transportTitle);

        for (int i = 0; i < linesName.size(); i++) {
            final String lineName = linesName.get(i);
            final Color color = colors.get(i % colors.size());
            legendPanel.add(createLegendItem(lineName, color));
        }

        legendPanel.add(Box.createVerticalStrut(10)); // Spacing
        final JLabel businessOccupationTitle = new JLabel("Business Occupation:");
        businessOccupationTitle.setFont(new Font("Serif", Font.BOLD, FONT_SIZE));
        legendPanel.add(businessOccupationTitle);
        legendPanel.add(createLegendItem("Business Occupation", Color.BLUE));

        final JScrollPane scrollPane = new JScrollPane(legendPanel);
        add(scrollPane);
        setVisible(true);
    }
     /**
     * Creates a JPanel representing a single item in the legend.
     * @param text The text for the legend item.
     * @param color The color for the legend item.
     * @return A JPanel representing the legend item.
     */
    private JPanel createLegendItem(final String text, final Color color) {
        final JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        final JLabel colorLabel = new JLabel();
        colorLabel.setOpaque(true);
        colorLabel.setBackground(color);
        colorLabel.setPreferredSize(new Dimension(10, 10));
        itemPanel.add(colorLabel);
        itemPanel.add(new JLabel(text));
        return itemPanel;
    }
}

