package com.atguigu.ecommerceuserprofile.utils;/**
 * Copyright (c) 2018-2028 尚硅谷 All Rights Reserved
 * <p>
 * Project: EcommerceUserProfile
 * Package: com.atguigu.ecommerceuserprofile.utils
 * Version: 1.0
 * <p>
 * Created by wushengran on 2020/6/9 14:21
 */

import org.apache.spark.sql.SparkSession;

/**
 * @ClassName: SparkUtils
 * @Description:
 * @Author: wushengran on 2020/6/9 14:21
 * @Version: 1.0
 */
public class SparkUtils {
    // 定义一个spark session的会话池
    private static ThreadLocal<SparkSession> sessionPool = new ThreadLocal<>();

    // 初始化spark session的方法
    public static SparkSession initSession() {
        // 先判断会话池中是否有session，如果有就直接用，没有再创建
        if (sessionPool.get() != null) {
            return sessionPool.get();
        }

        SparkSession session = SparkSession.builder()
                .appName("userprofile-etl")
                .master("local[*]")
                .config("es.nodes", "localhost")
                .config("es.port", "9200")
                .config("es.index.auto.create", "false")
                .enableHiveSupport()    // 启用hive支持
                .getOrCreate();
        sessionPool.set(session);
        return  session;
    }
}
