package com.lightrocks.holefiller;

// Making this static is not a best practice for mocking, ideally we'd use a single instance with injection
public class Consts {
	public static final double INVALID = -1;
	
	public static final String HOLE_FILLER_ARG = "holefiller";
	public static final String IMAGE_ARG = "image";
	public static final String OUTPUT_ARG = "output";
	
	public static final String INPUT_IMAGE_REQUIRED = "Input image required";
	public static final String USAGE_EXAMPLE = "Usage example: -image test.png [-holefiller NaiveHoleFiller -factor 2 -safedivisor 0.000000000001]";
}
