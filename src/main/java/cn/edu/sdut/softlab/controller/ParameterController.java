package cn.edu.sdut.softlab.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;

import cn.edu.sdut.softlab.model.EngineInfo;
import cn.edu.sdut.softlab.model.TempEngine;

@Named("parameterController")
@RequestScoped
public class ParameterController implements Serializable {
	private static final long serialVersionUID = 1L;

	EntityManagerFactory emf;
	EntityManager em;
	@Inject
	private TempEngine newEngineInfo;
	@Inject
	private EngineInfo engineInfo;
	private List<EngineInfo> newEngineInfoList;

	// 统计报表
	private List<EngineInfo> engineInfoList = null;

	public List<EngineInfo> getEngineInfoList() {
		return engineInfoList;
	}

	public void setEngineInfoList(List<EngineInfo> engineInfoList) {
		this.engineInfoList = engineInfoList;
	}

	public List<EngineInfo> getNewEngineInfoList() {
		return newEngineInfoList;
	}

	public void setNewEngineInfoList(List<EngineInfo> newEngineInfoList) {
		this.newEngineInfoList = newEngineInfoList;
	}

	public EngineInfo getEngineInfo() {
		return engineInfo;
	}

	public void setEngineInfo(EngineInfo engineInfo) {
		this.engineInfo = engineInfo;
	}

	// 参数分析
	public void analysis() {
		// 1.获取T P1 P2等参数
		System.out.println("print engineInfo:  " + engineInfo.toString());
		
		String resultFlag = null;
		// System.out.println("print Tem: " + newEngineInfo.getTem_T());
		// System.out.println("print P1: " + newEngineInfo.getPressure_P1());
		// System.out.println("print Tem" + tempEngine.getTem_T());
		engineInfo.setTem_T(newEngineInfo.getTem_T());
		engineInfo.setPressure_P1(newEngineInfo.getPressure_P1());
		engineInfo.setPressure_P2(newEngineInfo.getPressure_P2());
		engineInfo.setGap_L1(newEngineInfo.getGap_L1());
		engineInfo.setGap_L2(newEngineInfo.getGap_L2());
		engineInfo.setOffset_X(newEngineInfo.getOffset_X());

		// 2.逻辑判断
		if (newEngineInfo.getTem_T() > 95) {
			System.out.println("温度过高!");
			resultFlag = "温度过高!";
			engineInfo.setException_T("温度过高!");

		} else {
			if (newEngineInfo.getPressure_P1() > 1.1 && newEngineInfo.getPressure_P1() < 1.3) {
				if (newEngineInfo.getPressure_P2() > 0.8 && newEngineInfo.getPressure_P2() < 1.0) {
					if (newEngineInfo.getGap_L1() == 1) {
						if (newEngineInfo.getGap_L2() == 0) {
							if (newEngineInfo.getOffset_X() > 8) {
								System.out.println("结束!");
								resultFlag = "测试结束，没有故障!";
							} else {
								System.out.println("风扇传动带脱落!");
								resultFlag = "风扇传动带脱落!";
								engineInfo.setException_X("风扇传动带脱落!");
							}
						} else {
							System.out.println("水泵轴与叶轮松脱!");
							resultFlag = "水泵轴与叶轮松脱!";
							engineInfo.setException_L2("水泵轴与叶轮松脱!");
						}
					} else {
						System.out.println("节温器主筏门脱落!");
						resultFlag = "节温器主筏门脱落!";
						engineInfo.setException_L1("节温器主筏门脱落!");
					}
				} else {
					System.out.println("汽缸密封故障!");
					resultFlag = "汽缸密封故障!";
					engineInfo.setException_P2("汽缸密封故障!");
				}
			} else {
				System.out.println("冷却系统漏水!");
				resultFlag = "冷却系统漏水!";
				engineInfo.setException_P1("冷却系统漏水!");
			}
		}

		try {
			emf = Persistence.createEntityManagerFactory("engine_diagnosis_system");

			em = emf.createEntityManager();
			// em.persist(engineInfo);
			em.getTransaction().begin();// 至关重要的一步：开启事务
			
			// engineInfo.setId(36);
			engineInfo.setTime(new Date());

			em.persist(engineInfo);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
		// System.out.println(engineInfo.exception_T);

		System.out.println("Exception_T" + engineInfo.getException_T());
		System.out.println("Exception_P1" + engineInfo.getException_P1());
		System.out.println("Exception_X" + engineInfo.getException_X());
		// return resultFlag;
	}

	public void testDouble() {
		// String stringDemo = "22.32";
		System.out.println("print Double:  ");
	}

	// 查询统计结果
	public String result() {
		emf = Persistence.createEntityManagerFactory("engine_diagnosis_system");
		em = emf.createEntityManager();

		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(engineInfo.getClass()));
		System.out.println("成功find");

		engineInfoList = em.createQuery(cq).getResultList();

		// engineInfoList.size();
		// engineInfoList.get(1);
		// System.out.println("打印输出size: " + engineInfoList.size());
		// System.out.println("打印输出listItem.get(2): " + engineInfoList.get(2));
		// 问题的核心：如何将List取出来 // 注意：此处list类型应该为java.util.Iterator
		Iterator<EngineInfo> iterator = engineInfoList.iterator();
		while (iterator.hasNext()) {
			EngineInfo engine = (EngineInfo) iterator.next(); // item.getName();
			

			//System.out.println("print ID:  " + engine.getId() + "print exception_T:  " + engine.getException_T());
		}

		return "result.jsf";

	}

	// 退出方法
	public String backOff() {
		return "index.jsf";
	}
	public String log() {
		emf = Persistence.createEntityManagerFactory("engine_diagnosis_system");
		em = emf.createEntityManager();

		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(engineInfo.getClass()));
		System.out.println("成功find");

		engineInfoList = em.createQuery(cq).getResultList();

		// engineInfoList.size();
		// engineInfoList.get(1);
		// System.out.println("打印输出size: " + engineInfoList.size());
		// System.out.println("打印输出listItem.get(2): " + engineInfoList.get(2));
		// 问题的核心：如何将List取出来 // 注意：此处list类型应该为java.util.Iterator
		Iterator<EngineInfo> iterator = engineInfoList.iterator();
		while (iterator.hasNext()) {
			EngineInfo engine = (EngineInfo) iterator.next(); // item.getName();
			

			//System.out.println("print ID:  " + engine.getId() + "print exception_T:  " + engine.getException_T());
		}
		return "log.jsf";
	}
}
