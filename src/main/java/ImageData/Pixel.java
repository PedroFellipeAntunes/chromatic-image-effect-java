package ImageData;

import java.util.Objects;

public class Pixel {
    private int red, green, blue, alpha;
    
    public Pixel(int red, int green, int blue, int alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    
    public Pixel(Pixel pixel) {
        this.red = pixel.red;
        this.green = pixel.green;
        this.blue = pixel.blue;
        this.alpha = pixel.alpha;
    }
    
    public int getAverage() {
        return (this.red + this.green + this.blue) / 3;
    }
    
    public int getRed() {
        return red;
    }
    
    public void setRed(int red) {
        this.red = red;
    }
    
    public int getGreen() {
        return green;
    }
    
    public void setGreen(int green) {
        this.green = green;
    }
    
    public int getBlue() {
        return blue;
    }
    
    public void setBlue(int blue) {
        this.blue = blue;
    }
    
    public int getAlpha() {
        return alpha;
    }
    
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pixel pixel = (Pixel) o;
        return red == pixel.red && green == pixel.green && blue == pixel.blue && alpha == pixel.alpha;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue, alpha);
    }
}