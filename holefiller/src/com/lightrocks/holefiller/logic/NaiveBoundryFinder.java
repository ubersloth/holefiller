package com.lightrocks.holefiller.logic;

import com.lightrocks.holefiller.Consts;
import com.lightrocks.holefiller.model.Boundry;
import com.lightrocks.holefiller.model.Point;

public class NaiveBoundryFinder{
	public Boundry findBoundry(double[][] image) {
		Boundry boundry = new Boundry();

		// Runtime complexity is m*n of image dimensions
		// A more sophisticated boundry finder is possible, but anyway, finding the hole initially will still cost m*n of image dimensions
		// Also, it handles holes that are doughnut shaped or touching the sides of the image
		for (int row = 0; row < image.length; row++) {
			for (int col = 0; col < image[0].length; col++) {
				if (image[row][col] != Consts.INVALID) {
					nextpixel: for (int i = Math.max(row-1, 0); i <= Math.min(row+1, image.length-1); i++) {
						for (int j = Math.max(col-1, 0); j <= Math.min(col+1, image[0].length-1); j++) {
							if (image[i][j] == Consts.INVALID) {
								Point boundryPoint = new Point(row, col, image[row][col]);
								boundry.points.put(boundryPoint, boundryPoint);
								boundry.min.x = Math.min(boundry.min.x, row);
								boundry.min.y = Math.min(boundry.min.y, col);
								boundry.max.x = Math.max(boundry.max.x, row);
								boundry.max.y = Math.max(boundry.max.y, col);
								
								break nextpixel;
							}
						}
					}
				}
			}
		}
		
		return boundry;
	}
}
