import javax.swing.border.AbstractBorder;
import java.awt.*;

class RoundedBorder extends AbstractBorder {
    private final int radius;
    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(c.getForeground());
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}
