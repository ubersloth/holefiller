package com.lightrocks.holefiller.interfaces;

import com.lightrocks.holefiller.model.Boundry;

public interface HoleFiller {

	void fillHole(double[][] image, Boundry edges, WeightingLogic<?> weightingLogic);

}