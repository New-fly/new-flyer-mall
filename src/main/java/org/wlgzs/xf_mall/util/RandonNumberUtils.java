package org.wlgzs.xf_mall.util;

import java.util.*;

/**
 * @author:胡亚星
 * @createTime 2018-04-21 17:24
 * @description:
 **/
public class RandonNumberUtils {
    /**
     * 随机生成字符 或 者数字
     * @return
     */
    private String getRandom(){
        String value = "";
        
        Random random = new Random();
        int gen = random.nextInt(2);
        String charOrNum = gen % 2 ==0 ? "char":"num";
        if ("char".equals(charOrNum)) {
            //字符
            int temp = random.nextInt(2)%2==0?65:97;
            int ascii = random.nextInt(26);
            value += (char)(ascii + temp);
        }else if ("num".equals(charOrNum)) {
            //是数字
            value += String.valueOf(random.nextInt(10));
        }
        return value;
    }

    
    /**     
     * @author 胡亚星
     * @date 2018/5/6 17:09  
     * @param   
     * @return   
     *@Description:生成6位随机数字
     */
    public String getNumber(int lenth){
        String value = "";
        Random random = new Random();
        for (int i = 0;i < lenth;i++){
            int gen = random.nextInt(5);
            value = value + gen;
        }
        return value;
    }
    
    /**
     * 随机生成字符串（包含字符和数字）
     * @param length 指定长度
     * @return 返回set
     */
    private Set<String> getStrAndNum(int length) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < length; i++) {
            String value = getRandom();
            set.add(value);
        }
        //若生成的字符串没达到指定长度 继续生成
        if (set.size() < length) {
            String value = getRandom();
            set.add(value);
        }
        return set;
    }

    /**
     * 存放在set中的字符组拼接成字符串
     * @param set
     * @return
     */
    private  String printSet(Set set){ //打印set的方法
        Iterator iterator = set.iterator();
        String value = "";
        while (iterator.hasNext()) {
            //String ele = (String) iterator.next();
            value += (String)iterator.next();
        }
        return value;
    }

    /**
     * 返回生成的随机字符串
     * @param length 指定随机字符串长度
     * @return 指定长度 大于零 返回指定长度随机字符，小于等于零 返回null
     */
    public String getRandonString(int length){
        String value= "";
        if (length > 0) {
            //如果返回的字符串小于指定长度 重新生成
            if (value.length() < length) {
                Set<String> store = getStrAndNum(length);
                value = printSet(store);
            }
            return value;
        }else{
            return value;
        }
    }
    /**
     * @author 阿杰
     * @param []
     * @return java.lang.String
     * @description 利用UUID取得订单号
     */
    public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();//生成UUID然后取它的hashCode值
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        //hashCode 以31为权，每一位为字符的ASCII值进行运算,
        // hashcode其实就是散列码，hashcode使用高效率的哈希算法来定位查找对象,尽量减少有同样的hash地址
        return machineId + String.format("%015d", hashCodeV);
    }
}
