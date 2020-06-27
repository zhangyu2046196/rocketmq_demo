package com.youyuan.rocketmq.base.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

import java.text.DecimalFormat;

/**
 * 类名称：GenerateCodeUtil <br>
 * 类描述：生成编码工具类 <br>
 *
 * @author zhangyu
 * @version 1.0.0
 * @date 创建时间：2020/4/30 14:38<br>
 */
public class GenerateCodeUtil {

    private static Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    /**
     * 方法名: getCode  <br>
     * 方法描述: 按照指定条件生成编码 <br>
     *
     * @param sourceCode 前一个编码
     * @param codePrefix 编码前缀
     * @param length     编码长度 不包含前缀
     * @param step       步长
     * @return {@link }
     * @date 创建时间: 2020/4/30 14:18 <br>
     * @author zhangyu
     */
    public static String getCode(String sourceCode, String codePrefix, Integer length, Integer step) {
        Integer splitPoint = 0;
        if (StrUtil.isNotEmpty(codePrefix)) {
            splitPoint = codePrefix.length();
        }
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sBuilder.append("0");
        }
        if (StrUtil.isEmpty(sourceCode)) {
            sourceCode = codePrefix + sBuilder.toString();
        }
        String numStr = sourceCode.substring(splitPoint);
        DecimalFormat countFormat = new DecimalFormat(sBuilder.toString());
        return codePrefix + countFormat.format(Integer.parseInt(numStr) + step);
    }

    /**
     * 方法名: getCode  <br>
     * 方法描述: 按照指定条件生成编码 <br>
     *
     * @param sourceCode 前一个编码
     * @param hotelCode  酒店编码
     * @param codePrefix 编码前缀
     * @param length     编码长度 不包含前缀
     * @param step       步长
     * @return {@link }
     * @date 创建时间: 2020/4/30 14:18 <br>
     * @author zhangyu
     */
    public static String getCode(String sourceCode, String hotelCode, String codePrefix, Integer length, Integer step) {
        Integer splitPoint = 0;
        StringBuilder stringBuilder = new StringBuilder();
        if (StrUtil.isNotEmpty(codePrefix)) {
            stringBuilder.append(codePrefix);
        }
        if (StrUtil.isNotEmpty(hotelCode)) {
            stringBuilder.append(hotelCode);
        }
        String prefixHotleCode = stringBuilder.toString();
        if (StrUtil.isNotEmpty(prefixHotleCode)) {
            splitPoint = prefixHotleCode.length();
        }
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sBuilder.append("0");
        }
        if (StrUtil.isEmpty(sourceCode)) {
            sourceCode = codePrefix + hotelCode + sBuilder.toString();
        }
        String numStr = sourceCode.substring(splitPoint);
        DecimalFormat countFormat = new DecimalFormat(sBuilder.toString());
        return codePrefix + hotelCode + countFormat.format(Integer.parseInt(numStr) + step);
    }

    /**
    * 方法名:  generateCode <br>
    * 方法描述: 生成全局唯一id <br>
    * 
    * @return {@link String 返回全局唯一id}
    * @date 创建时间: 2020/5/11 11:40 <br>
    * @author zhangyu
    */
    public static String generateCode(){
        long id = snowflake.nextId();
        return String.valueOf(id);
    }

    public static void main(String[] args) {
//        String str = "WL2457897500019";
        String str = "";
        for (int i = 0; i < 20; i++) {
            str = getCode(str, "", "WL", 5, 1);
            System.out.println(str);
        }
    }

}
