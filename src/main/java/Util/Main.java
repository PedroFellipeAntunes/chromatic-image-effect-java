package Util;

/*
Pedro Fellipe Cruz Antunes
Code that creates a window which receives an n amount of PNG files and applies
chromatic aberration effect to the image.

The output will be n PNG files.

User inputs:
    Drop files;
    Define offset
    Save png file to the same folder as the original file;
*/

import Windows.DropDownWindow;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DropDownWindow dropDownWindow = new DropDownWindow();
        });
    }
}