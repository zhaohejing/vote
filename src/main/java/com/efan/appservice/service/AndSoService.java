package com.efan.appservice.service;

import com.efan.appservice.iservice.IAndSoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


/**
 * Created by 45425 on 2017/5/10.
 */
@Service
public class AndSoService implements IAndSoService {
    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    public   JdbcTemplate _jdbc;



 /*   //获取歌曲列表
    public ResultModel<Map<String,Object>> GetSongsList(GetSongsInput input){
        StringBuilder sql=new StringBuilder();
        StringBuilder count=new StringBuilder();
        sql.append("SELECT a.ID, a.ullSongCode,a.unSongCode,a.pszName,a.pszSpell,b.pszName singerName from songinfo a left join singerinfo b on a.arrSingers=b.unSingerCo where 1=1");
        count.append("SELECT count(*) from songinfo where 1=1");
        if (input.getFilter()!=null&& !input.getFilter().isEmpty()){
            sql.append(" and  a.pszName like '%"+input.getFilter()+"%' ");
            count.append(" and  pszName like '%"+input.getFilter()+"%' ");
        }
        if (input.word!=null&& !input.word.isEmpty()){
            sql.append(" and  a.pszSpell like '%"+input.word+"%' ");
            count.append(" and  pszSpell like '%"+input.word+"%' ");
        }
        if (input.cateId!=null&& !input.cateId.isEmpty()){
            sql.append(" and  a.arrStyles = '"+input.cateId+"' ");
            count.append(" and  arrStyles = '"+input.cateId+"' ");
        }
        if (input.singer!=null&&! input.singer.isEmpty()){
            sql.append(" and  a.arrSingers = '"+input.singer+"' ");
            count.append(" and  arrSingers = '"+input.singer+"' ");
        }
        if (input.version!=null&&! input.version.isEmpty()){
            sql.append(" and  a.arrVersions = '"+input.version+"' ");
            count.append(" and  arrVersions = '"+input.version+"' ");
        }
        sql.append(" limit  "+input.getPage()+" , "+input.getSize() );
        Long total=_jdbc.queryForObject(count.toString(),Long.class);
        List<Map<String,Object>> list = _jdbc.queryForList(sql.toString());
        return  new ResultModel<Map<String,Object>>(list,total);
    }
  */
}
