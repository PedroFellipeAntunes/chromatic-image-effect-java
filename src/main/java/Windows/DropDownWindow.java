package Windows;

import Chromatic.Operations;
import Chromatic.TYPE;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DropDownWindow {
    private JFrame frame;
    private JLabel dropLabel;
    private JSlider slider;
    private JTextField valueField;
    private JSlider sliderAngle;
    private JTextField valueFieldAngle;
    private JButton simpleButton;
    private JButton interpolatedButton;
    
    private int limit = 5;
    private double angle = 0.0;
    private TYPE type = TYPE.Simple;
    private JButton grayButton;
    private JButton blendButton;
    private boolean gray = false;
    private boolean blend = false;
    private boolean loading = false;
    private Font defaultFont = UIManager.getDefaults().getFont("Label.font");
    
    public DropDownWindow() {
        frame = new JFrame("Image Chromatic Aberration");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setLayout(new BorderLayout());
        
        dropLabel = new JLabel("Drop IMAGE files here", SwingConstants.CENTER);
        dropLabel.setPreferredSize(new Dimension(300, 200));
        dropLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        dropLabel.setForeground(Color.WHITE);
        dropLabel.setOpaque(true);
        dropLabel.setBackground(Color.BLACK);
        dropLabel.setTransferHandler(new TransferHandler() {
            public boolean canImport(TransferHandler.TransferSupport support) {
                if (!loading && support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    return true;
                }
                
                return false;
            }
            
            public boolean importData(TransferHandler.TransferSupport support) {
                if (!canImport(support)) {
                    return false;
                }
                
                try {
                    Transferable transferable = support.getTransferable();
                    List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                    
                    for (File file : files) {
                        if (!file.getName().toLowerCase().endsWith(".png")
                                && !file.getName().toLowerCase().endsWith(".jpg")
                                && !file.getName().toLowerCase().endsWith(".jpeg")) {
                            JOptionPane.showMessageDialog(frame, "Incorrect image format, use: png, jpg or jpeg", "Error", JOptionPane.ERROR_MESSAGE);
                            
                            return false;
                        }
                    }
                    
                    dropLabel.setText("LOADING (1/" + files.size() + ")");
                    loading = true;
                    ableOrDisableButton(grayButton);
                    ableOrDisableButton(blendButton);
                    
                    frame.repaint();
                    
                    new Thread(() -> {
                        int filesProcessed = 1;
                        
                        for (File file : files) {
                            Operations.processFile(file.getPath(), gray, blend, limit, angle, type);
                            
                            filesProcessed++;
                            
                            final int finalFilesProcessed = filesProcessed;
                            
                            SwingUtilities.invokeLater(() -> {
                                dropLabel.setText("LOADING (" + finalFilesProcessed + "/" + files.size() + ")");
                            });
                        }
                        
                        SwingUtilities.invokeLater(() -> {
                            dropLabel.setText("Images Edited");
                            
                            Timer resetTimer = new Timer(1000, e2 -> {
                                dropLabel.setText("Drop IMAGE files here");
                                loading = false;
                                ableOrDisableButton(grayButton);
                                ableOrDisableButton(blendButton);
                            });
                            
                            resetTimer.setRepeats(false);
                            resetTimer.start();
                        });
                    }).start();
                    
                    return true;
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        });
        
        //Slider percentage
        slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        slider.setMajorTickSpacing(25);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBackground(Color.BLACK);
        slider.setForeground(Color.WHITE);
        slider.setValue(limit);
        
        //Value of slider
        valueField = new JTextField();
        valueField.setForeground(Color.WHITE);
        valueField.setBackground(Color.BLACK);
        valueField.setFont(defaultFont);
        valueField.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        valueField.setText(String.valueOf(slider.getValue()));
        valueField.setPreferredSize(new Dimension(50, 20));
        
        valueField.addActionListener(e -> {
            if (!loading) {
                String text = valueField.getText();
                
                if (!text.isEmpty()) {
                    text = text.substring(0, Math.min(text.length(), 3));
                    
                    int value = Integer.parseInt(text);
                    
                    value = Math.max(0, Math.min(100, value));
                    
                    slider.setValue(value);
                    valueField.setText(String.valueOf(value));
                } else {
                    valueField.setText(String.valueOf(slider.getValue()));
                }
                
                valueField.transferFocus();
            }
        });
        
        slider.addChangeListener(e -> {
            if (!loading) {
                limit = slider.getValue();
                
                valueField.setText(String.valueOf(limit));
            }
        });
        
        //Panel with sliders and values
        JPanel sliderPanel1 = new JPanel(new BorderLayout());
        sliderPanel1.add(slider, BorderLayout.WEST);
        sliderPanel1.add(valueField, BorderLayout.EAST);
        
        //Slider angle
        sliderAngle = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        sliderAngle.setMajorTickSpacing(90);
        sliderAngle.setMinorTickSpacing(45);
        sliderAngle.setPaintTicks(true);
        sliderAngle.setPaintLabels(true);
        sliderAngle.setBackground(Color.BLACK);
        sliderAngle.setForeground(Color.WHITE);
        sliderAngle.setValue((int) angle);
        
        //Value of slider angle
        valueFieldAngle = new JTextField();
        valueFieldAngle.setForeground(Color.WHITE);
        valueFieldAngle.setBackground(Color.BLACK);
        valueFieldAngle.setFont(defaultFont);
        valueFieldAngle.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        valueFieldAngle.setText(String.valueOf(angle));
        valueFieldAngle.setPreferredSize(new Dimension(50, 20));
        
        valueFieldAngle.addActionListener(e -> {
            if (!loading) {
                String text = valueFieldAngle.getText();
                
                if (!text.isEmpty()) {
                    text = text.substring(0, Math.min(text.length(), 3));
                    
                    double value = Integer.parseInt(text);
                    
                    value = Math.max(0.0, Math.min(360.0, value));
                    
                    sliderAngle.setValue((int) value);
                    valueFieldAngle.setText(String.valueOf(value));
                } else {
                    valueFieldAngle.setText(String.valueOf(sliderAngle.getValue()));
                }
                
                valueFieldAngle.transferFocus();
            }
        });
        
        sliderAngle.addChangeListener(e -> {
            if (!loading) {
                angle = sliderAngle.getValue();
                
                valueFieldAngle.setText(String.valueOf(angle));
            }
        });
        
        //Panel with sliders and values
        JPanel sliderPanel2 = new JPanel(new BorderLayout());
        sliderPanel2.add(sliderAngle, BorderLayout.WEST);
        sliderPanel2.add(valueFieldAngle, BorderLayout.EAST);
        
        //GrayScale button
        grayButton = new JButton("Gray Scale");
        setButtonsVisuals(grayButton);
        
        grayButton.addActionListener(e -> {
            if (!loading) {
                if (gray == true) {
                    resetButton(grayButton);
                    gray = false;
                } else {
                    grayButton.setBackground(Color.WHITE);
                    grayButton.setForeground(Color.BLACK);
                    gray = true;
                }
            }
        });
        
        //Blend button
        blendButton = new JButton("Blend");
        setButtonsVisuals(blendButton);
        
        blendButton.addActionListener(e -> {
            if (!loading) {
                if (blend == true) {
                    resetButton(blendButton);
                    blend = false;
                } else {
                    blendButton.setBackground(Color.WHITE);
                    blendButton.setForeground(Color.BLACK);
                    blend = true;
                }
            }
        });
        
        //Bottom panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        controlPanel.setBackground(Color.BLACK);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //Panel for sliders
        JPanel slidersPanel = new JPanel(new BorderLayout());
        slidersPanel.add(sliderPanel1, BorderLayout.NORTH);
        slidersPanel.add(sliderPanel2, BorderLayout.SOUTH);
        
        controlPanel.add(slidersPanel);
        controlPanel.add(grayButton);
        controlPanel.add(blendButton);
        
        simpleButton = new JButton("Simple");
        setButtonsVisuals(simpleButton);
        simpleButton.setBackground(Color.WHITE);
        simpleButton.setForeground(Color.BLACK);
        
        simpleButton.addActionListener(e -> {
            if (!loading) {
                if (!type.equals(TYPE.Simple)) {
                    simpleButton.setBackground(Color.WHITE);
                    simpleButton.setForeground(Color.BLACK);
                    
                    resetButton(interpolatedButton);
                    
                    type = TYPE.Simple;
                }
            }
        });
        
        interpolatedButton = new JButton("Interpolated");
        setButtonsVisuals(interpolatedButton);
        
        interpolatedButton.addActionListener(e -> {
            if (!loading) {
                if (!type.equals(TYPE.Interpolated)) {
                    interpolatedButton.setBackground(Color.WHITE);
                    interpolatedButton.setForeground(Color.BLACK);
                    
                    resetButton(simpleButton);
                    
                    type = TYPE.Interpolated;
                }
            }
        });
        
        //Panel with buttons on side
        JPanel verticalButtonsPanel = new JPanel(new GridLayout(0, 1));
        verticalButtonsPanel.setBackground(Color.BLACK);
        verticalButtonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        verticalButtonsPanel.add(simpleButton);
        verticalButtonsPanel.add(interpolatedButton);
        
        frame.add(verticalButtonsPanel, BorderLayout.EAST);
        
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.add(dropLabel, BorderLayout.CENTER);
        
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xPos = (screenSize.width - frame.getWidth()) / 2;
        int yPos = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(xPos, yPos);
        
        frame.setVisible(true);
    }
    
    private void setButtonsVisuals(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 40));
    }
    
    private void resetButton(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
    }
    
    private void ableOrDisableButton(JButton button) {
        button.setEnabled(!loading);
    }
}