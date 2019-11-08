package com.sprphnx.batchprocessing.batch.processor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.sprphnx.batchprocessing.model.User;

@Component
public class UserItemProcessor implements ItemProcessor<User, User> {

	public static Map<String, String> DEPT = new HashMap<>();
	
	public UserItemProcessor() {
		super();
		DEPT.put("001", "IT");
		DEPT.put("002", "HR");
	}

	@Override
	public User process(User user) throws Exception {
		User processedUser = new User();
		BeanUtils.copyProperties(user, processedUser);
		processedUser.setDept(DEPT.get(user.getDept()));
		return processedUser;
	}

}
