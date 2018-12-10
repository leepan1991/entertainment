package cn.innovativest.entertainment.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 集合操作工具类
 */
public class CollectionUtils {
    /**
     * Don't let anyone instantiate this class.
     */
    private CollectionUtils() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * 判断集合是否为null或者0个元素
     *
     * @param c
     * @return
     */
    public static boolean isEmpty(Collection c) {
        return (c == null || c.isEmpty());
    }

    /**
     * 两个集合并集
     */
    public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
        List<T> result = new ArrayList<T>(a);
        result.addAll(b);
        return result;
    }

    /**
     * 两个集合交集
     */
    public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
        List<T> list = new ArrayList<T>();

        for (T element : a) {
            if (b.contains(element)) {
                list.add(element);
            }
        }
        return list;
    }

    /**
     * 去除两个集合相同的部分
     */
    public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
        List<T> list = new ArrayList<T>(a);
        for (T element : b) {
            list.remove(element);
        }
        return list;
    }
}
