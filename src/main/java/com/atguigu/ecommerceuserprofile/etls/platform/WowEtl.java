package com.atguigu.ecommerceuserprofile.etls.platform;/**
 * Copyright (c) 2018-2028 尚硅谷 All Rights Reserved
 * <p>
 * Project: EcommerceUserProfile
 * Package: com.atguigu.ecommerceuserprofile.etls.platform
 * Version: 1.0
 * <p>
 * Created by wushengran on 2020/6/10 14:31
 */

import com.alibaba.fastjson.JSON;
import com.atguigu.ecommerceuserprofile.utils.SparkUtils;
import com.atguigu.ecommerceuserprofile.utils.dateutils.DateStyle;
import com.atguigu.ecommerceuserprofile.utils.dateutils.DateUtil;
import lombok.Data;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: WowEtl
 * @Description:
 * @Author: wushengran on 2020/6/10 14:31
 * @Version: 1.0
 */
public class WowEtl {
    public static void main(String[] args) {
        SparkSession session = SparkUtils.initSession();
        // 自定义方法，提取每一天的数据，作为List返回
        List<EveryDayRegVo> everyDayRegVos = regWeekCountEtl(session);
        List<EveryDayOrderVo> everyDayOrderVos = orderWeekCountEtl(session);
        // 打印输出
        System.out.println(everyDayRegVos);
        System.out.println(everyDayOrderVos);
    }

    private static List<EveryDayRegVo> regWeekCountEtl(SparkSession session) {
        LocalDate now = LocalDate.of(2019, Month.NOVEMBER, 30);
        Date nowDay = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date lastTwoWeekFirstDay = DateUtil.addDay(nowDay, -14);

        String regCountSql = "select date_format(create_time, 'yyyy-MM-dd') as day, " +
                " count(id) as regCount from ecommerce.t_member" +
                " where create_time >= '%s' and create_time < '%s'" +
                " group by date_format(create_time, 'yyyy-MM-dd') order by day";
        regCountSql = String.format(regCountSql,
                DateUtil.DateToString(lastTwoWeekFirstDay, DateStyle.YYYY_MM_DD_HH_MM_SS),
                DateUtil.DateToString(nowDay, DateStyle.YYYY_MM_DD_HH_MM_SS));

        Dataset<Row> dataset = session.sql(regCountSql);

        // 将结果数据转换成VO，返回
        List<EveryDayRegVo> result =  dataset.toJSON().collectAsList()
                .stream()
                .map(str -> JSON.parseObject(str, EveryDayRegVo.class))
                .collect(Collectors.toList());
        return result;
    }

    private static List<EveryDayOrderVo> orderWeekCountEtl(SparkSession session) {
        LocalDate now = LocalDate.of(2019, Month.NOVEMBER, 30);
        Date nowDay = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date lastTwoWeekFirstDay = DateUtil.addDay(nowDay, -14);

        String orderCountSql = "select date_format(create_time, 'yyyy-MM-dd') as day, " +
                " count(order_id) as orderCount from ecommerce.t_order" +
                " where create_time >= '%s' and create_time < '%s'" +
                " group by date_format(create_time, 'yyyy-MM-dd') order by day";
        orderCountSql = String.format(orderCountSql,
                DateUtil.DateToString(lastTwoWeekFirstDay, DateStyle.YYYY_MM_DD_HH_MM_SS),
                DateUtil.DateToString(nowDay, DateStyle.YYYY_MM_DD_HH_MM_SS));

        Dataset<Row> dataset = session.sql(orderCountSql);

        // 将结果数据转换成VO，返回
        List<EveryDayOrderVo> result =  dataset.toJSON().collectAsList()
                .stream()
                .map(str -> JSON.parseObject(str, EveryDayOrderVo.class))
                .collect(Collectors.toList());
        return result;
    }

    // 定义输出的VO类型
    @Data
    static class EveryDayRegVo {
        private String day;
        private Integer regCount;
    }

    @Data
    static class EveryDayOrderVo {
        private String day;
        private Integer orderCount;
    }
}
