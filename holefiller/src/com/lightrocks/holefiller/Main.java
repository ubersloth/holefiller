package com.lightrocks.holefiller;

import java.io.IOException;

import com.lightrocks.holefiller.interfaces.HoleFiller;
import com.lightrocks.holefiller.logic.NaiveBoundryFinder;
import com.lightrocks.holefiller.logic.fillers.NaiveHoleFiller;
import com.lightrocks.holefiller.logic.weighting.DistanceWeightingArgs;
import com.lightrocks.holefiller.logic.weighting.DistanceWeightingLogic;
import com.lightrocks.holefiller.model.Boundry;

public class Main {

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		ArgsMapper argsMapper = new ArgsMapper(args);
		
		if (!argsMapper.map.containsKey(Consts.IMAGE_ARG) || argsMapper.map.get(Consts.IMAGE_ARG).isEmpty()) {
			System.out.println(Consts.INPUT_IMAGE_REQUIRED);
			System.out.println(Consts.USAGE_EXAMPLE);
			return;			
		}
		
		HoleFiller holeFiller = null;
		
		// Load hole filler dynamically
		if (!argsMapper.map.containsKey(Consts.HOLE_FILLER_ARG)) {
			holeFiller = new NaiveHoleFiller();
		}
		else {
			holeFiller = (HoleFiller)Class.forName(NaiveHoleFiller.class.getPackage().getName() + "." +
					argsMapper.map.get(Consts.HOLE_FILLER_ARG)).newInstance();
		}

		String filename = argsMapper.map.get(Consts.IMAGE_ARG);
		ImageOperations imgop = new ImageOperations();
		double image[][] = imgop.load(filename);
				
		DistanceWeightingArgs weightingArgs = new DistanceWeightingArgs();
		weightingArgs.parseArgs(argsMapper);
		DistanceWeightingLogic weightingLogic = new DistanceWeightingLogic();
		weightingLogic.initArgs(weightingArgs);
		
		NaiveBoundryFinder nef = new NaiveBoundryFinder();
		Boundry boundry = nef.findBoundry(image);	
		
		holeFiller.fillHole(image, boundry, weightingLogic);
		
		imgop.save(image, createTargetFilename(filename, argsMapper));
	}
	
	public static String createTargetFilename(String baseName, ArgsMapper argsMapper) {
		if (argsMapper.map.containsKey(Consts.OUTPUT_ARG) && !argsMapper.map.get(Consts.OUTPUT_ARG).isEmpty()) {
			return argsMapper.map.get(Consts.OUTPUT_ARG);
		}
		String prefix = baseName.substring(0, baseName.lastIndexOf("."));
		String argString = String.join("-", argsMapper.map.values().toArray(new String[]{})).replace(baseName + "-", "").replace(baseName, "");
		return prefix + "-" + argString + "-" + System.currentTimeMillis() + ".png";		
	}		
}
