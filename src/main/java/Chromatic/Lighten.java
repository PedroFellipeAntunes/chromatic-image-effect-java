package Chromatic;

import ImageData.Pixel;
import java.util.ArrayList;

public class Lighten {
    public ArrayList<ArrayList<Pixel>> applyLighten(ArrayList<ArrayList<Pixel>> layerA, ArrayList<ArrayList<Pixel>> layerB) {
        ArrayList<ArrayList<Pixel>> outputImage = new ArrayList<>();
        
        for (int y = 0; y < layerA.size(); y++) {
            ArrayList<Pixel> row = new ArrayList<>();
            
            for (int x = 0; x < layerA.get(0).size(); x++) {
                Pixel newPixel = new Pixel(0, 0, 0, 0);
                
                Pixel pixelA = layerA.get(y).get(x);
                Pixel pixelB = layerB.get(y).get(x);
                
                //RED
                if (pixelB.getRed() > pixelA.getRed()) {
                    newPixel.setRed(pixelB.getRed());
                } else {
                    newPixel.setRed(pixelA.getRed());
                }
                
                //GREEN
                if (pixelB.getGreen() > pixelA.getGreen()) {
                    newPixel.setGreen(pixelB.getGreen());
                } else {
                    newPixel.setGreen(pixelA.getGreen());
                }
                
                //BLUE
                if (pixelB.getBlue() > pixelA.getBlue()) {
                    newPixel.setBlue(pixelB.getBlue());
                } else {
                    newPixel.setBlue(pixelA.getBlue());
                }
                
                //ALPHA
                if (pixelB.getAlpha() > pixelA.getAlpha()) {
                    newPixel.setAlpha(pixelB.getAlpha());
                } else {
                    newPixel.setAlpha(pixelA.getAlpha());
                }
                
                row.add(newPixel);
            }
            
            outputImage.add(row);
        }
        
        return outputImage;
    }
}