package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class DataCache {

    private static List<BlackListSN> blackListSNList ;

    public DataCache(List<BlackListSN> blackListSNList){

        this.blackListSNList = blackListSNList;
    }

    public synchronized static List<BlackListSN> getInstance(){

        List<BlackListSN> subList = new ArrayList<BlackListSN>();

        if(blackListSNList !=null && blackListSNList.size()>1000){
            subList.addAll(blackListSNList.subList(0, 1000));
            blackListSNList = blackListSNList.subList(1000, blackListSNList.size());
        }else{
            subList.addAll(blackListSNList);
            blackListSNList = new ArrayList<>();

        }
        return subList;


    }
}
