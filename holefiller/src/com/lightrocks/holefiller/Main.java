package com.lightrocks.holefiller;

import java.io.IOException;

import com.lightrocks.holefiller.interfaces.HoleFiller;
import com.lightrocks.holefiller.logic.NaiveEdgeFinder;
import com.lightrocks.holefiller.logic.fillers.NaiveHoleFiller;
import com.lightrocks.holefiller.logic.weighting.DistanceWeightingArgs;
import com.lightrocks.holefiller.logic.weighting.DistanceWeightingLogic;
import com.lightrocks.holefiller.model.Edges;

public class Main {

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		ArgsMapper argsMapper = new ArgsMapper(args);
		
		if (!argsMapper.map.containsKey(Consts.IMAGE_ARG) || argsMapper.map.get(Consts.IMAGE_ARG).isEmpty()) {
			System.out.println(Consts.INPUT_IMAGE_REQUIRED);
			System.out.println(Consts.USAGE_EXAMPLE);
			return;			
		}
		
		HoleFiller holeFiller = null;
		
		if (!argsMapper.map.containsKey(Consts.HOLE_FILLER_ARG)) {
			holeFiller = new NaiveHoleFiller();
		}
		else {
			holeFiller = (HoleFiller)Class.forName(NaiveHoleFiller.class.getPackage().getName() + "." +
					argsMapper.map.get(Consts.HOLE_FILLER_ARG)).newInstance();
		}

		String imageFile = argsMapper.map.get(Consts.IMAGE_ARG);
		ImageOpertaions imop = new ImageOpertaions();
		double image[][] = imop.load(imageFile);
				
		DistanceWeightingArgs weightingArgs = new DistanceWeightingArgs();
		weightingArgs.parseArgs(argsMapper);
		DistanceWeightingLogic weightingLogic = new DistanceWeightingLogic();
		weightingLogic.initArgs(weightingArgs);
		
		NaiveEdgeFinder nef = new NaiveEdgeFinder();
		Edges edges = nef.findEdges(image);	
		
		holeFiller.fillHole(image, edges, weightingLogic);
		
		imop.save(image, createTargetFilename(imageFile, argsMapper));
	}
	
	public static String createTargetFilename(String imageFile, ArgsMapper argsMapper) {
		String prefix = imageFile.substring(0, imageFile.lastIndexOf("."));
		String argString = String.join("-", argsMapper.map.values().toArray(new String[]{})).replace(imageFile + "-", "").replace(imageFile, "");
		return prefix + "-" + argString + "-" + System.currentTimeMillis() + ".png";		
	}		
}
