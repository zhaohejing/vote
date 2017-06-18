package com.efan;

import com.efan.appservice.iservice.IGiftService;
import com.efan.controller.dtos.GiftDto;
import com.efan.controller.dtos.SendDto;
import com.efan.core.primary.Giving;
import com.sun.javafx.collections.MappingChange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Component
public class ScheduledJob {
        private IGiftService _giftService;
        @Autowired
        public  ScheduledJob(IGiftService giftService){
            _giftService=giftService;
        }
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    public JdbcTemplate _jdbc;
    private Logger logger = LogManager.getLogger(getClass());
    @Scheduled(cron="0 0/2 * * * ?")
    public void SendGifts() {
            String sql=GenderSql();
        List<Map<String,Object>> list= _jdbc.queryForList(sql);
        if (list.size()>0){
            for (Map<String,Object> d:list
                 ) {
                String sendKey=d.get("pay_key").toString();
                if(d.get("actor_id")==null) continue;
                Long actorId=Long.parseLong(d.get("actor_id").toString());
                if(d.get("gift_id")==null) continue;
                Long giftId=Long.parseLong(d.get("gift_id").toString());
                if(d.get("activity_id")==null) continue;
                Long activityId=Long.parseLong(d.get("activity_id").toString());
                //String sendKey,String sendName,String sendImage,Long activityId,Long actorId,Long giftId
                SendDto dto=new SendDto(sendKey,
                        d.get("send_name")==null?"":d.get("send_name").toString(),
                         d.get("send_image")==null?"":d.get("send_image").toString(),
                        activityId
                        ,actorId
                        ,giftId);
                try {
                    Giving t = _giftService.SendGift(dto);
                    if (t!=null ){
                        logger.info(t.getSendName()+"送礼给"+t.getActorId() +"成功");

                    }
                }catch (Exception e){
                    logger.error(e.getMessage());
                    continue;
                }
            }
        }
    }

    private  String GenderSql(){
        StringBuilder sb=new StringBuilder();
        sb.append("select a.* from orders a left join (select a.id, a.send_key from giving a ");
        sb.append(" inner join ");
        sb.append(" (select send_key,max(creation_time) creation_time from giving group by send_key) b on a.creation_time=b.creation_time) b");
        sb.append(" on a.pay_key=b.send_key  ");
        sb.append(" where a.pay_state=1 and b.id is null");
        String left =getTimeByMinute(-5);
        String right=getTimeByMinute(0);
        sb.append(" and a.creation_time>='"+left+"' ");
        sb.append(" and a.creation_time<'"+right+"' ;");
       return  sb.toString();
    }
       /*
    获取当前时间之前或之后几分钟 minute
    */
    private   String getTimeByMinute(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

    }
}
