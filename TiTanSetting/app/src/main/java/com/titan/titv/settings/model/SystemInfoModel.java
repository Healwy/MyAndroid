package com.titan.titv.settings.model;

import com.titan.titv.settings.base.BaseModel;

public class SystemInfoModel extends BaseModel {
    private String mName;
    private String mValue;

    public SystemInfoModel(String name, String value) {
        this.mName = name;
        this.mValue = value;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        this.mValue = value;
    }
}
