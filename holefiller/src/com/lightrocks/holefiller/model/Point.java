package com.lightrocks.holefiller.model;

import java.util.Objects;

public class Point {
	public int x;
	public int y;
	public double value;
	
	public Point() {
		this(0, 0);
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Point(int x, int y, double value) {
		this(x, y);
		this.value = value;
	}
	
	public double distance(Point other) {
		return Math.sqrt(Math.pow(x-other.x, 2) + Math.pow(y-other.y, 2));
	}
	
	@Override
	public boolean equals(Object obj) {
		// Wish I was writing Kotlin :)
		if (obj instanceof Point) {
			Point other = (Point)obj;
			return other.x == x && other.y == y;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
