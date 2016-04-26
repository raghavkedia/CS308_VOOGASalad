package authoring.parser;

import java.util.Map;
import java.util.TreeMap;

public class GlobalParser {
	
	public static Map<String, String[]> pathParse(String path) {
		Map<String, String[]> map = new TreeMap<String, String[]>();
		String[] paths = path.split("_");
		for (String str : paths) {
			String[] pathInfo = str.split(":");
			String pathID = pathInfo[0];
			String[] curves = pathInfo[1].split(" ");
			map.put(pathID, curves);
		}
		
		return map;
	}
	
	public static String pathCompress(Map<String, String[]> path) {
		StringBuilder sb = new StringBuilder();
		for (String key : path.keySet()) {
			sb.append(key);
			sb.append(":");
			String[] curves = path.get(key);
			for (int i = 0; i < curves.length - 1; i++) {
				sb.append(curves[i]);
				sb.append(" ");
			}
			sb.append(curves[curves.length - 1]);
			sb.append("_");
		}
		sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}
	
	public static Map<String, String[]> spawnParse(String spawn) {
		Map<String, String[]> spawns = new TreeMap<String, String[]>();
		if (spawn.equals("0")) {
			return spawns;
		} else {
			String[] temp = spawn.split(",");
			for (String str : temp) {
				String[] info = str.split(":");
				String pathID = info[0];
				String[] spawnObjects = info[1].split("_");
				spawns.put(pathID, spawnObjects);
			}
			return spawns;
		}
	}
	
	public static String spawnCompress(Map<String, String> spawn) {
		
		return null;
	}
	
}
