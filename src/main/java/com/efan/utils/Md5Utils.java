package com.efan.utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Md5
 * 提供对字符串的md5-->stringMD5
 * 提供对文件的Md5-->fileMD5
 *
 * 对于大文件，可以使用DigestInputStream
 */
public class Md5Utils {

    private static char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
    private static MessageDigest messageDigest = null;

    static{
        try{
            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            messageDigest = MessageDigest.getInstance("MD5");
        }catch (NoSuchAlgorithmException e) {
            System.err.println(Md5Utils.class.getName()+"初始化失败，MessageDigest不支持MD5Util.");
            e.printStackTrace();
        }
    }
    /**
     * 字符串的md5加密
     */
    public static String stringMD5(String input) {
        // 输入的字符串转换成字节数组
        byte[] inputByteArray = input.getBytes();
        // inputByteArray是输入字符串转换得到的字节数组
        messageDigest.update(inputByteArray);
        // 转换并返回结果，也是字节数组，包含16个元素
        byte[] resultByteArray = messageDigest.digest();
        // 字符数组转换成字符串返回
        return bufferToHex(resultByteArray);
    }
    /**
     * 生成签名sign
     * 第一步：非空参数值的参数按照参数名ASCII码从小到大排序，按照键值对的形式。生成字符串StringA
     * stringA="appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA";
     * 第二部：拼接API密钥，这里的秘钥是微信商户平台的秘钥，是自己设置的，不是公众号的秘钥
     * stringSignTemp="stringA&key=192006250b4c09247ec02edce69f6a2d"
     * 第三部：MD5加密
     * sign=MD5(stringSignTemp).toUpperCase()="9A0A8659F005D6984697E2CA0A9CF3B7"
     *

     */
    public static String sign(Map<String, String> map,String key) {
        //排序
        String sort=sortParameters(map);
        //拼接API秘钥
        sort=sort+"&key="+key;
        //System.out.println(sort);

        // 输入的字符串转换成字节数组
        byte[] inputByteArray = sort.getBytes();
        // inputByteArray是输入字符串转换得到的字节数组
        messageDigest.update(inputByteArray);
        // 转换并返回结果，也是字节数组，包含16个元素
        byte[] resultByteArray = messageDigest.digest();
        // 字符数组转换成字符串返回
       String sign= bufferToHex(resultByteArray);

        return sign.toLowerCase();
    }
    public static String SHA1(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        try {
            //排序
            String sort=sortParameters(map);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(sort.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 对参数列表进行排序，并拼接key=value&key=value形式

     */
    /**
     * 获得值
     *
     * @return 值
     */

    private static String sortParameters(Map<String, String> map) {
        Set<String> keys = map.keySet();
        List<String> paramsBuf = new ArrayList<>();
        for (String k : keys) {
            paramsBuf.add((k + "=" + getParamString(map, k)));
        }
        // 对参数排序
        Collections.sort(paramsBuf);
        String result="";
        int count=paramsBuf.size();
        for(int i=0;i<count;i++){
            if(i<(count-1)){
                result+=paramsBuf.get(i)+"&";
            }else {
                result+=paramsBuf.get(i);
            }
        }
        return result;
    }
    /**
     * 返回key的值

     */
    private static String getParamString(Map map, String key) {
        String buf = "";
        if (map.get(key) instanceof String[]) {
            buf = ((String[]) map.get(key))[0];
        } else {
            buf = (String) map.get(key);
        }
        return buf;
    }
    /**
     * 字符串列表从大到小排序

     */
    private static List<String> sort(List<String> data) {
        Collections.sort(data, new Comparator<String>() {
            public int compare(String obj1, String obj2) {
                return obj1.compareTo(obj2);
            }
        });
        return data;
    }
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes
     *            the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    public static String encode(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {

        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }


    /**
     * 文件的md5加密

     */
    public static String fileMD5(String inputFile) throws IOException {
        // 缓冲区大小（这个可以抽出一个参数）
        int bufferSize = 256 * 1024;
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;
        try {
            // 使用DigestInputStream
            fileInputStream = new FileInputStream(inputFile);
            digestInputStream = new DigestInputStream(fileInputStream,messageDigest);
            // read的过程中进行MD5处理，直到读完文件
            byte[] buffer =new byte[bufferSize];
            while (digestInputStream.read(buffer) > 0);
            // 获取最终的MessageDigest
            messageDigest= digestInputStream.getMessageDigest();
            // 拿到结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 同样，把字节数组转换成字符串
            return bufferToHex(resultByteArray);
        } finally {
            try {
                digestInputStream.close();
            } catch (Exception e) {
            }
            try {
                fileInputStream.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Map转Xml

     */
    public static String MapToXml(Map<String, String> arr) {
        String xml = "<xml>";
        Iterator<Map.Entry<String, String>> iter = arr.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            String key = entry.getKey();
            String val = entry.getValue();
            if (IsNumeric(val)) {
                xml += "<" + key + ">" + val + "</" + key + ">";
            } else
                xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
        }
        xml += "</xml>";
        return xml;
    }
    public static String MapToXmlNoReg(Map<String, String> arr) {
        String xml = "<xml>";
        Iterator<Map.Entry<String, String>> iter = arr.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            String key = entry.getKey();
            String val = entry.getValue();

                xml += "<" + key + ">" + val + "</" + key + ">";

        }
        xml += "</xml>";
        return xml;
    }
    private static boolean IsNumeric(String str) {
        return str.matches("\\d*");
    }
    public static String getUuid() {
               String s =  UUID.randomUUID().toString();
               // 去掉"-"符号
               return s.replace("-", "");
           }
}
