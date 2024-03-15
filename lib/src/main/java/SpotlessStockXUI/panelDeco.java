// Last Update: 2021-10-18 20:54:26
//It will add round boarders to our JPanels.
// Austin Stephens
// 02/22/2024
// Professor Kumar
// Advanced Java Programming
// COP3805C
//This is the panelDeco class. It will add round boarders to our JPanels.
package SpotlessStockXUI;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

public class panelDeco extends JPanel {

    private int roundTopLeft = 0;
    private int roundTopRight = 0;
    private int roundBottomLeft = 0;
    private int roundBottomRight = 0;

    public panelDeco() {
        setOpaque(false);
    }

    public int getRoundTopLeft() {
        return roundTopLeft;
    }

    public void setRoundTopLeft(int roundTopLeft) {
        this.roundTopLeft = roundTopLeft;
        repaint();
    }

    public int getRoundTopRight() {
        return roundTopRight;
    }

    public void setRoundTopRight(int roundTopRight) {
        this.roundTopRight = roundTopRight;
        repaint();
    }

    public int getRoundBottomLeft() {
        return roundBottomLeft;
    }

    public void setRoundBottomLeft(int roundBottomLeft) {
        this.roundBottomLeft = roundBottomLeft;
        repaint();
    }

    public int getRoundBottomRight() {
        return roundBottomRight;
    }

    public void setRoundBottomRight(int roundBottomRight) {
        this.roundBottomRight = roundBottomRight;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());

        Shape roundedRect = createRoundedRect();
        g2d.fill(roundedRect);

        g2d.dispose();
        super.paintComponent(g);
    }

    
    private RoundRectangle2D createRoundedRect() {
        int width = getWidth();
        int height = getHeight();

            return new RoundRectangle2D.Double(0, 0, width, height,
                    roundTopLeft, roundTopLeft);
        }
}