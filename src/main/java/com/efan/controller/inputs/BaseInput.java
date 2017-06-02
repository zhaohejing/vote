package com.efan.controller.inputs;

/**
 * Created by 45425 on 2017/5/16.
 */
public class BaseInput {
    private Integer size;
    private Integer index;
    private String filter;
    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
    public Integer getPage() {
        if (index<=0)
        return index*size;
        else return  (index-1)*size;
    }
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }


}
