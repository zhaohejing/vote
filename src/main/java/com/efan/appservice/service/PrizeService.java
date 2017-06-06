package com.efan.appservice.service;

import com.efan.appservice.iservice.IPrizeService;
import com.efan.controller.dtos.PrizeDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Prize;
import com.efan.repository.IPrizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 45425 on 2017/6/6.
 */
@Service
public class PrizeService implements IPrizeService {

    private IPrizeRepository _prizeRepository;

    @Autowired
    public PrizeService(IPrizeRepository prizeRepository){

        _prizeRepository=prizeRepository;
    }
    /*获取活动列表分页数据*/
    public ResultModel<Prize> Prizes(BaseInput input){
        //  Sort sort = new Sort(Sort.Direction.DESC, "createdate");
        Pageable pageable = new PageRequest(input.getIndex()-1, input.getSize(),null);
        Page<Prize> res=  _prizeRepository.findAllByPrizeNameContains(input.getFilter(), pageable);
        return  new ResultModel<>(res.getContent(),res.getTotalElements());
    }
    /*获取详情*/
    public Prize Prize(DeleteInput input){
        Prize result
                =  _prizeRepository.findOne(input.id);
        return  result;
    }
    /*删除*/
    public void   Delete(DeleteInput input){
        _prizeRepository.delete(input.id);
    }
    /*创建或编辑*/
    public Prize   Modify(PrizeDto input){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Prize model;
        if (input.id !=null&&input.id>0){
            model=_prizeRepository.findOne(input.id  );
            model.setActivityId(input.activityId);
            model.setDescription(input.description);

            if (! input.imageUrl.isEmpty()){
                model.setImageName(input.imageName);
                model.setImageUrl(input.imageUrl);
            }
            model.setPrizeName(input.prizeName);
            model=  _prizeRepository.saveAndFlush(model );
            return model;
        }else {
            model=new Prize();
            model.setPrizeName(input.prizeName);
            model.setDescription(input.description);
            if (! input.imageUrl.isEmpty()){
                model.setImageName(input.imageName);
                model.setImageUrl(input.imageUrl);
            }
            model.setActivityId(input.activityId);
            model.setId(0L);
            model.setCreationTime(df.format(new Date()));

            model=  _prizeRepository.save(model);
            return  model;
        }
    }


    private  String getTimeStampNumberFormat(Timestamp formatTime) {
        SimpleDateFormat m_format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", new Locale("zh", "cn"));
        return m_format.format(formatTime);
    }
}
