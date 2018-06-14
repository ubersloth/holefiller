package com.lightrocks.holefiller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageOperations {
	public double[][] load(String path) throws IOException {
		
		File file = new File(path);
		BufferedImage image = null;
		image = ImageIO.read(file);
		
		double[][] imageMatrix = new double[image.getHeight()][image.getWidth()];
		
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				Color color = new Color(image.getRGB(j, i));
				if (color.getRed() == color.getBlue() && color.getRed() == color.getGreen()) {
					imageMatrix[i][j] = color.getRed()/255D;
				}
				else {
					imageMatrix[i][j] = -1;
				}
			}
		}
		
		return imageMatrix;
	}

	public void save(double[][] imageMatrix, String path) throws IOException {
		File file = new File(path);
		BufferedImage image = new BufferedImage(imageMatrix[0].length, imageMatrix.length, BufferedImage.TYPE_INT_ARGB);
		
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				int component = (int)(imageMatrix[i][j]*255);
				image.setRGB(j, i, new Color(component, component, component).getRGB());				
			}
		}

		ImageIO.write(image, "png", file);
	}
}
