package egova.com.cn.environment.util;

/**
 * 服务端基础错误码
 * @author hsy
 *
 */
public interface BaseErrorCode {
	/** 网络错误代码. */
	int CONNECTION_ERROR = -999;
	/** 请求成功代码. */
	int SUCCESS = 0;
	/** 请求失败代码. */
	int FAIL = -1;
}
