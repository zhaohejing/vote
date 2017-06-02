package com.efan.controller;

import com.efan.appservice.iservice.IAndSoService;
import com.efan.controller.dtos.SingerDto;
import com.efan.controller.dtos.SongCateDto;
import com.efan.controller.dtos.SongDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.GetSingerInput;
import com.efan.controller.inputs.GetSongsInput;
import com.efan.core.page.ActionResult;
import com.efan.core.page.FilterModel;
import com.efan.core.page.PageModel;
import com.efan.core.page.ResultModel;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *点歌接口列表
 */
@RestController
@RequestMapping("/api/songs")
public class AndSoController {
    private IAndSoService _songService;
    @Autowired
    public AndSoController(IAndSoService songService){
        this._songService=songService;

    }
    /*获取热门歌星列表*/
    @ApiOperation(value="热门歌星列表", notes="歌单接口")
    @ApiImplicitParam(name = "input", value = "{filter:关键词,index:页码,size:页容量}", required = true, dataType = "BaseInput")
    @RequestMapping(value  ="/hotstarlist" ,method = RequestMethod.POST)
    public ActionResult HotStarList(@RequestBody BaseInput input){
        ResultModel<Map<String,Object>> result=_songService.GetSingerByHot(input);
        return  new ActionResult(result);
    }

    /*获取歌星分类*/
    @ApiOperation(value="获取歌星分类", notes="歌单接口")
    @RequestMapping(value  ="/singercate" ,method = RequestMethod.POST)
    public ActionResult getSingerCate(){
        ResultModel<Map<String,Object>> result=_songService.GetSingerCate();
        return  new ActionResult(result);
    }
    /*获取歌星区域*/
    @ApiOperation(value="获取歌星区域", notes="歌单接口")
    @RequestMapping(value  ="/singerarea" ,method = RequestMethod.POST)
    public ActionResult getSingerArea(){
        ResultModel<Map<String,Object>> result=_songService.GetSingerArea();
        return  new ActionResult(result);
    }
    /*歌曲模糊搜索*/
    @ApiOperation(value="歌曲模糊搜索", notes="歌单接口")
    @ApiImplicitParam(name = "input", value = "{filter:关键词,index:页码,size:页容量 ,word:简写}", required = true, dataType = "GetSongsInput")
    @RequestMapping(value  ="/searchsongs" ,method = RequestMethod.POST)
    public ActionResult SearchSongs(@RequestBody GetSongsInput input){
        ResultModel<Map<String,Object>> result=_songService.GetSongsList(input);
        return  new ActionResult(result);
    }
    /*根据分类获取歌星列表*/
    @ApiOperation(value="根据分类获取歌星列表", notes="歌单接口")
    @ApiImplicitParam(name = "input", value = "{filter:过滤条件,index:页码,size:页容量 ,word:关键词}", required = true, dataType = "GetSingerInput")
    @RequestMapping(value  ="/getstarsbycate" ,method = RequestMethod.POST)
    public ActionResult GetStarByCate(@RequestBody GetSingerInput input){
        ResultModel<Map<String,Object>> result=_songService.GetSingerList(input);
        return  new ActionResult(result);
    }
    /*获取歌星下歌曲列表*/
    @ApiOperation(value="获取歌星下歌曲列表", notes="歌单接口")
    @ApiImplicitParam(name = "input", value = "{filter:过滤条件,index:页码,size:页容量 ,word:关键词}", required = true, dataType = "GetSongsInput")
    @RequestMapping(value  ="/getsongsbystar" ,method = RequestMethod.POST)
    public ActionResult GetSongsByStar(@RequestBody GetSongsInput input){
        ResultModel<Map<String,Object>> result=_songService.GetSongsList(input);
        return  new ActionResult(result);
    }
    /*获取歌曲分类列表*/
    @ApiOperation(value="获取歌曲分类列表", notes="歌单接口")
    @RequestMapping(value  ="/getsongscates" ,method = RequestMethod.POST)
    public ActionResult SongsCates(){
        List<Map<String,Object>> result=_songService.GetSongsCateList();
        return  new ActionResult(result);
    }
    /*获取分类下歌曲列表*/
    @ApiOperation(value="获取分类下歌曲列表", notes="歌单接口")
    @ApiImplicitParam(name = "input", value = "{filter:过滤条件,index:页码,size:页容量 ,word:关键词}", required = true, dataType = "GetSongsInput")
    @RequestMapping(value  ="/getsongsbycate" ,method = RequestMethod.POST)
    public ActionResult GetSongsByCates(@RequestBody GetSongsInput input){
        ResultModel<Map<String,Object>> result=_songService.GetSongsList(input);
        return  new ActionResult(result);
    }
    /*获取热点歌曲分类列表*/
    @ApiOperation(value="获取热点歌曲分类列表", notes="歌单接口")
    @RequestMapping(value  ="/gethotsongscate" ,method = RequestMethod.POST)
    public ActionResult GetSongsHotCates(){
        List<Map<String,Object>> result=_songService.GetSongsCateList();
        return  new ActionResult(result);
    }
    /*获取热点歌曲分类下歌曲*/
    @ApiOperation(value="获取热点歌曲分类下歌曲", notes="歌单接口")
    @ApiImplicitParam(name = "input", value = "{filter:过滤条件,index:页码,size:页容量 ,word:关键词}", required = true, dataType = "GetSongsInput")
    @RequestMapping(value  ="/gethotsongsbycate" ,method = RequestMethod.POST)
    public ActionResult GetSongsByHotCates(@RequestBody GetSongsInput input){
        ResultModel<Map<String,Object>> result=_songService.GetSongsList(input);
        return  new ActionResult(result);
    }

    /*获取排行榜列表*/
    @ApiOperation(value="获取排行榜列表", notes="歌单接口")
    @RequestMapping(value  ="/getrankinglist" ,method = RequestMethod.POST)
    public ActionResult GetRankingList(){
        List<Map<String,Object>> result=_songService.GetSongsVerList();
        return  new ActionResult(result);
    }

    /*获取排行榜歌曲列表*/
    @ApiOperation(value="获取排行榜歌曲列表", notes="歌单接口")
    @ApiImplicitParam(name = "input", value = "{filter:过滤条件,index:页码,size:页容量 ,word:关键词}", required = true, dataType = "GetSongsInput")
    @RequestMapping(value  ="/getrankingsongs" ,method = RequestMethod.POST)
    public ActionResult GetRankingSongs(@RequestBody GetSongsInput input){
        ResultModel<Map<String,Object>> result=_songService.GetSongsList(input);
        return  new ActionResult(result);
    }

    /*获取分类下歌星列表*/
    @ApiOperation(value="获取歌星分类列表", notes="歌单接口")
    @ApiImplicitParam(name = "input", value = "{filter:过滤条件,index:页码,size:页容量 ,word:关键词}", required = true, dataType = "GetSingerInput")
    @RequestMapping(value  ="/getstarcates" ,method = RequestMethod.POST)
    public ActionResult GetStarCates(@RequestBody GetSingerInput input){
        ResultModel<Map<String,Object>> result=_songService.GetSingerList(input);
        return  new ActionResult(result);
    }


}
