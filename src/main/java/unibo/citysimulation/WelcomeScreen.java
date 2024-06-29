package unibo.citysimulation;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import unibo.citysimulation.utilities.ConstantAndResourceLoader;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents the welcome screen of the application.
 */
public class WelcomeScreen extends JFrame {
    private static final long serialVersionUID = 1L;
    /**
     * Constructs a WelcomeScreen object.
     */
    public WelcomeScreen() {
    // Set the title of the window
    setTitle("Welcome to city-simulation");

    // Set the layout manager to BorderLayout
    setLayout(new BorderLayout());

    setMinimumSize(new Dimension(ConstantAndResourceLoader.WELCOME_SCREEN_MIN_WIDTH,
            ConstantAndResourceLoader.WELCOME_SCREEN_MIN_HEIGHT));

    // Create a JLabel with the welcome message
    final JLabel welcomeLabel = new JLabel("Welcome to city-simulation", SwingConstants.CENTER);
    add(welcomeLabel, BorderLayout.CENTER);

    // Create a panel for the buttons
    final JPanel buttonPanel = new JPanel(new FlowLayout());

    // Create the START button
    final JButton startButton = new JButton("START");
    startButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
            // Close the welcome screen
            dispose();

            // Start the simulation
            final SimulationLauncher simulationLauncher = new SimulationLauncher();
            simulationLauncher.start();
        }
    });
    buttonPanel.add(startButton);

    // Create the EXIT button
    final JButton exitButton = new JButton("EXIT");
    exitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
        // Exit the program
        WelcomeScreen.this.dispose();
        }
    });
    buttonPanel.add(exitButton);

    // Add the button panel to the window
    add(buttonPanel, BorderLayout.SOUTH);

    final JButton aboutButton = new JButton("ABOUT");
    aboutButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
            // Show the about dialog
            JOptionPane.showMessageDialog(WelcomeScreen.this,
                    "City-simulation is a project developed by the students of the Object Oriented Programing course \"\r\n" + //
                                                "                            + \"at the University of Bologna.\\n" + //
                                                "\"\r\n" + //
                                                "                            + \"The project aims to simulate a city and its traffic, with the goal of improving the \"\r\n" + //
                                                "                            + \"quality of life of its citizens.\\n" + //
                                                "\"\r\n" + //
                                                "                            + \"The project is open-source and can be found on GitHub");
        }
    });
    buttonPanel.add(aboutButton);
    // Set the default close operation
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Set the size of the window
    setSize(ConstantAndResourceLoader.WELCOME_SCREEN_MIN_WIDTH, ConstantAndResourceLoader.WELCOME_SCREEN_MIN_HEIGHT);

    // Center the window on the screen
    setLocationRelativeTo(null);
    }

    /**
     * The main method of the application.
     *
     * @param args The command-line arguments.
     */
    public static void main(final String[] args) {

    // Create and show the welcome screen
    final WelcomeScreen welcomeScreen = new WelcomeScreen();
    welcomeScreen.setVisible(true);
    }
}
