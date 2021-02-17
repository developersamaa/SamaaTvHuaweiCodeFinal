package com.Samaatv.samaaapp3.model;

public class Nav_Model {
    String Title;
    boolean IsSelected;

    public Nav_Model(String title, boolean isSelected) {
        Title = title;
        IsSelected = isSelected;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public boolean isSelected() {
        return IsSelected;
    }

    public void setSelected(boolean selected) {
        IsSelected = selected;
    }
}
