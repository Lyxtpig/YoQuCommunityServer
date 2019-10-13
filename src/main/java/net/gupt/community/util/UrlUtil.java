package net.gupt.community.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * ClassName  UrlUtil <br/>
 * Description  Url工具类<br/>
 *
 * @author Administrator
 * @version 1.0
 * @date 2019/10/1313:22<br/>
 * @since JDK 1.8
 */
@Slf4j
public class UrlUtil {
    /**
     * URL解码并每个字符分割然后格式化
     *
     * @param str      字符串
     * @param charset  字符编码
     * @param splitter 分割符
     * @return String
     */
    public static String deCodeWithRegexp(String str, String charset, String splitter) {
        StringBuilder builder = new StringBuilder();
        if (!str.trim().isEmpty()) {
            try {
                //对传进来的字符串进行同一编码，并转换为字符数组
                String content = URLDecoder.decode(str, charset);
                char[] chars = content.toCharArray();
                //在每个字符后加一条分割符
                for (char character : chars
                ) {
                    builder.append(character).append(splitter);
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return builder.substring(0, builder.lastIndexOf(splitter));
    }
}