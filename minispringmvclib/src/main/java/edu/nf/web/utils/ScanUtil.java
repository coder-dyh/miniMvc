package edu.nf.web.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * class扫描工具
 * Created by wangl on 2017/7/6.
 */
public class ScanUtil {

	private static List<String> classNames = new ArrayList<String>();

	private final static String DEFAULT_PATH = "";

	/**
	 * 获取包下以及子包中所有的完整类名
	 *
	 * @return 所有的完整类名
	 */
	public static List<String> scanPackage() {
		URL url = Thread.currentThread().getContextClassLoader().getResource(DEFAULT_PATH);
		if (url != null) {
			scanPackage(url.getPath(), DEFAULT_PATH);
		}
		return classNames;
	}

	/**
	 * 从项目文件获取某包下所有类
	 *
	 * @param filePath 文件目录
	 * @param packageName 包名
	 */
	private static void scanPackage(String filePath, String packageName) {
		File[] files = new File(filePath).listFiles();
		packageName = packageName + ".";
		for (File childFile : files) {
			if (childFile.isDirectory()) {
				scanPackage(childFile.getPath(), packageName + childFile.getName());
			} else {
				String fileName = childFile.getName();
				if (fileName.endsWith(".class")) {
					if(packageName.charAt(0) == '.'){
						packageName = packageName.substring(1, packageName.length());
					}
					String className = packageName + fileName.replace(".class", "");
					classNames.add(className);
				}
			}
		}
	}


	public static void main(String[] args) throws Exception {
		int i = 0;
		List<String> classNames = scanPackage();
		for (String className : classNames) {
			i++;
			System.out.println(className);
		}
		System.out.println(i);
	}

}
