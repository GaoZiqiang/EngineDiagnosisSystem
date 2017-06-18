package cn.edu.sdut.softlab.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import cn.edu.sdut.softlab.model.UserInfo;
@Named("UserController")
@RequestScoped
public class UserController {
	EntityManagerFactory emf = null;
    EntityManager em = null;
    
    private UserInfo user = new UserInfo();
	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	// 用户注册
	public void register() throws IllegalStateException, SecurityException, HeuristicMixedException,
			HeuristicRollbackException, RollbackException, SystemException {
		try {
			// utx.begin();
			// 该参数必须与persistence-unit的name相一致
			System.out.println("Start!");
			emf = Persistence.createEntityManagerFactory("engine_diagnosis_system");

			em = emf.createEntityManager();
			System.out.println("打印输出em:  " + em.toString());
			System.out.println("打印输出emf:  " + emf.toString());
			user.setId(3);
			user.setUsername("wangwu");
			user.setPassword("003");
			// 断点测试
			System.out.println("打印输出newPerson:   " + user.toString());
			System.out.println("打印输出em:  " + em.toString());// 测试结果，EntityManager注入失败
			em.getTransaction().begin();// 至关重要的一步：开启事务
			em.persist(user);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();

			emf.close();
			// utx.commit();
			System.out.println("存入成功!");
		}

	}

	// 用户登录
	public void login() {

	}
}
