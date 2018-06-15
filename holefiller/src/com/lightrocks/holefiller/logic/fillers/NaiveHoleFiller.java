package com.lightrocks.holefiller.logic.fillers;

import com.lightrocks.holefiller.Consts;
import com.lightrocks.holefiller.interfaces.HoleFiller;
import com.lightrocks.holefiller.interfaces.WeightingLogic;
import com.lightrocks.holefiller.model.Boundry;
import com.lightrocks.holefiller.model.Point;

public class NaiveHoleFiller implements HoleFiller {
	@Override
	public void fillHole(double[][] image, Boundry boundry, WeightingLogic<?> weightingLogic) {
		for (int i = boundry.min.x; i <= boundry.max.x; i++) {
			for (int j = boundry.min.y; j <= boundry.max.y; j++) {
				if (image[i][j] == Consts.INVALID) {				
					Point ij = new Point(i, j);
					double weightedValueSum = 0;
					double weightSum = 0;
					
					for (Point p : boundry.points.values()) {
						double weight = weightingLogic.weight(p, ij);
						weightedValueSum += weight*p.value;
						weightSum += weight;
					}
					
					image[i][j] = weightedValueSum/weightSum;
				}
			}
		}		
	}
}
