package com.xuwei.controller;

import java.io.Serializable;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baomidou.mybatisplus.service.IService;

public class BaseTreeController<T extends IService<M>,M extends Serializable> extends BaseController<M> {

	T baseService;
	
	@RequestMapping(value = "main",method = RequestMethod.GET)
    public String main(Model model){
        return defaultViewPrefix();
    }
}
