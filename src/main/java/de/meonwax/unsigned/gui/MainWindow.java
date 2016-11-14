package de.meonwax.unsigned.gui;

import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.meonwax.unsigned.Machine;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private Display display;

    private Machine machine;

    public static void main(String[] args) throws IOException {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }

    public MainWindow() {
        initGui();
        machine = new Machine(display);
    }

    public void initGui() {
        setTitle("Unsigned 6502");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        createLoadPanel();
        createCpuPanel();
        createDisplay();
    }

    private void createLoadPanel() {
        JPanel panel = new JPanel();

        JLabel statusLabel = new JLabel();
        panel.add(statusLabel);

        JButton runButton = new JButton("Run");
        runButton.addActionListener(e -> {
            machine.setStart(49152);// start at address $c000
            machine.run();
        });
        runButton.setEnabled(false);
        panel.add(runButton);

        JButton stepButton = new JButton("Step");
        stepButton.addActionListener(e -> {
            machine.step();
        }); // start at address $c000
        stepButton.setEnabled(false);
        panel.add(stepButton);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            machine.reset();
            runButton.setEnabled(false);
            stepButton.setEnabled(false);
        });
        panel.add(resetButton);

        JButton openButton = new JButton("Open file");
        openButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (machine.loadFile(filename, 49152)) {
                    statusLabel.setText("Loaded: " + chooser.getSelectedFile().getName());
                    runButton.setEnabled(true);
                    stepButton.setEnabled(true);
                } else {
                    runButton.setEnabled(false);
                    stepButton.setEnabled(false);
                }
            }
        });
        panel.add(openButton);

        add(panel);
    }

    private void createCpuPanel() {
        JPanel panel = new JPanel();

        JLabel acLabel = new JLabel("AC:");
        panel.add(acLabel);
        JLabel acValue = new JLabel();
        panel.add(acValue);
        
        JLabel xrLabel = new JLabel("XR:");
        panel.add(xrLabel);
        JLabel xrValue = new JLabel();
        panel.add(xrValue);
        
        JLabel yrLabel = new JLabel("YR:");
        panel.add(yrLabel);
        JLabel yrValue = new JLabel();
        panel.add(yrValue);
        
        JLabel spLabel = new JLabel("SP:");
        panel.add(spLabel);
        JLabel spValue = new JLabel();
        panel.add(spValue);
        
        JLabel pcLabel = new JLabel("PC:");
        panel.add(pcLabel);
        JLabel pcValue = new JLabel();
        panel.add(pcValue);

        add(panel);
    }

    public void createDisplay() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Video output");
        panel.add(label);

        display = new Display();
        panel.add(display);

        add(panel);
    }
}
