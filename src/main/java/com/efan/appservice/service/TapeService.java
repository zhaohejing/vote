package com.efan.appservice.service;

import com.efan.appservice.iservice.ITapeService;
import com.efan.controller.inputs.MySongsInput;
import com.efan.core.page.FilterModel;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.MySongs;
import com.efan.repository.primary.IMySongsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * 歌曲接口列表
 */
@Service
public class TapeService implements ITapeService {

    private IMySongsRepository _mysongsRepository;
    @Autowired
    public TapeService(IMySongsRepository mysongsRepository){
        this._mysongsRepository=mysongsRepository;
    }
    //创建我点过的歌曲
    //  @Async
    public MySongs CreateMySongs(MySongsInput input)  {
        Timestamp date = new Timestamp(System.currentTimeMillis());
     MySongs songs=new MySongs();
     songs.setSinger(input.singer);
     songs.setCreationTime(date);
     songs.setId(0L);
            songs.setCreationUserId(1L);
            songs.setSongName(input.songName);
            songs.setUserKey(input.userKey);
            songs.setState(true);
            songs.setSongKey(input.songKey);
            songs.setModifyTime(date);
            songs.setModifyUserId(1L);
            songs=_mysongsRepository.save(songs);
       return songs;
    }

     public  void DeleteMySongs(Long id){
        _mysongsRepository.delete(id);
     }

    //获取我的点歌列表
    public ResultModel<MySongs> GetMySongsList(FilterModel model){
        Pageable pageable = new PageRequest(model.index-1, model.size,null);
        Page<MySongs> res=  _mysongsRepository.findAll( pageable);
        return  new ResultModel<MySongs>( res.getContent(),res.getTotalElements());
    }

}

