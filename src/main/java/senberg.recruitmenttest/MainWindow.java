package senberg.recruitmenttest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MainWindow extends JFrame {
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final CoordinateService coordinateService;
    private final StatusService statusService;
    private ScheduledFuture<?> refreshTimer;

    public MainWindow() {
        super("Recruitment Test Client");
        setPreferredSize(new Dimension(640, 480));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);
        menu.add(Box.createHorizontalGlue());
        JMenu help = new JMenu("Help");
        menu.add(help);
        help.setMnemonic(KeyEvent.VK_H);
        JMenuItem about = new JMenuItem("About");
        help.add(about);
        about.setMnemonic(KeyEvent.VK_A);
        about.addActionListener(event -> new AboutDialog(this).setVisible(true));

        JPanel contentPanel = new JPanel();
        getContentPane().add(contentPanel);
        contentPanel.setLayout(new BorderLayout());

        JLabel statusText = new JLabel("");
        contentPanel.add(statusText, "South");
        statusService = new StatusService(statusText);
        statusService.addMessage("Started.");
        statusText.setToolTipText("The last five status messages are shown here.");

        MapPanel mapPanel = new MapPanel();
        contentPanel.add(mapPanel, "North");
        coordinateService = new CoordinateService(mapPanel, statusService);
        startTimer();

        JButton manualRefresh = new JButton("Manual refresh");
        contentPanel.add(manualRefresh, "West");
        manualRefresh.setPreferredSize(new Dimension(200, 20));
        manualRefresh.addActionListener(event -> coordinateService.refreshCoordinates());

        JButton stopAutomaticRefresh = new JButton("Stop automatic refresh");
        contentPanel.add(stopAutomaticRefresh, "Center");
        stopAutomaticRefresh.setPreferredSize(new Dimension(200, 20));
        stopAutomaticRefresh.addActionListener(event -> stopTimer());

        JButton startAutomaticRefresh = new JButton("Start automatic refresh");
        contentPanel.add(startAutomaticRefresh, "East");
        startAutomaticRefresh.setPreferredSize(new Dimension(200, 20));
        startAutomaticRefresh.addActionListener(event -> startTimer());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void stopTimer() {
        synchronized (executor) {
            if (refreshTimer != null && !refreshTimer.isCancelled()) {
                statusService.addMessage("Stopping automatic refresh timer.");
                refreshTimer.cancel(true);
            }
        }
    }

    private void startTimer() {
        synchronized (executor) {
            if (refreshTimer == null || refreshTimer.isCancelled()) {
                statusService.addMessage("Starting new automatic refresh timer.");
                refreshTimer = executor.scheduleAtFixedRate(coordinateService::refreshCoordinates, 0, 30, TimeUnit.SECONDS);
            }
        }
    }
}
