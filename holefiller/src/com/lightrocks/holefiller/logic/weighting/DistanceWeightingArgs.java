package com.lightrocks.holefiller.logic.weighting;

import com.lightrocks.holefiller.ArgsMapper;
import com.lightrocks.holefiller.interfaces.WeightingArgs;

public class DistanceWeightingArgs implements WeightingArgs {
	public double factor = 2D;
	public double safeDivisor = 1e-12;
	
	public void parseArgs(ArgsMapper mapper) {
		if (mapper.map.containsKey("factor")) {
			factor = Double.valueOf(mapper.map.get("factor"));
		}
		
		if (mapper.map.containsKey("safedivisor")) {
			safeDivisor = Double.valueOf(mapper.map.get("safedivisor"));
		}
	}
}
