package com.lightrocks.holefiller.logic;

import com.lightrocks.holefiller.Consts;
import com.lightrocks.holefiller.model.Edges;
import com.lightrocks.holefiller.model.Point;

public class NaiveEdgeFinder{
	public Edges findEdges(double[][] image) {
		Edges e = new Edges();

		// Runtime complexity is m*n of image dimensions
		// A more sophisticated edge finder is possible, but anyway, finding the hole initially will still cost m*n of image dimensions
		// Also, it handles holes that are doughnut shaped or touching the sides of the image
		for (int row = 0; row < image.length; row++) {
			for (int col = 0; col < image[0].length; col++) {
				if (image[row][col] != Consts.INVALID) {
					nextpixel: for (int i = Math.max(row-1, 0); i <= Math.min(row+1, image.length-1); i++) {
						for (int j = Math.max(col-1, 0); j <= Math.min(col+1, image[0].length-1); j++) {
							if (image[i][j] == Consts.INVALID) {
								Point edgePoint = new Point(row, col, image[row][col]);
								e.edgePoints.put(edgePoint, edgePoint);
								e.min.x = Math.min(e.min.x, row);
								e.min.y = Math.min(e.min.y, col);
								e.max.x = Math.max(e.max.x, row);
								e.max.y = Math.max(e.max.y, col);
								
								break nextpixel;
							}
						}
					}
				}
			}
		}
		
		return e;
	}
}
