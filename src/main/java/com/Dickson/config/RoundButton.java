package com.Dickson.config;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RoundButton extends JButton {
    private Color borderColor;

    public RoundButton(String label, Color backgroundColor, Color borderColor) {
        super(label);
        setBackground(backgroundColor);
        setForeground(borderColor);
        this.borderColor = borderColor;
        setFocusable(false);

        /*
         These statements enlarge the button so that it
         becomes a circle rather than an oval.
        */
        Dimension size = getPreferredSize();
        size.width = size.height = Math.max(size.width, size.height);
        setPreferredSize(new Dimension(15, 15));  // Set the size of the button

        /*
         This call causes the JButton not to paint the background.
         This allows us to paint a round background.
        */
        setContentAreaFilled(false);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (getModel().isArmed()) {
            g2d.setColor(Color.LIGHT_GRAY);
        } else {
            g2d.setColor(getBackground());
        }
        g2d.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(borderColor); // Set the border color here
        g2d.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    }


    // Hit detection.
    Shape shape;

    public boolean contains(int x, int y) {
        // If the button has changed size, make a new shape object.
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }
        return shape.contains(x, y);
    }
}
