package unibo.citysimulation;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import unibo.citysimulation.utilities.ConstantAndResourceLoader;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import unibo.citysimulation.view.rounded.RoundedButton;

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
        welcomeLabel.setFont(new Font("Roboto", Font.BOLD | Font.ITALIC, 18));
        welcomeLabel.setForeground(Color.BLACK);
        add(welcomeLabel, BorderLayout.CENTER);

        // Create a panel for the buttons
        final JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(0xEAEEEF));

        // Create the START button
        final RoundedButton startButton = createButton("START", new Color(0xA7C4D4));
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
        final RoundedButton exitButton = createButton("EXIT", new Color(0xA7C4D4));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                // Exit the program
                WelcomeScreen.this.dispose();
            }
        });
        buttonPanel.add(exitButton);

        // Create the ABOUT button
        final RoundedButton aboutButton = createButton("ABOUT", new Color(0xA7C4D4));
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                // Show the about dialog
                JOptionPane.showMessageDialog(WelcomeScreen.this,
                        "City-simulation is a project developed by the students of the Object Oriented Programming course\n" +
                        "at the University of Bologna.\n" +
                        "The project aims to simulate a city and its traffic, with the goal of improving the\n" +
                        "quality of life of its citizens.\n" +
                        "The project is open-source and can be found on GitHub");
            }
        });
        buttonPanel.add(aboutButton);

        // Add the button panel to the window
        add(buttonPanel, BorderLayout.SOUTH);

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the window
        setSize(ConstantAndResourceLoader.WELCOME_SCREEN_MIN_WIDTH, ConstantAndResourceLoader.WELCOME_SCREEN_MIN_HEIGHT);

        // Center the window on the screen
        setLocationRelativeTo(null);
    }

    private RoundedButton createButton(String text, Color color) {
        final RoundedButton button = new RoundedButton(text);
        button.setPreferredSize(new Dimension(100, 40));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Roboto", Font.PLAIN, 14));
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
