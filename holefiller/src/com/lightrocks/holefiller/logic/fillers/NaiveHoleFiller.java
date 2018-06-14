package com.lightrocks.holefiller.logic.fillers;

import com.lightrocks.holefiller.Consts;
import com.lightrocks.holefiller.interfaces.HoleFiller;
import com.lightrocks.holefiller.interfaces.WeightingLogic;
import com.lightrocks.holefiller.model.Edges;
import com.lightrocks.holefiller.model.Point;

public class NaiveHoleFiller implements HoleFiller {
	@Override
	public void fillHole(double[][] image, Edges edges, WeightingLogic<?> weightingLogic) {
		for (int i = edges.min.x; i <= edges.max.x; i++) {
			for (int j = edges.min.y; j <= edges.max.y; j++) {
				if (image[i][j] == Consts.INVALID) {				
					Point ij = new Point(i, j);
					double weightedValueSum = 0;
					double weightSum = 0;
					
					for (Point p : edges.edgePoints.values()) {
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
