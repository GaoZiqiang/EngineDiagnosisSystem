package cn.edu.sdut.softlab.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import cn.edu.sdut.softlab.model.TempUser;
import cn.edu.sdut.softlab.model.UserInfo;

@Named("UserController")
@RequestScoped
public class UserController {
	EntityManagerFactory emf;
	EntityManager em;

	private UserInfo user = new UserInfo();
	private TempUser tempUser = new TempUser();

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public TempUser getTempUser() {
		return tempUser;
	}

	public void setTempUser(TempUser tempUser) {
		this.tempUser = tempUser;

	}

	// 用户注册
	public String register() throws IllegalStateException, SecurityException, HeuristicMixedException,
			HeuristicRollbackException, RollbackException, SystemException {
		try {
			// utx.begin();
			// 该参数必须与persistence-unit的name相一致
			System.out.println("Start!");
			emf = Persistence.createEntityManagerFactory("engine_diagnosis_system");

			em = emf.createEntityManager();
			System.out.println("打印输出em:  " + em.toString());
			System.out.println("打印输出emf:  " + emf.toString());

			// 断点测试
			System.out.println("打印输出newPerson:   " + user.toString());
			System.out.println("打印输出em:  " + em.toString());// 测试结果，EntityManager注入失败
			em.getTransaction().begin();// 至关重要的一步：开启事务
			// 主键自动生成策略 就不能再setId了
			// user.setId(365);

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
		return "diagnosis.jsf";
	}

	// 用户登录
	public String login() {
		String flag = null;
		try {
			emf = Persistence.createEntityManagerFactory("engine_diagnosis_system");
			em = emf.createEntityManager();

			System.out.println("打印日志em in login:" + em.toString());
			Query query = em
					.createQuery("select u from UserInfo u where u.username = :username and u.password = :password");
			query.setParameter("username", tempUser.getUsername());
			query.setParameter("password", tempUser.getPassword());

			List resultList = query.getResultList();
			System.out.println("打印日志resultListSize:" + resultList.size());
			if (resultList.size() > 0) {
				flag = "error";
			} else {
				flag = "diagnosis.jsf";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}
	/*
	 * Query query = em.
	 * createQuery("select u from UserInfo u where u.username = :uername and u.password = :password"
	 * ); query.setParameter("username", tempUser.getUsername());
	 * query.setParameter("password", tempUser.getPassword());
	 * 
	 * List resultList = query.getResultList(); if(resultList.size() > 0) {
	 * return "index"; }else { return "error"; } }
	 */
}
