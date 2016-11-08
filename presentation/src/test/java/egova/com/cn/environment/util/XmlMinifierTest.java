package egova.com.cn.environment.util;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class XmlMinifierTest {
    @Test
    public void should_minify_xml_file_with_multi_lines() throws Exception {
        File file = new File(getClass().getResource("/xml/formated_xml.xml").getFile());

        String origin = FileUtils.readFileToString(file, "UTF-8");

        String minify = XmlMinifier.minify(origin);
        String expected = "<?xml version='1.0' encoding='gb2312'?><request><username name='68' /></request>";
        assertThat(minify, is(expected));
    }
}
