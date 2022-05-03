package com.own.month04.mybeanutils;

import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * bean 复制工具类
 */
public class CopyBean {
    /**
     * 浅拷贝
     *
     * @param resource 源 bean
     * @param target 目标 bean
     * @param ignore 不参与复制的属性名
     */
    public static void shallowCopy(Object resource, Object target, List<String> ignore) {
        if (Objects.isNull(resource) || Objects.isNull(target)) {
            return;
        }
        
        Field[] resourceFields = resource.getClass().getDeclaredFields();
        Map<String, Field> map = new HashMap<>();

        Arrays.stream(target.getClass().getDeclaredFields()).forEach((item) -> {
            if (CollectionUtils.isEmpty(ignore) || !ignore.contains(item.getName())) {
                map.put(item.getName(), item);
            }
        });

        Arrays.stream(resourceFields).forEach((item) -> {
            if (map.containsKey(item.getName())) {
                Field targetField = map.get(item.getName());
                Object obj = null;

                // 确保属性是可以访问的
                boolean resourceAccessible = true;
                try {
                    obj = item.get(resource);
                } catch (IllegalAccessException e) {
                    resourceAccessible = false;
                    item.setAccessible(true);
                    try {
                        obj = item.get(resource);
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }

                boolean targetAccessible = true;
                try {
                    targetField.set(target, obj);
                } catch (IllegalAccessException e) {
                    targetAccessible = false;
                    targetField.setAccessible(true);
                    try {
                        targetField.set(target, obj);
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }

                item.setAccessible(resourceAccessible);
                targetField.setAccessible(targetAccessible);
            }
        });
    }
}

