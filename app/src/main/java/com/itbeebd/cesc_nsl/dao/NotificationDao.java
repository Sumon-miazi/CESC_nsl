package com.itbeebd.cesc_nsl.dao;

import com.itbeebd.cesc_nsl.sugarClass.NotificationObj;

import java.util.ArrayList;
import java.util.List;

public class NotificationDao {
    public String saveNotification(ArrayList<NotificationObj> notificationObjArrayList) {
        int unReadNotification = 0;
        try {
            for(int i = 0; i < notificationObjArrayList.size(); i++){
                NotificationObj notificationObj = notificationObjArrayList.get(i);
                List<NotificationObj> oldNoti = NotificationObj.find(NotificationObj.class, "NID = ?", String.valueOf(notificationObj.getN_id()));
                if(oldNoti != null){
                    if(oldNoti.size() == 0){
                        unReadNotification++;
                        notificationObj.save();
                    }
                }
                else {
                    unReadNotification++;
                    notificationObj.save();
                }
            }
        }
        catch (Exception ignore){
            ignore.printStackTrace();
            for(int i = 0; i < notificationObjArrayList.size(); i++){
                NotificationObj notificationObj = notificationObjArrayList.get(i);
                notificationObj.save();
            }

            unReadNotification = notificationObjArrayList.size();
        }
     //   guardian.save();
        return String.valueOf(unReadNotification);
    }
}
