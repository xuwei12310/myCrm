package com.xuwei.activiti.bean;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.xuwei.activiti.utils.ProcessEnum;


public class ActivitiTask {
	
	Task task;
	
	ProcessInstance processInstance;
	
	String processType;
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public ProcessInstance getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}

	public String getProcessType() {
		return ProcessEnum.valueOf(processInstance.getProcessDefinitionKey()).getValue();
	}
	
}
