package com.lightrocks.holefiller.logic.weighting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lightrocks.holefiller.interfaces.WeightingGroup;
import com.lightrocks.holefiller.interfaces.WeightingLogic;
import com.lightrocks.holefiller.model.Edges;
import com.lightrocks.holefiller.model.Point;

public class DistanceWeightingLogic implements WeightingLogic<DistanceWeightingArgs> {
	private DistanceWeightingArgs args;
	
	@Override
	public void initArgs(DistanceWeightingArgs weightingArgs) {
		args = weightingArgs;		
	}

	@Override
	public double weight(Point a, Point b) {
		return 1 / Math.pow(a.distance(b), args.factor)+args.safeDivisor;
	}

	@Override
	public List<WeightingGroup> createWeightingGroups(double howMany, Edges edges) {
		double xlength = edges.max.x-edges.min.x;
		double ylength = edges.max.y-edges.min.y;

		int groupLength = (int)Math.floor(Math.max(xlength, ylength)/Math.ceil(Math.sqrt(howMany)));
		HashMap<String, DistanceWeightingGroup> map = new HashMap<>();
		
		for (Point p : edges.edgePoints.values()) {
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
