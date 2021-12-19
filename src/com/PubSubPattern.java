package com;

import java.util.*;

class PushNotification {
    public static void notify(int user_id, String the_message){};
};

public class PubSubPattern {
    private Map<String, Set<Integer>> channelMap = new HashMap<>();

    public PubSubPattern(){
        // Write your code here
    }

    /**
     * @param channel: the channel's name
     * @param user_id: the user who subscribes the channel
     * @return: nothing
     */
    public void subscribe(String channel, int user_id) {
        Set<Integer> users = channelMap.get(channel);
        if(users == null) {
            users = new HashSet<>();
            channelMap.put(channel, users);
        }
        users.add(user_id);
    }

    /**
     * @param channel: the channel's name
     * @param user_id: the user who unsubscribes the channel
     * @return: nothing
     */
    public void unsubscribe(String channel, int user_id) {
        Set<Integer> users = channelMap.get(channel);
        if(users == null) return;

        users.remove(user_id);
    }

    /**
     * @param channel: the channel's name
     * @param message: the message need to be delivered to the channel's subscribers
     * @return: nothing
     */
    public void publish(String channel, String message) {
        Set<Integer> users = channelMap.get(channel);
        if (users == null) return;

        for (Integer user : users) {
            PushNotification.notify(user, message);
        }
    }
}
