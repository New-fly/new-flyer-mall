package org.wlgzs.xf_mall.util;

import java.io.*;

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
    public static void writerFile(String content,String filePath){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(content);
            bw.close();
            String json = "要写入的JSON字符串";
            String file = "D:\\1.txt";
            FileOutputStream writerStream = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
            writer.write(content);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
