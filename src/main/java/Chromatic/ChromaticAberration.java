package Chromatic;

import ImageData.Pixel;
import java.util.ArrayList;

public class ChromaticAberration {
    //Unused code
    private int calculateOffset(ArrayList<ArrayList<Pixel>> image, double degrees, int percentage) {
        //Get max offset based on angle and image size
        double angle = Math.toRadians(degrees);
        
        int maxX = image.get(0).size();
        int maxY = image.size();
        
        int maxOffset = (int) Math.min(maxX / Math.abs(Math.cos(angle)), maxY / Math.abs(Math.sin(angle)));
        
        //New offset based on percentage
        int offset = maxOffset * percentage / 100;
        
        return offset;
    }
    
    public ArrayList<ArrayList<Pixel>> applyChromatic(ArrayList<ArrayList<Pixel>> image, int offset, double degrees) {
        if (offset == 0) {
            return image;
        }
        
        double angle = Math.toRadians(degrees);
        
        //Calculate angle for offset
        int dx = (int) (offset * Math.cos(angle));
        int dy = (int) (offset * Math.sin(angle));
        
        ArrayList<ArrayList<Pixel>> outputImage = new ArrayList<>();
        
        for (int y = 0; y < image.size(); y++) {
            ArrayList<Pixel> row = new ArrayList<>();
            
            for (int x = 0; x < image.get(0).size(); x++) {
                row.add(new Pixel(0, 0, 0, 0));
            }
            
            outputImage.add(row);
        }
        
        for (int y = 0; y < image.size(); y++) {
            for (int x = 0; x < image.get(0).size(); x++) {
                Pixel pixel = image.get(y).get(x);
                
                //Offset for Red
                if (x - dx >= 0 && y - dy >= 0 && x - dx < image.get(0).size() && y - dy < image.size()) {
                    outputImage.get(y - dy).get(x - dx).setRed(pixel.getRed());
                    outputImage.get(y - dy).get(x - dx).setAlpha(pixel.getAlpha());
                }
                
                //Keep Green in place
                outputImage.get(y).get(x).setGreen(pixel.getGreen());
                outputImage.get(y).get(x).setAlpha(pixel.getAlpha());
                
                //Offset for Blue
                if (x + dx >= 0 && y + dy >= 0 && x + dx < image.get(0).size() && y + dy < image.size()) {
                    outputImage.get(y + dy).get(x + dx).setBlue(pixel.getBlue());
                    outputImage.get(y + dy).get(x + dx).setAlpha(pixel.getAlpha());
                }
            }
        }
        
        return outputImage;
    }
    
    public ArrayList<ArrayList<Pixel>> applyChromaticInterpolated(ArrayList<ArrayList<Pixel>> image, int offset, double degrees) {
        if (offset == 0) {
            return image;
        }
        
        double angle = Math.toRadians(degrees);
        
        //Calculate angle for offset
        int dx = (int) (offset * Math.cos(angle));
        int dy = (int) (offset * Math.sin(angle));
        
        ArrayList<ArrayList<Pixel>> outputImage = new ArrayList<>();
        
        for (int y = 0; y < image.size(); y++) {
            ArrayList<Pixel> row = new ArrayList<>();

            for (int x = 0; x < image.get(0).size(); x++) {
                row.add(new Pixel(0, 0, 0, 0));
            }

            outputImage.add(row);
        }
        
        for (int y = 0; y < image.size(); y++) {
            for (int x = 0; x < image.get(0).size(); x++) {
                Pixel pixel = image.get(y).get(x);
                
                //Offset for Red
                if (x - dx >= 0 && y - dy >= 0 && x - dx < image.get(0).size() && y - dy < image.size()) {
                    Pixel redPixel = image.get(y - dy).get(x - dx);
                    int interpolatedRed = (pixel.getRed() + redPixel.getRed()) / 2;
                    
                    outputImage.get(y - dy).get(x - dx).setRed(interpolatedRed);
                    outputImage.get(y - dy).get(x - dx).setAlpha(pixel.getAlpha());
                }
                
                //Keep Green in place
                outputImage.get(y).get(x).setGreen(pixel.getGreen());
                outputImage.get(y).get(x).setAlpha(pixel.getAlpha());
                
                //Offset for Blue
                if (x + dx >= 0 && y + dy >= 0 && x + dx < image.get(0).size() && y + dy < image.size()) {
                    Pixel bluePixel = image.get(y + dy).get(x + dx);
                    int interpolatedBlue = (pixel.getBlue() + bluePixel.getBlue()) / 2;
                    
                    outputImage.get(y + dy).get(x + dx).setBlue(interpolatedBlue);
                    outputImage.get(y + dy).get(x + dx).setAlpha(pixel.getAlpha());
                }
            }
        }
        
        return outputImage;
    }
}