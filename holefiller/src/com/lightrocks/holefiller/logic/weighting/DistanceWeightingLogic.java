package com.lightrocks.holefiller.logic.weighting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
		return 1 / (Math.pow(a.distance(b), args.factor)+args.safeDivisor);
	}
	
	@Override
	public List<Point> getMostInfluential(double limit, Point p, Edges edges) {
		int offset = (int)(Math.sqrt(limit)/2D);
		List<Point> influentialPoints = new LinkedList<>();
		
		for (int i = Math.max(edges.min.x, p.x-offset); i <= Math.min(edges.max.x, p.x+offset); i++) {
			for (int j = Math.max(edges.min.y, p.y-offset); j <= Math.min(edges.max.y, p.y+offset); j++) {
				Point candidate = new Point(i,j);
				if (edges.edgePoints.containsKey(candidate)) {
					influentialPoints.add(edges.edgePoints.get(candidate));
				} 
			}
		}
		
		return influentialPoints;
	}	

	@Override
	public List<WeightingGroup> createWeightingGroups(double limit, Edges edges) {
		double xlength = edges.max.x-edges.min.x;
		double ylength = edges.max.y-edges.min.y;

		int groupLength = (int)Math.floor(Math.max(xlength, ylength)/Math.floor(Math.sqrt(limit)));
		groupLength = Math.max(groupLength, 1);
		
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
