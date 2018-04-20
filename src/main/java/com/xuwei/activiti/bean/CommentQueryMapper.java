package com.xuwei.activiti.bean;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface CommentQueryMapper {

	@Select("select b.NAME_ as taskName "+
		"FROM ACT_HI_COMMENT a "+
		"left join ACT_HI_TASKINST b on a.TASK_ID_ = b.ID_ "+
		"where a.PROC_INST_ID_ =#{processInstanceId} ")
	@Results(value={
			@Result(property = "taskName", column = "taskName", javaType = String.class, jdbcType = JdbcType.VARCHAR)
	})
	List<CustomComment> queryCommentsByProcessInstanceId(String processInstanceId);
}
