package org.wlgzs.xf_mall.util;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/22 09:57
 * @Description:
 */
import org.wlgzs.xf_mall.entity.Result;

public class ResultUtil {
    public static Result success(String []object) {
        Result result = new Result();
        result.setErrno(0);
        result.setData(object);
        return result;
    }
    public static Result success() {
        return success(null);
    }

}
