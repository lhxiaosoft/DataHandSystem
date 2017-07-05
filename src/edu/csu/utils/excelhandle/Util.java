package edu.csu.utils.excelhandle;

public class Util {
	/**
	 * 获得格式标记   如xls或xlsx
	 * 
	 * @param path
	 * @return
	 */
	public static String getPostfix(String path) {
		if (path == null || Common.EMPTY.equals(path.trim())) {
			return Common.EMPTY;
		}
		if (path.contains(Common.POINT)) {
			return path.substring(path.lastIndexOf(Common.POINT) + 1,
					path.length());
		}
		return Common.EMPTY;
	}
}
