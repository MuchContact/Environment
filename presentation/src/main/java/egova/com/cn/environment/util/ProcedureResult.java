package egova.com.cn.environment.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 服务端存储过程执行结果
 * 
 * @author 胡威
 * @date 2013-10-15
 */
public class ProcedureResult<T> {
	/**
	 * 错误码　
	 */
	private int errorCode;
	/**
	 * 错误描述
	 */
	private String errorDesc;
	/**
	 * 数据列表
	 */
	private List<T> dataList;
	
	
	/**
	 * 数据列表映射
	 */
	private Map<String,List<T>> dataListMaps ;

	

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public boolean isSuccess() {
		return errorCode == CommonResult.CODE_SUCCESS;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
	public Map<String, List<T>> getDataListMaps() {
		return dataListMaps;
	}

	public void setDataListMaps(Map<String, List<T>> dataListMaps) {
		this.dataListMaps = dataListMaps;
	}






	public static final IMapProcessor<Map<String, String>> BASIC_MAP_PROCESSOR = new IMapProcessor<Map<String, String>>() {
		@Override
		public Map<String, String> convert(Map<String, String> map) {
			return map;
		}
	};
	
	public static <T> List<T> convert(List<Map<String, String>> listMap, IMapProcessor<T> processor) {
		int size = null == listMap ? 0 : listMap.size();
		ArrayList<T> list = new ArrayList<T>(size);
		if (size > 0 && null != processor) {
			try {
				for (Map<String, String> map : listMap) {
					T t = processor.convert(map);
					if (null != t) {
						list.add(t);
					}
				}
			} catch (Exception e) {
				Log.e("ProcedureResult", "[convert]--转换错误", e);
			}

		}
		return list;
		
	}
	
	 static MutiList convert(final Map<String,List<Map<String, String>>> mapListMap, final IMutiMapIProcessor processor) {
		
		 return new MutiList() {
			
			@SuppressWarnings("unchecked")
			@Override
			public <T> List<T> getDataList(String keyWord) {
				
				List<?> ss = convert(mapListMap.get(keyWord), processor.getMapProcessor(keyWord) );
				
				List<T> res = new ArrayList<T>(ss.size());
				for (Object object : ss) {
					res.add((T) object);
				}
				
				return res;
				
				
			}
		};
	}
	
	
	
	
	
	public static <T> ProcedureResult<T> convert(ProcedureResult<Map<String,String>> mapResult, IMapProcessor<T> processor){
		ProcedureResult<T> result = new ProcedureResult<T>();
		result.setErrorCode(mapResult.getErrorCode());
		result.setErrorDesc(mapResult.getErrorDesc());		
		result.setDataList(convert(mapResult.getDataList(),processor));
		return result;
		
	}
}