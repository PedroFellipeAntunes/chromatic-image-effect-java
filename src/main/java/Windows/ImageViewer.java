package Windows;

import ImageData.Pixel;
import Chromatic.Operations;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageViewer extends JDialog {
    private ImagePanel panel;
    private JPanel buttonPanel;
    private JButton saveButton;
    private JButton goBackButton;
    
    private boolean goBack = false;
    
    public boolean wentBack() {
        return goBack;
    }
    
    public ImageViewer(ArrayList<ArrayList<Pixel>> bandedImage, String filePath) {
        super((Frame) null, "Image Viewer", true);
        
        panel = new ImagePanel(bandedImage);
        panel.setBackground(new Color(61, 56, 70));
        
        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        
        saveButton = new JButton("Save");
        goBackButton = new JButton("Go Back");
        
        setButtonsVisuals(saveButton);
        setButtonsVisuals(goBackButton);
        
        saveButton.addActionListener(e -> {
            Operations.saveImage(bandedImage, filePath);
            goBack = true;
            dispose();
        });
        
        goBackButton.addActionListener(e -> {
            goBack = true;
            dispose();
        });
        
        buttonPanel.add(saveButton);
        buttonPanel.add(goBackButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);
        
        pack();
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void setButtonsVisuals(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 40));
    }
    
    class ImagePanel extends JPanel {
        private BufferedImage image;
        
        public ImagePanel(ArrayList<ArrayList<Pixel>> bandedImage) {
            int width = bandedImage.get(0).size();
            int height = bandedImage.size();
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Pixel p = bandedImage.get(i).get(j);
                    Color color = new Color(p.getRed(), p.getGreen(), p.getBlue(), p.getAlpha());
                    image.setRGB(j, i, color.getRGB());
                }
            }
            
            if (width > height) {
                setPreferredSize(new Dimension(700, 500));
            } else if (width == height) {
                setPreferredSize(new Dimension(500, 500));
            } else {
                setPreferredSize(new Dimension(500, 600));
            }
        }
        
        public void updateImage(ArrayList<ArrayList<Pixel>> bandedImage) {
            int width = bandedImage.get(0).size();
            int height = bandedImage.size();
            
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Pixel p = bandedImage.get(i).get(j);
                    Color color = new Color(p.getRed(), p.getGreen(), p.getBlue(), p.getAlpha());
                    image.setRGB(j, i, color.getRGB());
                }
            }
            
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            double scaleX = (double) getWidth() / image.getWidth();
            double scaleY = (double) getHeight() / image.getHeight();
            double scale = Math.min(scaleX, scaleY);
            
            int newWidth = (int) (image.getWidth() * scale);
            int newHeight = (int) (image.getHeight() * scale);
            int x = (getWidth() - newWidth) / 2;
            int y = (getHeight() - newHeight) / 2;
            
            g.drawImage(image, x, y, newWidth, newHeight, this);
        }
    }
}