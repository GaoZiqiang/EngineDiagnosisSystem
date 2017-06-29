package cn.edu.sdut.softlab.controller;

import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import cn.edu.sdut.softlab.model.EngineInfo;
import cn.edu.sdut.softlab.model.TempUserInfo;
import cn.edu.sdut.softlab.model.UserInfo;

@Named("UserController")
@RequestScoped
public class UserController {
	EntityManagerFactory emf;
	EntityManager em;

	private String tempLoginName;
	@Inject
	private UserInfo user;
	private TempUserInfo tempUser = new TempUserInfo();

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public TempUserInfo getTempUser() {
		return tempUser;
	}

	public void setTempUser(TempUserInfo tempUser) {
		this.tempUser = tempUser;

	}

	/*
	 * * 用户注册
	 */
	public String register() throws IllegalStateException, SecurityException, HeuristicMixedException,
			HeuristicRollbackException, RollbackException, SystemException {
		try {
			// 该参数必须与persistence-unit的name相一致
			emf = Persistence.createEntityManagerFactory("engine_diagnosis_system");
			//断点测试
			System.out.println("断点测试emf:  " + emf.toString());
			em = emf.createEntityManager();
			// 开启事务
			em.getTransaction().begin();
			// 将User持久化到数据库
			em.persist(user);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
			System.out.println("注册成功!");
		}
		return "diagnosis";
	}

	/*
	 * * 用户登录
	 */
	public String login() throws IllegalStateException, SecurityException, HeuristicMixedException,
			HeuristicRollbackException, RollbackException, SystemException {
		String flag = null;
		try {
			emf = Persistence.createEntityManagerFactory("engine_diagnosis_system");
			em = emf.createEntityManager();

			Query query = em
					.createQuery("select u from UserInfo u where u.username = :username and u.password = :password");
			query.setParameter("username", tempUser.getUsername());
			query.setParameter("password", tempUser.getPassword());

			List resultList = query.getResultList();

			if (resultList.size() == 1) {
				flag = "diagnosis";
			} else {
				flag = "index";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "diagnosis.jsf";

	}

	public boolean loginValidate1(String username) throws IllegalStateException, SecurityException,
			HeuristicMixedException, HeuristicRollbackException, RollbackException, SystemException {
		boolean flagBoolean = false;
		try {
			emf = Persistence.createEntityManagerFactory("engine_diagnosis_system");
			em = emf.createEntityManager();

			Query query = em.createQuery("select u from UserInfo u where u.username = :username");
			query.setParameter("username", username);
			// query.setParameter("password", tempUser.getPassword());

			List resultList = query.getResultList();

			/*
			 * Iterator<UserInfo> userIterator = resultList.iterator(); while
			 * (userIterator.hasNext()) { UserInfo newUser = (UserInfo)
			 * userIterator.next(); // item.getName(); tempLoginName =
			 * newUser.getUsername(); System.out.println("print newId:  " +
			 * newUser.getId()); }
			 */

			if (resultList.size() == 1) {
				flagBoolean = true;
			} else {
				flagBoolean = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flagBoolean;

	}

	public boolean loginValidate2(String password) throws IllegalStateException, SecurityException,
			HeuristicMixedException, HeuristicRollbackException, RollbackException, SystemException {
		boolean flagBoolean = false;
		try {
			emf = Persistence.createEntityManagerFactory("engine_diagnosis_system");
			em = emf.createEntityManager();

			Query query = em
					.createQuery("select u from UserInfo u where u.username = :username and u.password = :password");
			query.setParameter("username", tempUser.getUsername());
			query.setParameter("password", password);

			List resultList = query.getResultList();

			/*
			 * Iterator<UserInfo> userIterator = resultList.iterator(); while
			 * (userIterator.hasNext()) { UserInfo newUser = (UserInfo)
			 * userIterator.next(); // item.getName(); tempLoginName =
			 * newUser.getUsername(); System.out.println("print newId:  " +
			 * newUser.getId()); }
			 */

			if (resultList.size() == 1) {
				flagBoolean = true;
			} else {
				flagBoolean = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flagBoolean;

	}

	/*
	 * * 用户名验证 长度 用户注册
	 */
	public void userNameValidate(FacesContext context, UIComponent component, Object value)
			throws IllegalStateException, SecurityException, HeuristicMixedException, HeuristicRollbackException,
			RollbackException, SystemException {
		if (value.toString().length() < 5 || value.toString().length() > 20) {
			throw new ValidatorException(new FacesMessage("用户名长度应为5-20个字符"));
		}
		if (loginValidate1(value.toString())) {
			throw new ValidatorException(new FacesMessage("该用户名已注册"));
		}
	}

	/*
	 * s * 用户密码验证 长度 用户注册
	 */
	public void passwordValidate(FacesContext context, UIComponent component, Object value)
			throws IllegalStateException, SecurityException, HeuristicMixedException, HeuristicRollbackException,
			RollbackException, SystemException {
		if (value.toString().length() < 5 || value.toString().length() > 12) {
			throw new ValidatorException(new FacesMessage("密码长度应为5-12个字符"));
		}

	}

	/*
	 * * 用户名验证 用户登录
	 */
	public void loginUserNameValidate(FacesContext context, UIComponent component, Object value)
			throws IllegalStateException, SecurityException, HeuristicMixedException, HeuristicRollbackException,
			RollbackException, SystemException {
		if (!loginValidate1(value.toString())) {
			throw new ValidatorException(new FacesMessage("该用户不存在"));
		}
	}

	/*
	 * * 用户名密码验证 用户登录
	 */
	public void loginPasswordValidate(FacesContext context, UIComponent component, Object value)
			throws IllegalStateException, SecurityException, HeuristicMixedException, HeuristicRollbackException,
			RollbackException, SystemException {
		if (!loginValidate2(value.toString())) {
			throw new ValidatorException(new FacesMessage("密码错误"));
		}
	}

}
