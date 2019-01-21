package org.wlgzs.xf_mall.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @Auther: 阿杰
 * @Date: 2018/5/5 15:05
 * @Description:
 */
public class IdsUtil {
    public static long[] IdsUtils(String ids) {
        long[] Ids = new long[1];
        if (ids.contains(",")) {
            String[] str = ids.split(",");
            if (str.length != 0) {
                Ids = new long[str.length];
                for (int i = 0; i < Ids.length; i++) {
                    Ids[i] = Integer.valueOf(str[i]);
                }
            }
        }
        if (!ids.contains(",")) {
            Ids[0] = Long.parseLong(ids);
        }
        return Ids;
    }

    public static void writeFile(MultipartFile file, File saveFile) {
        try {
            if (!file.isEmpty()) {
                if (!saveFile.getParentFile().exists()) saveFile.getParentFile().mkdirs();
                FileOutputStream outputStream = new FileOutputStream(saveFile);
                BufferedOutputStream out = new BufferedOutputStream(outputStream);
                out.write(file.getBytes());
                out.flush();
                out.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //删除文件
    public boolean deleteFile(String url) {
        File file = new File("." + url);
        if (file.exists() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static void saveFile(String content, File saveFile) {
        try {
            if (!saveFile.getParentFile().exists()) saveFile.getParentFile().mkdirs();
            FileOutputStream outputStream = new FileOutputStream(saveFile);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "GBK"));
            writer.write(content);
            writer.flush();
            writer.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
