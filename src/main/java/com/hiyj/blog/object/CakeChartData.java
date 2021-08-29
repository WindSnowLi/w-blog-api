/*
 *  CakeChartData.java, 2021-08-25
 *
 *  Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 *
 */

package com.hiyj.blog.object;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class CakeChartData {
    private List<String> dataName;
    private List<Map<String, Object>> data;
}
