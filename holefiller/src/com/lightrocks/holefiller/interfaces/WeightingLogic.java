package com.lightrocks.holefiller.interfaces;

import java.util.List;

import com.lightrocks.holefiller.model.Edges;
import com.lightrocks.holefiller.model.Point;

public interface WeightingLogic<T extends WeightingArgs> {
	void initArgs(T weightingArgs);
	double weight(Point a, Point b);
	List<WeightingGroup> createWeightingGroups(double howMany, Edges edges);
}
