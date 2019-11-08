package com.sprphnx.batchprocessing.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprphnx.batchprocessing.model.User;
import com.sprphnx.batchprocessing.repository.UserRepository;

@Component
public class DBWriter implements ItemWriter<User>{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public void write(List<? extends User> users) throws Exception {
		Iterable<? extends User> usersIterable = users;
		userRepository.saveAll(usersIterable);
		
	}

}
