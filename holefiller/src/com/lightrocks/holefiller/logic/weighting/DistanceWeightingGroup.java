package com.lightrocks.holefiller.logic.weighting;

import com.lightrocks.holefiller.interfaces.WeightingGroup;
import com.lightrocks.holefiller.model.Point;

public class DistanceWeightingGroup implements WeightingGroup {
	private int count = 0;
	private double xsum = 0;
	private double ysum = 0;
	private double valuesum = 0;
	
	public void add(Point p) {
		xsum += p.x;
		ysum += p.y;
		valuesum += p.value;
		count++;
	}
	
	@Override
	public Point getGroupRepresentative() {
		return new Point((int)(xsum/count), (int)(ysum/count), valuesum/count);
	}

	@Override
	public int getCount() {
		return count;
	}

}
