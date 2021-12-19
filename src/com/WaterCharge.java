package com;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author CRR
 */
public class WaterCharge {
    private static final String START_DATE = "2021/11/7";
    private static final String END_DATE = "2021/12/8";
    private static final int MS_TO_DAY = 1000 * 60 * 60 * 24;
    private static final double COST = 37.19;

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    private static final Map<String, List<String>> ROOM_MEMBER = new LinkedHashMap<>();

    static {
        ROOM_MEMBER.put("A", Arrays.asList("2021/12/1", END_DATE));
        ROOM_MEMBER.put("B", Arrays.asList(START_DATE, END_DATE));
        ROOM_MEMBER.put("C", Arrays.asList(START_DATE, END_DATE));
//        roomMember.put("D", Arrays.asList(startDate, "2021/10/18"));
    }

    public static void main(String[] args) {
        HashMap<String, Long> map = new HashMap<>(ROOM_MEMBER.size());
        long total = summary(map);
        for(String room : map.keySet()) {
            Long days = map.get(room);
            System.out.println("房间" + room + "需要缴电费：" + WaterCharge.COST * days / total);
        }
    }

    private static long summary(HashMap<String, Long> map) {
        long total = 0;
        for(Map.Entry<String, List<String>> next : ROOM_MEMBER.entrySet()) {
            List<String> date = next.getValue();
            String start = date.get(0);
            String end = date.get(1);
            long interval = days(start, end);
            map.put(next.getKey(), interval);
            total += interval;
        }
        return total;
    }

    /**
     * 计算两个日期之间的间隔天数
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 长整型天数
     */
    private static long days(String startDate, String endDate) {
        long interval = 0;
        try {
            long start = FORMAT.parse(startDate).getTime();
            long end   = FORMAT.parse(endDate).getTime();
            interval = (end - start) / MS_TO_DAY;
        } catch (ParseException e) {
            System.out.println("请输入yyyy/MM/dd格式的日期");
        }
        return interval;
    }
}
