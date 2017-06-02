package com.efan.core.page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 45425 on 2017/5/8.
 */
public class ResultModel<T> implements Serializable {

    public ResultModel(List<T> list){
        this.data=list;
        this.total=(long)list.size();
    }
    public  ResultModel(List<T> list,Long total){
        this.data=list;
        this.total=total;
    }
    private List<T> data;
    private Long total;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
