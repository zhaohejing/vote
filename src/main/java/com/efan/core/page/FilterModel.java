package com.efan.core.page;

import java.security.PublicKey;

/**
 * Created by 45425 on 2017/5/10.
 */
public class FilterModel extends PageModel {
    public  FilterModel(){

    }
    public FilterModel(String filter)   {
        this.filter=filter;
    }
    public FilterModel(String filter,Integer index,Integer size)   {
        this.filter=filter;
        this.index=index;
        this.size=size;
    }
     public  String  filter;
}
