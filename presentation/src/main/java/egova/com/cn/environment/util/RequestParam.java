package egova.com.cn.environment.util;

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 请求参数的数据结构
 * @author 胡威
 * @date 2013-10-21
 */
public class RequestParam {
	public static final String SPLIT_CHAR = "#";
	public Map<String,Object> paramMap = new LinkedHashMap<String,Object>();
	public String functionName;
	
	//执行简单的存储过程流程使用到的参数，直接设置存储过程的名称
	public String procedureName;
	
	//方法名是否作为url请求路径的一部分，在智云系统中请求统计组的http接口时会用到
	public boolean isFuncNameAsUrlPath = false;
	
	/** 智云的方法名,只有智云才使用这个字段*/
	private String method;
	
	public int startNum;
	public int endNum;
	
	public RequestParam() {
		super();
	}

	
	
	
	public RequestParam(String method) {
		this();
		this.method = method;
	}
	
	
	public void putParam(String key, Object value) {
		if(null != key && null != value) {
			if(null == paramMap) {
				paramMap = new LinkedHashMap<String, Object>();
			}
			paramMap.put(key, value);
		}
	}
	
	
	
	
	
	
	
	public String getMethod() {
		return method;
	}




	public void setMethod(String method) {
		this.method = method;
	}




	public void putIntValue(String key, int value) {
		paramMap.put(key, value + SPLIT_CHAR + Types.INTEGER);
	}
	
	public void putStringValue(String key, String value) {
		paramMap.put(key, value + SPLIT_CHAR + Types.VARCHAR);
	}
	
	public void putDoubleValue(String key, double value) {
		paramMap.put(key, value + SPLIT_CHAR + Types.DOUBLE);
	}
	
	public void putFloatValue(String key, float value) {
		paramMap.put(key, value + SPLIT_CHAR + Types.FLOAT);
	}
}