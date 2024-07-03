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

    private static final int FONT_SIZE = 18;
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 40;
    private static final int BUTTON_FONT_SIZE = 14;
    private static final Color BUTTON_COLOR = new Color(0xA7C4D4);
    private static final Color BUTTON_HOVER_COLOR = new Color(0x87B2C8);
    private static final Color BACKGROUND_COLOR = new Color(0xEAEEEF);
    private static final Color FOREGROUND_COLOR = Color.BLACK;

    /**
     * Constructs a WelcomeScreen object.
     */
    public WelcomeScreen() {
        setTitle("Welcome to city-simulation");

        setLayout(new BorderLayout());

        setMinimumSize(new Dimension(ConstantAndResourceLoader.WELCOME_SCREEN_MIN_WIDTH,
                ConstantAndResourceLoader.WELCOME_SCREEN_MIN_HEIGHT));
        final JLabel welcomeLabel = new JLabel("Welcome to city-simulation", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Roboto", Font.BOLD | Font.ITALIC, FONT_SIZE));
        welcomeLabel.setForeground(FOREGROUND_COLOR);
        add(welcomeLabel, BorderLayout.CENTER);
        final JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(BACKGROUND_COLOR);
        final RoundedButton startButton = createButton("START", BUTTON_COLOR);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                dispose();
                final SimulationLauncher simulationLauncher = new SimulationLauncher();
                simulationLauncher.start();
            }
        });
        buttonPanel.add(startButton);
        final RoundedButton exitButton = createButton("EXIT", BUTTON_COLOR);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                WelcomeScreen.this.dispose();
            }
        });
        buttonPanel.add(exitButton);
        final RoundedButton aboutButton = createButton("ABOUT", BUTTON_COLOR);
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                JOptionPane.showMessageDialog(WelcomeScreen.this,
                        "City-simulation is a project developed by the students of the Object Oriented Programming course\n"
                                + "at the University of Bologna.\n"
                                + "The project aims to simulate a city and its traffic, with the goal of improving the\n"
                                + "quality of life of its citizens.\n"
                                + "The project is open-source and can be found on GitHub");
            }
        });
        buttonPanel.add(aboutButton);
        add(buttonPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(ConstantAndResourceLoader.WELCOME_SCREEN_MIN_WIDTH,
                ConstantAndResourceLoader.WELCOME_SCREEN_MIN_HEIGHT);

        setLocationRelativeTo(null);
    }

    private RoundedButton createButton(final String text, final Color color) {
        final RoundedButton button = new RoundedButton(text);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setBackground(color);
        button.setForeground(FOREGROUND_COLOR);
        button.setFont(new Font("Roboto", Font.PLAIN, BUTTON_FONT_SIZE));
        button.setFocusPainted(false);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(final java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }

            @Override
            public void mouseExited(final java.awt.event.MouseEvent evt) {
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
        final WelcomeScreen welcomeScreen = new WelcomeScreen();
        welcomeScreen.setVisible(true);
    }
}
