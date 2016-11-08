package egova.com.cn.environment.login;

public interface LoginResponseCode {
    /**
     * 错误代码:用户名错误.
     */
    int ERR_CODE_USER_NAME = 1;
    /**
     * 错误代码:用户标识错误.
     */
    int ERR_CODE_USER_ID = 2;
    /**
     * 错误代码:密码错误.
     */
    int ERR_CODE_PASSWORD = 3;
    /**
     * 错误代码:其它错误.
     */
    int ERR_CODE_OTHER = -1;

}
