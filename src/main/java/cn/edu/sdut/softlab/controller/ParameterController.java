package cn.edu.sdut.softlab.controller;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import cn.edu.sdut.softlab.model.EngineInfo;

public class ParameterController {
	EntityManagerFactory emf = null;
    EntityManager em = null;

	private EngineInfo engineInfo = new EngineInfo();
    public EngineInfo getEngineInfo() {
		return engineInfo;
	}

	public void setEngineInfo(EngineInfo engineInfo) {
		this.engineInfo = engineInfo;
	}

	/*private ParameterUtil parameterUtil = new ParameterUtil();

	public ParameterUtil getParameterUtil() {
		return parameterUtil;
	}

	public void setParameterUtil(ParameterUtil parameterUtil) {
		this.parameterUtil = parameterUtil;
	}
*/
	// 将参数存入数据库
	public void addParameter() throws IllegalStateException, SecurityException, HeuristicMixedException,
			HeuristicRollbackException, RollbackException, SystemException {
		try {
			// utx.begin();
			// 该参数必须与persistence-unit的name相一致
			System.out.println("Start!");
			emf = Persistence.createEntityManagerFactory("engine_diagnosis_system");

			em = emf.createEntityManager();
			System.out.println("打印输出em:  " + em.toString());
			System.out.println("打印输出emf:  " + emf.toString());
			engineInfo.setTime(new Date());
			// 断点测试
			//System.out.println("打印输出newPerson:   " + user.toString());
			System.out.println("打印输出em:  " + em.toString());// 测试结果，EntityManager注入失败
			em.getTransaction().begin();// 至关重要的一步：开启事务
			em.persist(engineInfo);
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
	
	//参数分析
	public void Analysis() {
		//1.获取T P1 P2等参数
		//2.逻辑判断
		if (T > 95) {
			System.out.println("温度过高!");
			engineInfo.setException_T("温度过高!");
		} else {
			if (P1 > 1.1 && P1 < 1.3) {
				if (P2 > 0.8 && P2 < 1.0) {
					if (L1 == 1) {
						if (L2 == 0) {
							if (X > 8) {
								System.out.println("结束!");
								
							} else {
								System.out.println("风扇传动带脱落!");
								engineInfo.setException_X("风扇传动带脱落!");
							}
						} else {
							System.out.println("水泵轴与叶轮松脱!");
							engineInfo.setException_L2("水泵轴与叶轮松脱!");
						}
					} else {
						System.out.println("节温器主筏门脱落!");
						engineInfo.setException_L1("节温器主筏门脱落!");
					}
				} else {
					System.out.println("汽缸密封故障!");
					engineInfo.setException_P2("汽缸密封故障!");
				}
			} else {
				System.out.println("冷却系统漏水!");
				engineInfo.setException_P1("冷却系统漏水!");
			}
		}
	}
}
