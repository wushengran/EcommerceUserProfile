package com.atguigu.ecommerceuserprofile.app.support;/**
 * Copyright (c) 2018-2028 尚硅谷 All Rights Reserved
 * <p>
 * Project: EcommerceUserProfile
 * Package: com.atguigu.ecommerceuserprofile.app.support
 * Version: 1.0
 * <p>
 * Created by wushengran on 2020/6/12 14:20
 */

import lombok.Data;

/**
 * @ClassName: EsTagQuery
 * @Description:
 * @Author: wushengran on 2020/6/12 14:20
 * @Version: 1.0
 */
@Data
public class EsTagQuery {
    private String name;    // 当前标签的名称
    private String value;    // 当前标签的取值
    private String type;    // 查询的类型
}
