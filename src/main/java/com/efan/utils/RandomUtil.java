package com.efan.utils;

import java.util.*;

public class RandomUtil {
    private static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERCHAR = "0123456789";

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    private static String generateMixString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(LETTERCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    /**
     * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    /**
     * 生成一个定长的纯0字符串
     *
     * @param length
     *            字符串长度
     * @return 纯0字符串
     */
    private static String generateZeroString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num
     *            数字
     * @param fixdlenth
     *            字符串长度
     * @return 定长的字符串
     */
    public static String toFixdLengthString(long num, int fixdlenth) {
        StringBuilder sb = new StringBuilder();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
                    + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * 每次生成的len位数都不相同

     */
    public static int getNotSimple(int[] param, int len) {
        Random rand = new Random();
        for (int i = param.length; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = param[index];
            param[index] = param[i - 1];
            param[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < len; i++) {
            result = result * 10 + param[i];
        }
        return result;
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
     */
    public static String sign(Map<String, String> map,String key) {
        //排序
        String sort=sortParameters(map);
        //拼接API秘钥
        sort=sort+"&key="+key;
        //System.out.println(sort);
        //MD5加密
        String sign= Md5Utils.stringMD5(sort).toUpperCase();
        return sign;
    }

    /**
     * 对参数列表进行排序，并拼接key=value&key=value形式
     * @param map 集合
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
}
