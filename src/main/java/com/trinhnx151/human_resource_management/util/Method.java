package com.trinhnx151.human_resource_management.util;

import java.sql.Timestamp;
import java.util.Date;

public class Method {
    public static Timestamp getT_now() {
        Date nowDate = new Date();
        Timestamp now = new Timestamp(nowDate.getTime());
        return now;
    }
}
