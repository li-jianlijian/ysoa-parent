package com.ys.oa.stringUtil;

import com.sun.istack.internal.Nullable;

public class StringUtils {

    // 该方法与springboot提供的StringUtils的isEmpty一样
    // 防止调错包 于是自己建了个
    public static boolean isEmpty(@Nullable Object str) {
        return str == null || "".equals(str);
    }
}
