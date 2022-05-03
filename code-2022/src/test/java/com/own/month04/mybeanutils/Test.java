package com.own.month04.mybeanutils;

import java.util.List;
import java.util.Map;

/**
 * beanutils 测试工具类
 */
public class Test {
    public static void main(String[] args) {
        From from = new From("a", 1, true, Map.of("1", "1", "2", "2"));
        To to = new To("to", "to", 2, false, Map.of("3", "3", "4", "4"));
        CopyBean.shallowCopy(from, to, List.of("isGood", "name"));
        System.out.println(to);
    }

    static class From {
        public From(String name, int index, boolean isGood, Map<String, String> map) {
            this.name = name;
            this.index = index;
            this.isGood = isGood;
            this.map = map;
        }

        private String name;
        private int index;
        private boolean isGood;
        private Map<String, String> map;

        @Override
        public String toString() {
            return "From{" +
                    "name='" + name + '\'' +
                    ", index=" + index +
                    ", isGood=" + isGood +
                    ", map=" + map +
                    '}';
        }
    }

    static class To {
        public To(String str, String name, int index, boolean isGood, Map<String, String> map) {
            this.str = str;
            this.name = name;
            this.index = index;
            this.isGood = isGood;
            this.map = map;
        }

        private String str;
        private String name;
        private int index;
        private boolean isGood;
        private Map<String, String> map;

        @Override
        public String toString() {
            return "To{" +
                    "str='" + str + '\'' +
                    ", name='" + name + '\'' +
                    ", index=" + index +
                    ", isGood=" + isGood +
                    ", map=" + map +
                    '}';
        }
    }
}
