package com.efan.core.page;

import org.springframework.data.domain.Sort;


/**
 * 分页帮助类
 */
public class PageModel {
    public Integer index;
    public  Integer size;
public  PageModel(){
    this.index=1;
    this.size=10;
}
    public  PageModel(Integer index,Integer size){
        this.index=index;
        this.size=size;
    }



}
