package edu.nf.web.utils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

//*******简化版本

/**
 * class扫描工具
 * Created by wangl on 2017/7/6.
 */

public class ScanUtil {

	private static List<String> classNames = new ArrayList<String>();

	private final static String DEFAULT_PATH = "";


	public static List<String> scanPackage() {
		URL url = Thread.currentThread().getContextClassLoader().getResource(DEFAULT_PATH);
		if (url != null) {
			scanPackage(url.getPath(), DEFAULT_PATH);
		}
		return classNames;
	}


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





/**
 * class扫描工具
 * Created by wangl on 2017/7/6.
 */
/*
public class ScanUtil {

    private static final Set<String> classNames = new HashSet<String>();

    */
/**
     * 获取指定包下以及子包中所有的类
     *
     * @param packageName 包名
     * @return 所有的完整类名
     *//*

    public static Set<String> scan(String packageName) {
        if(packageName == null){
            throw new RuntimeException("The path can not be null.");
        }
        String packagePath = packageName.replace(".", "/");
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> urls = loader.getResources(packagePath);
            while(urls.hasMoreElements()){
                URL url= urls.nextElement();
                if("file".equals(url.getProtocol())){
                    scanFromDir(url.getPath(), packageName);
                }
                if("jar".equals(url.getProtocol())){
                    JarURLConnection connection = (JarURLConnection)url.openConnection();
                    scanFromJar(connection.getJarFile());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Resolve path error.", e);
        }

        return classNames;
    }

    */
/**
     * 从项目文件获取某包下所有类
     *
     * @param filePath 文件目录
     * @param packageName 包名
     *//*

    private static void scanFromDir(String filePath, String packageName) {
        File[] files = new File(filePath).listFiles();
        packageName = packageName + ".";
        for (File childFile : files) {
            if (childFile.isDirectory()) {
                scanFromDir(childFile.getPath(), packageName + childFile.getName());
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

    */
/**
     * 扫描jar文件
     * @param jarFile
     *//*

    private static void scanFromJar(JarFile jarFile) {
        Enumeration<JarEntry> files = jarFile.entries();
        while (files.hasMoreElements()) {
            JarEntry entry = files.nextElement();
            if (entry.getName().endsWith(".class")){
                String className = entry.getName().replace("/", ".").replace(".class", "");
                classNames.add(className);
            }
        }
    }
}
*/

