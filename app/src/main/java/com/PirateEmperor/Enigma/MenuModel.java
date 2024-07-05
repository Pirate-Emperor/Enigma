package com.PirateEmperor.Enigma;

import androidx.fragment.app.Fragment;

public class MenuModel {

    public String menuName;
    public Fragment url;
    public boolean hasChildren, isGroup;
    public int drawable;

    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, Fragment url, int drawable) {

        this.menuName = menuName;
        this.url = url;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
        this.drawable = drawable;

    }
}
