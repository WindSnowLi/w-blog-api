/*
 *  PanelData.java, 2021-08-25
 *
 *  Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 *
 */

package xyz.firstmeet.lblog.object;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PanelData {
    private String title;
    private int total;
    private List<String> X;
    private List<Integer> Y;
}
