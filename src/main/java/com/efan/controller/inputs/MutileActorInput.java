package com.efan.controller.inputs;

import com.efan.controller.dtos.ActorDto;

import java.util.List;


public class  MutileActorInput{
    public  Long activityId;
    public List<MuActor> actors;
    public  class  MuActor{
        public String actorKey;
        public  String  actorName;
        public  String  actorImage;
    }
}
