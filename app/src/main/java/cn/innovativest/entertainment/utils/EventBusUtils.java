package cn.innovativest.entertainment.utils;


import org.greenrobot.eventbus.EventBus;

import cn.innovativest.entertainment.base.BaseEvent;

/**
 * EventBus工具类
 * 订阅、取消订阅、发布
 */

public class EventBusUtils {

    /**
     * 注册
     *
     * @param subscriber
     */
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    /**
     * 解除注册
     *
     * @param subscriber
     */
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 发送事件
     *
     * @param event
     */
    public static void sendEvent(BaseEvent event) {
        EventBus.getDefault().post(event);
    }

    /**
     * 发送粘性事件
     *
     * @param event
     */
    public static void sendStickyEvent(BaseEvent event) {
        EventBus.getDefault().postSticky(event);
    }

}
