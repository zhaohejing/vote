package com.efan.controller.inputs;

import org.springframework.data.redis.core.index.IndexValueTransformer;

/**
 * Created by 45425 on 2017/5/16.
 */
public class GetSingerInput extends BaseInput {
    public  String word;
    public Integer cate;
    public Integer area;

}
