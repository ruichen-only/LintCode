package com;

import java.util.*;

public class BankSystem {
    private Map<Integer, Integer> accountMap          = new HashMap<>();
    private Map<Integer, Set<Long>> tradRecordMap     = new HashMap<>();
    private Map<Integer, Map<Long, Integer>> countMap = new HashMap<>();

    public BankSystem() {
        // Write your code here
    }

    /**
     * @param id: user account id
     * @param amount: the number of bank deposits
     * @param timestamp: the data of bank transaction
     * @return: nothing
     */
    public void deposite(int id, int amount, long timestamp) {
        Set<Long> record = tradRecordMap.get(id);
        if(record == null) {
            record = new LinkedHashSet<>();
            tradRecordMap.put(id, record);
            countMap.put(id, new HashMap<>());
            accountMap.put(id, 0);
        }
        record.add(timestamp);
        int add = accountMap.get(id) + amount;
        countMap.get(id).put(timestamp, add);
        accountMap.put(id, add);
    }

    /**
     * @param id: user account id
     * @param amount : the number of bank withdraw
     * @param timestamp: the data of bank transaction
     * @return: if user account can not withdraw the number of amount,return false. else return true
     */
    public boolean withdraw(int id, int amount, long timestamp) {
        Integer account = accountMap.get(id);
        if(account == null || account < amount) {
            return false;
        } else {
            account -= amount;
            accountMap.put(id, account);
            countMap.get(id).put(timestamp, account);
            Set<Long> record = tradRecordMap.get(id);
            record.add(timestamp);
        }
        return true;
    }

    /**
     * @param id: user account id
     * @param startTime: start time
     * @param endTime: end time
     * @return: need return two numbers,the first one is start time account balance,the second is end time account balance
     */
    public int[] check(int id, long startTime, long endTime) {
        Set<Long> record = tradRecordMap.get(id);
        if(record == null) return new int[0];

        int[] res = new int[]{0, 0};
        Map<Long, Integer> map = countMap.get(id);
        for (; startTime >= 0; startTime--) {
            if(map.get(startTime) != null) {
                res[0] = map.get(startTime);
                break;
            }
        }
        for (; endTime >= 0; endTime--) {
            if(map.get(endTime) != null) {
                res[1] = map.get(endTime);
                break;
            }
        }
        return res;
    }
}
