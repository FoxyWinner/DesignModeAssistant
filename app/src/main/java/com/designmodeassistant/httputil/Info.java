package com.designmodeassistant.httputil;

public class Info
{


    private static final String IP = "47.94.92.196";

    private static final String place = "DesignModeService";

    public static final String path_queryContentItem =
            "http://" + IP + ":8080/" + place + "/QueryContentItem";

    public static final String path_QueryDesignModeBeanById
            = "http://" + IP + ":8080/" + place + "/QueryDesignModeBeanById?chapterId=";
}
