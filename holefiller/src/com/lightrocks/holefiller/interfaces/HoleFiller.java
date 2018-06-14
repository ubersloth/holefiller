package com.lightrocks.holefiller.interfaces;

import com.lightrocks.holefiller.model.Edges;

public interface HoleFiller {

	void fillHole(double[][] image, Edges edges, WeightingLogic<?> weightingLogic);

}