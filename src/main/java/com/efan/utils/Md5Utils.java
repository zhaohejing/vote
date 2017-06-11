package com.efan.utils;


import java.io.FileInputStream;
import java.io.IOException;
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

    protected static char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
    protected static MessageDigest messageDigest = null;

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
     * @param input
     * @return
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
     * @param map 不包含空字符串的map
     * @return
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


    /**
     *
     * <p>Title: JSON-XML转换工具</p>
     * <p>desc:
     * <p>Copyright: Copyright(c)Gb 2012</p>
     * @author http://www.ij2ee.com
     * @time 上午8:20:40
     * @version 1.0
     * @since
     */


    /**
     * 对参数列表进行排序，并拼接key=value&key=value形式
     * @param map
     * @return
     */
    private static String sortParameters(Map<String, String> map) {
        Set<String> keys = map.keySet();
        List<String> paramsBuf = new ArrayList<String>();
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
     * @param map
     * @param key
     * @return
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
     * @param data
     * @return
     */
    private static List<String> sort(List<String> data) {
        Collections.sort(data, new Comparator<String>() {
            public int compare(String obj1, String obj2) {
                return obj1.compareTo(obj2);
            }
        });
        return data;
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
     * @param inputFile
     * @return
     * @throws IOException
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
     * @param arr
     * @return
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
    private static boolean IsNumeric(String str) {
        if (str.matches("\\d *")) {
            return true;
        } else {
            return false;
        }
    }
    public static String getUuid() {
               String s =  UUID.randomUUID().toString();
               // 去掉"-"符号
               return s.replace("-", "");
           }
}
