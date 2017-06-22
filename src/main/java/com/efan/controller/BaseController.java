package com.efan.controller;

import com.efan.core.page.ActionResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 45425 on 2017/6/22.
 */
@RestController
public class BaseController  {
    public Logger logger = LogManager.getLogger(getClass());
  /*  public BaseController() throws TokenException{
        String token="";
        if (token==null){
            throw  new TokenException();

        }
    }
    public  class TokenException extends Exception{

    }*/
}
