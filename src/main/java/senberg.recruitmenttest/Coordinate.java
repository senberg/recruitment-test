package senberg.recruitmenttest;

class Coordinate {
    private final int x, y;
    private final String label;

    Coordinate(int x, int y, String label) {
        this.x = x;
        this.y = y;
        this.label = label;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getLabel() {
        return label;
    }
}
