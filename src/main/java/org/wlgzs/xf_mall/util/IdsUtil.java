package org.wlgzs.xf_mall.util;

/**
 * @Auther: 阿杰
 * @Date: 2018/5/5 15:05
 * @Description:
 */
public class IdsUtil {
    public static long[] IdsUtils(String ids){
        long[] Ids = new long[1];
        if(ids.contains(",")){
            String[] str = ids.split(",");
            if (str.length != 0) {
                Ids = new long[str.length];
                for (int i = 0; i < Ids.length; i++) {
                    Ids[i] = Integer.valueOf(str[i]);
                }
            }
        }
        if(!ids.contains(",")){
            Ids[0] = Long.parseLong(ids);
        }
        return Ids;
    }
}
