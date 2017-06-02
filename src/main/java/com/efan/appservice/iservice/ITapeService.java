package com.efan.appservice.iservice;

import com.efan.controller.inputs.MySongsInput;
import com.efan.core.page.FilterModel;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.MySongs;

/**
 * 录音service
 */
public interface ITapeService {
    ResultModel<MySongs> GetMySongsList(FilterModel model);

    MySongs CreateMySongs(MySongsInput input);
    void DeleteMySongs(Long id);
}
