package com.lightrocks.holefiller.model;

import java.util.HashMap;
import java.util.Map;

public class Boundry {
	public final Point min = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
	public final Point max = new Point();
	public final Map<Point, Point> points = new HashMap<>();
}
