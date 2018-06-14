package com.lightrocks.holefiller.interfaces;

import com.lightrocks.holefiller.model.Point;

public interface WeightingGroup {
	public Point getGroupRepresentative();
	public int getCount();
}
