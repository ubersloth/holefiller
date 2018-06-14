package com.lightrocks.holefiller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ArgsMapper {
	public final Map<String, String> map;
	
	public ArgsMapper(String[] args) {
		Map<String, String> argsMap = new HashMap<>();
		
		String key = null;
		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("-")) {
				key = args[i].replaceFirst("-", "");
				argsMap.put(key, "");
			}
			else if (key != null) {
				argsMap.put(key, argsMap.get(key) + args[i] + " ");
			}
		}
		
		argsMap.forEach((k,v)->{
			argsMap.put(k, v.trim());
		});
		
		map = Collections.unmodifiableMap(argsMap);
	}
}
