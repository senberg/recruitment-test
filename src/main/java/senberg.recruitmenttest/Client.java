package senberg.recruitmenttest;

import javax.swing.*;

public class Client {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            JFrame.setDefaultLookAndFeelDecorated(true);
        }
        
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
