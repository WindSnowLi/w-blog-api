/*
 *  PanelData.java, 2021-08-25
 *
 *  Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 *
 */

package com.hiyj.blog.object;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class PanelData implements Serializable {
    private String title;
    private int total;
    private List<String> X;
    private List<Integer> Y;
}
