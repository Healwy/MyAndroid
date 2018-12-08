package com.titan.titv.settings.security;

public interface ISecurityContract {

    interface Presenter {
        void setUnknowSource(String value);

        int getUnknowSource();

        void setADB(int value);

        int getADB();
    }
}
