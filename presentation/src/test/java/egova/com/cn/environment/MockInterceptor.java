package egova.com.cn.environment;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MockInterceptor implements Interceptor {
    private final Response.Builder builder;
    private File responseFile;

    public MockInterceptor(File fileFullPath, Response.Builder builder) {
        this.responseFile = fileFullPath;
        this.builder = builder;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String responseString = createResponseBody();

        return builder
                .request(chain.request())
                .body(ResponseBody.create(MediaType.parse("text/xml"), responseString.getBytes()))
                .build();
    }

    /**
     * 读文件获取json字符串，生成ResponseBody
     *
     * @return
     */
    private String createResponseBody() throws IOException {
        return getResponseString();
    }

    private String getResponseString() throws IOException {
        return FileUtils.readFileToString(responseFile, "UTF-8");
    }
}
