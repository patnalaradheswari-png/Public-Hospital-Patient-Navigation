
        import javax.sound.sampled.*;
        import javax.swing.*;
        import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Project extends JFrame {

    private int currentStep = 0;
    private final int patientToken = 18;

    private JLabel titleLabel, tokenLabel, stepCountLabel, stopNameLabel, directionLabel, queueStatusLabel, audioStatusLabel;
    private JProgressBar progressBar;
    private JButton prevBtn, nextBtn, audioBtn, resetBtn;

    private final String appTitle = "Public Hospital Patient Navigation & Queue Guide";

    private final String[] stopNames = {
            "Step 1: OPD Registration Counter",
            "Step 2: General Medicine Consultation (OPD)",
            "Step 3: Diagnostic & Blood Lab",
            "Step 4: Government Pharmacy Counter"
    };

    private final String[] directions = {
            "Go straight from Main Entrance -> Ground Floor, Window No. 2 (Blue Signboard).",
            "Take stairs/lift near Reception -> 1st Floor, Left Corridor, Room No. 104.",
            "Come down to Ground Floor -> Follow Yellow Floor Line to East Wing Lab.",
            "Walk towards Exit Gate -> Ground Floor, Green Counter No. 5 with prescription."
    };

    private final int[] runningTokens = {16, 14, 17, 15};
    private final int[] waitMinutes = {5, 12, 3, 8};

    public Project() {
        setTitle("Public Hospital Navigation Dashboard (Low-Bandwidth Mode)");
        setSize(760, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(new Color(15, 76, 129));
        topPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        titleLabel = new JLabel(appTitle);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        topPanel.add(titleLabel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(new EmptyBorder(15, 25, 15, 25));
        centerPanel.setBackground(new Color(245, 248, 252));

        JPanel infoBar = new JPanel(new BorderLayout());
        infoBar.setOpaque(false);
        tokenLabel = new JLabel("Your Token: #" + patientToken);
        tokenLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        tokenLabel.setForeground(new Color(0, 102, 51));

        stepCountLabel = new JLabel("Stop 1 of 4");
        stepCountLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        stepCountLabel.setForeground(Color.DARK_GRAY);

        infoBar.add(tokenLabel, BorderLayout.WEST);
        infoBar.add(stepCountLabel, BorderLayout.EAST);
        centerPanel.add(infoBar);
        centerPanel.add(Box.createVerticalStrut(10));

        progressBar = new JProgressBar(0, 4);
        progressBar.setValue(1);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(680, 22));
        centerPanel.add(progressBar);
        centerPanel.add(Box.createVerticalStrut(18));

        stopNameLabel = new JLabel();
        stopNameLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        stopNameLabel.setForeground(new Color(20, 40, 80));
        centerPanel.add(stopNameLabel);
        centerPanel.add(Box.createVerticalStrut(10));

        directionLabel = new JLabel();
        directionLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        directionLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 225), 2),
                new EmptyBorder(12, 12, 12, 12)
        ));
        directionLabel.setOpaque(true);
        directionLabel.setBackground(Color.WHITE);
        centerPanel.add(directionLabel);
        centerPanel.add(Box.createVerticalStrut(15));

        queueStatusLabel = new JLabel();
        queueStatusLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        queueStatusLabel.setForeground(new Color(180, 60, 0));
        centerPanel.add(queueStatusLabel);
        centerPanel.add(Box.createVerticalStrut(12));

        audioStatusLabel = new JLabel("Audio Guide Ready (Click 'Play Voice Prompt' for audio assistance)");
        audioStatusLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        audioStatusLabel.setForeground(new Color(70, 70, 70));
        centerPanel.add(audioStatusLabel);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        bottomPanel.setBackground(new Color(230, 236, 245));

        prevBtn = new JButton("<< Previous Stop");
        audioBtn = new JButton("Play Voice Prompt");
        nextBtn = new JButton("Completed -> Next Stop >>");
        resetBtn = new JButton("New Visit");

        Font btnFont = new Font("SansSerif", Font.BOLD, 14);
        prevBtn.setFont(btnFont);
        audioBtn.setFont(btnFont);
        nextBtn.setFont(btnFont);
        resetBtn.setFont(btnFont);

        audioBtn.setBackground(new Color(255, 193, 7));
        nextBtn.setBackground(new Color(40, 167, 69));
        nextBtn.setForeground(Color.WHITE);

        prevBtn.addActionListener(e -> {
            if (currentStep > 0) {
                currentStep--;
                updateUIContent();
            }
        });

        nextBtn.addActionListener(e -> {
            if (currentStep < 3) {
                currentStep++;
                playAudioCue();
                updateUIContent();
            } else {
                JOptionPane.showMessageDialog(this,
                        "All hospital stops completed! Please exit safely.",
                        "Visit Completed", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        audioBtn.addActionListener(e -> playAudioCue());

        resetBtn.addActionListener(e -> {
            currentStep = 0;
            updateUIContent();
        });

        bottomPanel.add(prevBtn);
        bottomPanel.add(audioBtn);
        bottomPanel.add(nextBtn);
        bottomPanel.add(resetBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        updateUIContent();
    }

    private void updateUIContent() {
        titleLabel.setText(appTitle);
        stepCountLabel.setText("Stop " + (currentStep + 1) + " of 4");
        progressBar.setValue(currentStep + 1);
        stopNameLabel.setText(stopNames[currentStep]);
        directionLabel.setText("<html><b>Direction:</b> " + directions[currentStep] + "</html>");

        int running = runningTokens[currentStep];
        int ahead = Math.max(0, patientToken - running);
        int wait = waitMinutes[currentStep];

        queueStatusLabel.setText(
                "Queue Status: Serving Token #" + running +
                        "  |  People Ahead: " + ahead +
                        "  |  Est. Wait: ~" + wait + " mins"
        );

        prevBtn.setEnabled(currentStep > 0);
        nextBtn.setText(currentStep == 3 ? "Finish Visit" : "Completed -> Next Stop >>");
        audioStatusLabel.setText("Audio Ready: Press 'Play Voice Prompt' for spoken guidance.");
    }

    private void playAudioCue() {
        audioStatusLabel.setText("Speaking Guidance: \"" + directions[currentStep] + "\"");
        new Thread(() -> {
            try {
                float sampleRate = 8000f;
                byte[] buf = new byte[1];
                AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);
                SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
                sdl.open(af);
                sdl.start();
                for (int i = 0; i < 250 * 8; i++) {
                    double angle = i / (sampleRate / 600.0) * 2.0 * Math.PI;
                    buf[0] = (byte) (Math.sin(angle) * 80.0);
                    sdl.write(buf, 0, 1);
                }
                sdl.drain();
                sdl.stop();
                sdl.close();
            } catch (Exception ex) {
                Toolkit.getDefaultToolkit().beep();
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Project app = new Project();
            app.setVisible(true);
        });
    }
}