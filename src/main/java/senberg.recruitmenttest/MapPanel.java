package senberg.recruitmenttest;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

public class MapPanel extends JPanel {
    private static final int SIZE = 300;

    public MapPanel() {
        setLayout(null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(640, SIZE + 2);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        for (int x = 0; x <= SIZE; x += SIZE / 10) {
            g.drawLine(x, 0, x, SIZE);
        }

        for (int y = 0; y <= SIZE; y += SIZE / 10) {
            g.drawLine(0, y, SIZE, y);
        }

        g2d.dispose();
    }

    public void setCoordinates(Set<Coordinate> coordinates) {
        removeAll();

        if (coordinates != null && !coordinates.isEmpty()) {
            Optional<Integer> minX = coordinates.stream().map(Coordinate::getX).min(Comparator.naturalOrder());
            Optional<Integer> maxX = coordinates.stream().map(Coordinate::getX).max(Comparator.naturalOrder());
            Optional<Integer> minY = coordinates.stream().map(Coordinate::getY).min(Comparator.naturalOrder());
            Optional<Integer> maxY = coordinates.stream().map(Coordinate::getY).max(Comparator.naturalOrder());
            int xRange = maxX.get() - minX.get();
            int yRange = maxY.get() - minY.get();

            coordinates.forEach(coordinate -> {
                JLabel symbol = new JLabel("\u2622");
                add(symbol);
                symbol.setSize(10, 10);

                double xDistanceFromMin = coordinate.getX() - minX.get();
                double xPercent = xDistanceFromMin / xRange;
                double renderedX = xPercent * SIZE - 4;

                double yDistanceFromMin = coordinate.getY() - minY.get();
                double yPercent = yDistanceFromMin / yRange;
                double renderedY = yPercent * SIZE - 4;

                symbol.setLocation((int) renderedX, (int) renderedY);
                symbol.setToolTipText(coordinate.getLabel());
            });
        }

        SwingUtilities.invokeLater(this::revalidate);
        SwingUtilities.invokeLater(this::repaint);
    }
}
