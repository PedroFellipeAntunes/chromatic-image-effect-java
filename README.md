# Chromatic Aberration Image Effect

This project is a Java Maven application built using NetBeans IDE and Swing that applies the **chromatic aberration** effect to images. Users can select multiple image files, adjust parameters like angle and offset, and choose various effects such as grayscale, blending modes, and the type of chromatic aberration (simple or interpolated).

<p align="center">
  <img src="tests/baboon_Chromatic%5BSimple%5D.png" width="350" alt="Example 1">
</p>

<p align="center">
  <img src="tests/tulips_Chromatic%5BInterpolated%5D.png" width="350" alt="Example 2">
</p>

## Features

- **Image Selection**: Choose multiple images in `.png`, `.jpg`, or `.jpeg` formats from a dropdown list.
- **Effect Control**: Adjust chromatic aberration using:
  - **Angle** slider
  - **Offset** slider
- **Grayscale**: Convert images to grayscale using the **BT.709** standard with a dedicated button.
- **Blending Modes**: Use the **Lighten** blend mode when applying effects, toggleable via a button.
- **Chromatic Aberration Type**: Switch between **Simple** and **Interpolated** chromatic aberration modes.
- **Save Option**: Preview the modified image and choose to save it after processing.

## Output Format & Naming

- The output image is always saved in **PNG** format.
- The naming convention for the output file is:  
  **`original_name_Chromatic[Type].png`**  
  Where:
  - `original_name` is the original image's filename.
  - `[Type]` is either **Simple** or **Interpolated**, based on the selected chromatic aberration mode.

## Implementation Details

- The core image manipulation is handled using a data structure of `ArrayList<ArrayList<Pixel>>` to represent and modify image pixel data.
- `Pixel` is an object which contains separate `interger` values for **RGBA**.
- Each image is processed sequentially based on user input and settings, and a preview is shown after every operation.
- The final window provides an option to save the modified image or discard it.

## How to Use

1. **Start the Application**: Run the Java application in NetBeans or simply execute the `.jar` file in the **target** folder.
2. **Adjust Parameters**:
   - Use the **Angle** and **Offset** sliders to control the chromatic aberration effect.
   - Click the **Grayscale** button to convert the image to grayscale using BT.709.
   - Use the **Blend** button to enable/disable blending via the **Lighten** mode.
   - Toggle between **Simple** and **Interpolated** chromatic aberration using the respective buttons.
3. **Select Images**: Use the dropdown menu to load multiple image files (`.png`, `.jpg`, or `.jpeg`).
4. **Preview & Save**: After applying the effect, preview each edited image. You will be prompted to save the modified image as a PNG file with the new name.
