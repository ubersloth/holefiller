package com.lightrocks.holefiller.logic.fillers;

import java.util.List;

import com.lightrocks.holefiller.Consts;
import com.lightrocks.holefiller.interfaces.HoleFiller;
import com.lightrocks.holefiller.interfaces.WeightingGroup;
import com.lightrocks.holefiller.interfaces.WeightingLogic;
import com.lightrocks.holefiller.model.Edges;
import com.lightrocks.holefiller.model.Point;

public class GroupedHoleFiller implements HoleFiller {

	@Override
	public void fillHole(double[][] image, Edges edges, WeightingLogic<?> weightingLogic) {
		List<WeightingGroup> weightingGroups = weightingLogic.createWeightingGroups(1000, edges);
		
		for (int i = edges.min.x; i <= edges.max.x; i++) {
			for (int j = edges.min.y; j <= edges.max.y; j++) {
				if (image[i][j] == Consts.INVALID) {				
					Point ij = new Point(i, j);
					double weightedValueSum = 0;
					double weightSum = 0;
					
					for (WeightingGroup wg : weightingGroups) {
						double weight = weightingLogic.weight(ij, wg.getGroupRepresentative());
						weightedValueSum += weight*wg.getGroupRepresentative().value*wg.getCount();
						weightSum += weight*wg.getCount();
					}
					
					image[i][j] = weightedValueSum/weightSum;
				}
			}
		}		
	}
}
