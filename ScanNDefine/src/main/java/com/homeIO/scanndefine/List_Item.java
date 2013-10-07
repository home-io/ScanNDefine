package com.homeIO.scanndefine;

/**
 * Created by justin on 10/3/13 for homeIO Technologies!
 */
public class List_Item {
    private String desc;
    private int iconID;

    public List_Item(String desc, int iconID) {
        this.desc = desc;
        this.iconID = iconID;
    }

    public String getDesc() {
        return desc;
    }

    public int getIconID() {
        return iconID;
    }
}
