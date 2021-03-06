package com.lightrocks.holefiller.logic.weighting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lightrocks.holefiller.interfaces.WeightingGroup;
import com.lightrocks.holefiller.interfaces.WeightingLogic;
import com.lightrocks.holefiller.model.Boundry;
import com.lightrocks.holefiller.model.Point;

public class DistanceWeightingLogic implements WeightingLogic<DistanceWeightingArgs> {
	private DistanceWeightingArgs args;
	
	@Override
	public void initArgs(DistanceWeightingArgs weightingArgs) {
		args = weightingArgs;		
	}

	@Override
	public double weight(Point a, Point b) {
		return 1 / (Math.pow(a.distance(b), args.factor)+args.safeDivisor);
	}

	@Override
	public List<WeightingGroup> createWeightingGroups(double limit, Boundry boundry) {
		double xlength = boundry.max.x-boundry.min.x;
		double ylength = boundry.max.y-boundry.min.y;

		int groupLength = (int)Math.floor(Math.max(xlength, ylength)/Math.floor(Math.sqrt(limit)));
		groupLength = Math.max(groupLength, 1);
		
		HashMap<String, DistanceWeightingGroup> map = new HashMap<>();
		
		for (Point p : boundry.points.values()) {
			String key = (int)Math.floor(p.x / groupLength) + ":" + (int)Math.floor(p.y / groupLength);
			if (!map.containsKey(key)) {
				map.put(key, new DistanceWeightingGroup());
			}
			
			DistanceWeightingGroup group = map.get(key);
			group.add(p);
		}	
		
		return new ArrayList<WeightingGroup>(map.values());
	}
}
