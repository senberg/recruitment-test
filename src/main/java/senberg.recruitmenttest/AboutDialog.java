package senberg.recruitmenttest;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {
    public AboutDialog(JFrame parent){
        super(parent, "About Recruitment Test Client", true);
        setPreferredSize(new Dimension(320, 240));

        Box textContent = Box.createVerticalBox();
        getContentPane().add(textContent);
        textContent.add(Box.createGlue());
        textContent.add(new JLabel("Recruitment Test Client 1.0"));
        textContent.add(new JLabel("Author: Staffan Enberg"));
        textContent.add(new JLabel("Email: senberg@live.se"));
        textContent.add(Box.createGlue());

        JPanel buttonArea = new JPanel();
        getContentPane().add(buttonArea, "South");
        JButton okButton = new JButton("Ok");
        buttonArea.add(okButton);
        okButton.addActionListener(event -> setVisible(false));

        pack();
        setLocationRelativeTo(parent);
    }
}
