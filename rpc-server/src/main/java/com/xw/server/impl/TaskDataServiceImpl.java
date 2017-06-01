package com.xw.server.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xw.dao.TaskDataMapper;
import com.xw.model.TaskData;
import com.xw.model.TaskDataExample;
import com.xw.server.TaskDataService;

@Service
public class TaskDataServiceImpl implements TaskDataService {

	@Resource
	private TaskDataMapper taskDataMapper;
	
	@Override
	public List<TaskData> list() {
		return taskDataMapper.selectByExample(new TaskDataExample());
	}

}
