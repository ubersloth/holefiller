package com.lightrocks.holefiller.logic.fillers;

import com.lightrocks.holefiller.Consts;
import com.lightrocks.holefiller.interfaces.HoleFiller;
import com.lightrocks.holefiller.interfaces.WeightingLogic;
import com.lightrocks.holefiller.model.Edges;
import com.lightrocks.holefiller.model.Point;

public class ProximityHoleFiller implements HoleFiller {

	@Override
	public void fillHole(double[][] image, Edges edges, WeightingLogic<?> weightingLogic) {
		for (int i = edges.min.x; i <= edges.max.x; i++) {
			for (int j = edges.min.y; j <= edges.max.y; j++) {
				if (image[i][j] == Consts.INVALID) {				
					Point ij = new Point(i, j);
					double weightedValueSum = 0;
					double weightSum = 0;
					
					for (int n = Math.max(edges.min.x, i-Consts.PROXIMITY); n <= Math.min(edges.max.x, i+Consts.PROXIMITY); n++) {
						for (int m = Math.max(edges.min.y, j-Consts.PROXIMITY); m <= Math.min(edges.max.y, j+Consts.PROXIMITY); m++) {
							Point p = new Point(n,m);
							if (edges.edgePoints.containsKey(p)) {
								p = edges.edgePoints.get(p);
								
								double weight = weightingLogic.weight(p, ij);
								weightedValueSum += weight*p.value;
								weightSum += weight;								
							} 
						}
					}

					image[i][j] = weightedValueSum/weightSum;
				}
			}
		}		
	}
}
