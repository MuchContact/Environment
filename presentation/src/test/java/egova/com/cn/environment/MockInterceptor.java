package egova.com.cn.environment;

import android.support.annotation.NonNull;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MockInterceptor implements Interceptor {
    private final Response.Builder builder;
    private File[] responseFiles;
    private int index = 0;

    public MockInterceptor(Response.Builder builder, File... fileFullPath) {
        this.responseFiles = fileFullPath;
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
     * 每次请求返回值不同,第n次网络请求返回responseFiles中第n或者最后一个文件的内容
     * @return
     */
    private String createResponseBody() throws IOException {
        File responseFile;
        if (index < responseFiles.length)
            responseFile = responseFiles[index++];
        else
            responseFile = responseFiles[responseFiles.length-1];
        String encodedBody = StringEscapeUtils.escapeXml11(FileUtils.readFileToString(responseFile, "UTF-8"));
        String soapWrapper = FileUtils.readFileToString(getTemplateFileForSoapResponse(), "UTF-8");
        return String.format(soapWrapper, encodedBody);
    }

    @NonNull
    private File getTemplateFileForSoapResponse() {
        return new File(getClass().getResource("/xml/soap_response_wrapper.xml").getFile());
    }

}
