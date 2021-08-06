package senberg.recruitmenttest;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class StatusService {
    private final JLabel statusText;
    private final List<String> messages = new LinkedList<>();

    public StatusService(JLabel statusText) {
        this.statusText = statusText;

        for (int i = 0; i < 5; i++) {
            addMessage("");
        }
    }

    public synchronized void addMessage(String message) {
        if (messages.size() == 5) {
            messages.remove(4);
        }

        messages.add(0, message);
        render();
    }

    private void render() {
        SwingUtilities.invokeLater(() -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html>");
            messages.forEach(message -> {
                stringBuilder.append(message);
                stringBuilder.append("<br>");
            });
            stringBuilder.append("</html>");
            statusText.setText(stringBuilder.toString());
        });
    }
}
