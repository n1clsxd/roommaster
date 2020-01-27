package com.wise.roommaster.dao;

import com.wise.roommaster.model.CompanyUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompanyUserDAO {
    public ArrayList<CompanyUser> list(){
        return new ArrayList<>(Arrays.asList(
                new CompanyUser(1,"Pedro","pedrin@wise.com","123123","adm",null,null),
                new CompanyUser(2,"Carla","carla@wise.com","123123","user",null,null),
                new CompanyUser(3,"Udyr","udyr@wise.com","123123","user",null,null)

        ));
    }
}
