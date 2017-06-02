package com.efan.appservice.service;

import com.efan.appservice.iservice.IGiftService;
import com.efan.controller.dtos.GiftDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Gift;
import com.efan.repository.IGiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *礼物创建相关
 */
@Service
public class GiftService implements IGiftService {

    private IGiftRepository _giftRepository;
    @Autowired
    public GiftService(IGiftRepository giftRepository){
        this._giftRepository=giftRepository;
    }
    /*获取活动列表分页数据*/
    public ResultModel<Gift> Gifts(BaseInput input){
        //  Sort sort = new Sort(Sort.Direction.DESC, "createdate");
        Pageable pageable = new PageRequest(input.getIndex()-1, input.getSize(),null);
        Page<Gift> res=  _giftRepository.findAllByGiftNameContains(input.getFilter(), pageable);
        return  new ResultModel<Gift>(res.getContent(),res.getTotalElements());
    }
    /*获取详情*/
    public Gift Gift(DeleteInput input){
        Gift res=  _giftRepository.findOne(input.id);
        return  res;
    }
    /*删除*/
    public void   Delete(DeleteInput input){
        _giftRepository.delete(input.id);
    }
    /*创建或编辑*/
    public Gift   Modify(GiftDto input){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Gift model;
        if (input.id !=null&&input.id>0){
            model=_giftRepository.findOne(input.id  );
            model.setGiftName(input.giftName);
            if (! input.imageUrl.isEmpty()){
                model.setImageName(input.imageName);
                model.setImageUrl(input.imageUrl);
            }
            model.setPrice(input.price);
            model=  _giftRepository.saveAndFlush(model );
            return model;
        }else {
            model=new Gift();
            model.setPrice(input.price);
            if (! input.imageUrl.isEmpty()){
                model.setImageName(input.imageName);
                model.setImageUrl(input.imageUrl);
            }
            model.setGiftName(input.giftName);
            model.setDelete(false);
            model.setId(0L);
            model.setCreationTime(df.format(new Date()));

          model=  _giftRepository.save(model);
          return  model;
        }
    }

}
