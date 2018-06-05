package org.wlgzs.xf_mall.util;

/**
 * @author:胡亚星
 * @createTime 2018-06-03 14:19
 * @description:
 **/
public class CheckImage {

    public boolean verifyImage(String fileName){
        String reg="(?i).+?\\.(jpg|gif|bmp|png)";
        return fileName.matches(reg);
    }

    public boolean verifyImages(String[] fileNames){
        String reg="(?i).+?\\.(jpg|gif|bmp|png)";
        for(int i = 0;i<fileNames.length;i++){
            if(!fileNames[i].matches(reg)){
                return false;
            }
        }
        return true;
    }
}
