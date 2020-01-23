package com.wise.roommaster.dao;

import com.wise.roommaster.model.CompanyUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompanyUserDAO {
    public List<CompanyUser> list(){
        return new ArrayList<>(Arrays.asList(
                new CompanyUser()
        ));
    }
}
