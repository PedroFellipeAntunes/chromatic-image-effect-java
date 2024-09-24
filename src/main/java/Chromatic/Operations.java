package Chromatic;

import FileManager.PngReader;
import FileManager.PngSaver;
import ImageData.Pixel;
import Windows.ImageViewer;
import java.util.ArrayList;

public class Operations {
    public static TYPE type;
    
    public static void processFile(String filePath, boolean gray, boolean blend, int percentage, double angle, TYPE type) {
        Operations.type = type;
        PngReader imageToPixelList = new PngReader();
        
        //Get 2D matrix of pixels
        ArrayList<ArrayList<Pixel>> originalImage = imageToPixelList.readPNG(filePath, gray);
        
        //Apply effect
        ChromaticAberration ca = new ChromaticAberration();
        
        ArrayList<ArrayList<Pixel>> newImage = switch (type) {
            case TYPE.Interpolated -> ca.applyChromaticInterpolated(originalImage, percentage, angle);
            default -> ca.applyChromatic(originalImage, percentage, angle);
        };
        
        //Lighten blending mode
        if (blend) {
            Lighten l = new Lighten();
            newImage = l.applyLighten(originalImage, newImage);
        }
        
        //View images before saving
        ImageViewer viewer = new ImageViewer(newImage, filePath);
    }
    
    //Save files
    public static void saveImage(ArrayList<ArrayList<Pixel>> image, String filePath) {
        PngSaver listToImage = new PngSaver();
        listToImage.saveToFile("Chromatic["+Operations.type+"]", filePath, image);
    }
}