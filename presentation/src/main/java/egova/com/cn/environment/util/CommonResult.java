/*
 * CommonResult.java		20100705
 * 
 * Copyright (c) 2010 北京数字政通科技股份有限公司
 * 版权所有
 *
 * 修改标识：史先方20100705
 * 修改描述：创建
 */
package egova.com.cn.environment.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调用WebService返回数据类型，包含三个字段.
 * 
 * @version	0.1	20100705
 * @author	史先方
 * @param T 暂存数据的类型，一般不用（默认为Object类型）
 * 
 */
public class CommonResult extends ProcedureResult<Map<String,String>>{
	/** 日志TAG, 便于查找. */
	private static final String TAG = "[CommonResult]";
	
	/** 网络错误代码. */
	public static final int CONNECTION_ERROR = BaseErrorCode.CONNECTION_ERROR;
	/** 网络错误描述. */
	public static final String CONNECTION_ERROR_MSG = "网络连接错误,请检查相关设置是否有误。";
	/** 请求成功代码. */
	public static final int CODE_SUCCESS = BaseErrorCode.SUCCESS;
	/** 请求失败代码. */
	public static final int CODE_FAIL = BaseErrorCode.FAIL;
	
	/** 结果集中数据的key */
	public static final String KEY_DATA = "data";
	
	/** 结果集中是否通过的Key. */
	public static final String KEY_PASS = "bPass";
	
	/** 结果集中错误代码的Key. */
	public static final String KEY_CODE = "nCode";
	
	/** 结果集中错误原因的Key. */
	public static final String KEY_REASON = "strReason";
	
	/** 结果描述字符串. */
	private String resultStr = "";
	
	
	
	/** 数据   智信 v14使用，取自 {@link ResultInfo#data}*/
	private Map<String, Object> data = new HashMap<String, Object>();
	
	
	public CommonResult() {
	}

	/**
	 * 设置结果.
	 * @param resultStr 结果字符串,XML形式
	 */
	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}

	/**
	 * 返回结果.
	 * @return 结果字符串,XML形式
	 */
	public String getResultStr() {
		return this.resultStr;
	}
	
	public <T> List<T> getDataList(IMapProcessor<T> mp){
		return convert(getDataList(), mp);
	}
	
	
	public  MutiList getDataList(IMutiMapIProcessor processor){
		return convert(getDataListMaps(), processor);
	}
	
	
	@Override
	public String toString() {
		return "errorCode:[" + getErrorCode() + "]\nerrorDesc:[" + getErrorDesc() + "]\nresultStr"
			+ (resultStr.length() > 100 ? resultStr.substring(0, 100) : resultStr) + "]";
	}
	
	
	/**
	 * v14使用
	 * @return
	 */
	public Map<String, Object> getData() {
		return data;
	}
	/**
	 * v14使用
	 * @return
	 */
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	/**
	 * v14使用
	 * @return
	 */
	public Object getData(String key) {
		return data.get(key);
	}
	
	
	
	
}
