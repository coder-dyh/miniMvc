package edu.cj.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 读取struts.xml文件中的配置信息的类
 * 
 * @author david
 */
public class ConfigurationManager {

  	/**
	 * 常量的配置形式如下：
	 * <constant name="struts.action.extension" value="action" />
	 * @param name
	 *      常量的名字
	 * @return 如果存在符合条件的constant标签则返回该标签的value属性值，否则返回null
	 */
	public static String getConstant(String name) {
		Document doc = getDocument();
		// XPath语句，选取属性name值为name变量的值的constant标签
		String xpath = "//constant[@name='" + name + "']";
		Element con = (Element) doc.selectSingleNode(xpath);
		if (con != null) {
			return con.attributeValue("value");
		} else {
			return null;
		}
	}

	/**
	 * 读取struts.xml中所有interceptor标签的信息
	 * 
	 * @return 包含struts.xml中所有interceptor标签的信息的列表
	 */
	public static List<String> getInterceptors() {
		List<String> interceptors = null;
		Document doc = getDocument();
		// XPath语句，选取所有 interceptor子元素，而不管它们在文档中的位置。
		String xpath = "//interceptor";
		List<Element> nodes = doc.selectNodes(xpath);
		if (nodes != null && nodes.size() > 0) {
			interceptors = new ArrayList<String>();
			for (Element ele : nodes) {
				// 获得class属性的值
				String className = ele.attributeValue("class");
				interceptors.add(className);
			}
		}
		return interceptors;
	}

	/**
	 * 读取struts.xml中所有action标签的信息
	 * 
	 * @return 包含struts.xml中所有action标签的信息的映射，其中key为action的name
	 */
	public static Map<String, ActionConfig> getActions() {
		Map<String, ActionConfig> actionMap = null;
		Document doc = getDocument();
		// XPath语句，选取所有 action子元素，而不管它们在文档中的位置。
		String xpath = "//action";
		List<Element> nodes = doc.selectNodes(xpath);
		if (nodes != null && nodes.size() > 0) {
			actionMap = new HashMap<String, ActionConfig>();
			for (Element ele : nodes) {
				ActionConfig ac = new ActionConfig();
				ac.setName(ele.attributeValue("name"));
				ac.setClassName(ele.attributeValue("class"));
				String method = ele.attributeValue("method");
				// 判读是否有填写方法名，没有的话就默认为execute
				method = (method == null || method.trim().equals("")) ? "execute" : method;
				ac.setMethod(method);
				// 获取当前action标签下的所有result标签
				List<Element> results = ele.elements("result");
				for (Element res : results) {
					ac.getResults().put(res.attributeValue("name"), res.getText());
				}
				actionMap.put(ac.getName(), ac);
			}
		}
		return actionMap;

	}

	/**
	 * 加载配置文件
     * TODO: 此代码还有性能问题，因为会多次读取文件，并且没有关闭相关的资源。仅仅只是教学用，以后再改进。
	 *
	 * @return 返回配置文件对应的文档对象
	 */
	private static Document getDocument() {

    	// 创建解析器
		SAXReader reader = new SAXReader();
		// 加载配置文件，struts.xml文件必须放置在classpath下面
		InputStream is = ConfigurationManager.class.getResourceAsStream("/struts.xml");
		Document doc = null;
		try {
			doc = reader.read(is);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException("加载配置文件出错");
		}
		return doc;
	}
}
