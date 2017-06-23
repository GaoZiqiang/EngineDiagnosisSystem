package cn.edu.sdut.softlab.service;

import javax.inject.Inject;
import javax.inject.Named;

import cn.edu.sdut.softlab.model.UserInfo;

@Named("entityManager")
public class TestEntityManeger {

	@Inject
	EntityManagerUtil<UserInfo> emu;
	@Inject
	private UserInfo userInfo;

	public void testEMU() {
		userInfo.setUsername("gaoziqiang");
		try {
			emu.create(userInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
