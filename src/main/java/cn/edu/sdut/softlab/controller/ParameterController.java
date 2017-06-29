package cn.edu.sdut.softlab.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;

import antlr.StringUtils;
import cn.edu.sdut.softlab.model.EngineInfo;
import cn.edu.sdut.softlab.model.TempEngineInfo;

@Named("parameterController")
@RequestScoped
public class ParameterController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private TempEngineInfo newEngineInfo;

	@Inject
	private EngineInfo engineInfo;

	EntityManagerFactory emf;
	EntityManager em;
	// 测试结果
	String resultT = null;
	String resultP1 = null;
	String resultP2 = null;
	String resultL1 = null;
	String resultX = null;

	private List<EngineInfo> engineInfoList = null;

	public List<EngineInfo> getEngineInfoList() {
		return engineInfoList;
	}

	public void setEngineInfoList(List<EngineInfo> engineInfoList) {
		this.engineInfoList = engineInfoList;
	}

	private List<EngineInfo> newEngineInfoList;

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
	// 必须要有getter和setter方法

	public String getResultT() {
		return resultT;
	}

	public void setResultT(String resultT) {
		this.resultT = resultT;
	}

	public String getResultP1() {
		return resultP1;
	}

	public void setResultP1(String resultP1) {
		this.resultP1 = resultP1;
	}

	public String getResultP2() {
		return resultP2;
	}

	public void setResultP2(String resultP2) {
		this.resultP2 = resultP2;
	}

	public String getResultL1() {
		return resultL1;
	}

	public void setResultL1(String resultL1) {
		this.resultL1 = resultL1;
	}

	public String getResultX() {
		return resultX;
	}

	public void setResultX(String resultX) {
		this.resultX = resultX;
	}

	// 故障分析list
	private List<String> resultListT = new ArrayList<String>();
	private List<String> resultListP1 = new ArrayList<String>();
	private List<String> resultListP2 = new ArrayList<String>();
	private List<String> resultListL1 = new ArrayList<String>();
	private List<String> resultListX = new ArrayList<String>();

	public List<String> getResultListT() {
		for (int i = 0; i < this.resultListT.size(); i++) {
			if (this.resultListT.get(i).equals("[")) {
				this.resultListT.remove("[");
			}
			if (this.resultListT.get(i).equals("]")) {
				this.resultListT.remove("]");
			}
		}
		return resultListT;
	}

	public void setResultListT(List<String> resultListT) {
		this.resultListT = resultListT;
	}

	public List<String> getResultListP1() {
		return resultListP1;
	}

	public void setResultListP1(List<String> resultListP1) {
		this.resultListP1 = resultListP1;
	}

	public List<String> getResultListP2() {
		return resultListP2;
	}

	public void setResultListP2(List<String> resultListP2) {
		this.resultListP2 = resultListP2;
	}

	public List<String> getResultListL1() {
		return resultListL1;
	}

	public void setResultListL1(List<String> resultListL1) {
		this.resultListL1 = resultListL1;
	}

	public List<String> getResultListX() {
		return resultListX;
	}

	public void setResultListX(List<String> resultListX) {
		this.resultListX = resultListX;
	}

	// newEngineInfo的getter和setter方法
	public TempEngineInfo getNewEngineInfo() {
		return newEngineInfo;
	}

	public void setNewEngineInfo(TempEngineInfo newEngineInfo) {
		this.newEngineInfo = newEngineInfo;
	}

	// 统计报表

	// 参数分析
	public String analysis() {
		// 1.获取T P1 P2等参数
		System.out.println("print engineInfo:  " + engineInfo.toString());
		engineInfo.setTem_T(newEngineInfo.getTem_T());
		engineInfo.setPressure_P1(newEngineInfo.getPressure_P1());
		engineInfo.setPressure_P2(newEngineInfo.getPressure_P2());
		engineInfo.setGap_L1(newEngineInfo.getGap_L1());
		engineInfo.setGap_L2(newEngineInfo.getGap_L2());
		engineInfo.setOffset_X(newEngineInfo.getOffset_X());

		// 2.逻辑判断

		// 1.加冷却液 汽油机
		if (newEngineInfo.getEngineType().equals("gasolineEngine")
				&& newEngineInfo.getCoolingMethod().equals("coolant")) {
			// 冷却液温度T
			if (newEngineInfo.getTem_T() > 80 && newEngineInfo.getTem_T() < 90) {
				resultT = "正常";
			} else {
				System.out.println("冷却液温度过高!");
				resultT = "冷却液温度过高!";
				engineInfo.setException_T("冷却液温度过高!");
			}
			// 水泵压力P1
			if (newEngineInfo.getPressure_P1() < 0.3) {
				System.out.println("水泵压力过低!");
				resultP1 = "水泵压力过低!";
				engineInfo.setException_P1("水泵压力过低!");
			} else if (newEngineInfo.getPressure_P1() > 0.5) {
				System.out.println("水泵压力过resultL1高!");
				resultP1 = "水泵压力过高!";
				engineInfo.setException_P1("水泵压力过高!");
			} else {
				resultP1 = "正常";
			}
			// 汽缸压缩压力P2
			if (newEngineInfo.getPressure_P2() > 1.0 && newEngineInfo.getPressure_P2() < 1.2) {
				resultP2 = "正常";
			} else {
				System.out.println("压力较低!");
				resultP2 = "压力较低!";
				engineInfo.setException_P2("压力较低!");
			}
			// 水泵轴与叶轮径向间隙L
			if (newEngineInfo.getGap_L1() < 0.04) {
				resultL1 = "正常";
			} else {
				System.out.println("水泵压力较明显!");
				resultL1 = "水泵震动较明显!";
				engineInfo.setException_L1("水泵震动较明显!");
			}
			// 风扇传送带偏移量X
			if (newEngineInfo.getOffset_X() > 8) {
				System.out.println("冷却系统温度异常!");
				resultX = "冷却系统温度异常!";
				engineInfo.setException_X("冷却系统温度异常!");
			} else {
				resultX = "正常";
			}
		}
		// 2.加冷却液 柴油机
		if (newEngineInfo.getEngineType().equals("dieselEngine")
				&& newEngineInfo.getCoolingMethod().equals("coolant")) {
			// 冷却液温度T
			if (newEngineInfo.getTem_T() > 80 && newEngineInfo.getTem_T() < 90) {
				resultT = "正常";
			} else {
				System.out.println("冷却液温度过高!");
				resultT = "冷却液温度过高!";
				engineInfo.setException_T("冷却液温度过高!");
			}
			// 水泵压力P1
			if (newEngineInfo.getPressure_P1() < 0.3) {
				System.out.println("水泵压力过低!");
				resultP1 = "水泵压力过低!";
				engineInfo.setException_P1("水泵压力过低!");
			} else if (newEngineInfo.getPressure_P1() > 0.5) {
				System.out.println("水泵压力过高!");
				resultP1 = "水泵压力过高!";
				engineInfo.setException_P1("水泵压力过高!");
			} else {
				resultP1 = "正常";
			}
			// 汽缸压缩压力P2
			if (newEngineInfo.getPressure_P2() > 3.0 && newEngineInfo.getPressure_P2() < 6.2) {
				resultP2 = "正常";
			} else {
				System.out.println("压力较低!");
				resultP1 = "压力较低!";
				engineInfo.setException_P2("压力较低!");
			}
			// 水泵轴与叶轮径向间隙L
			if (newEngineInfo.getGap_L1() < 0.04) {
				resultL1 = "正常";
			} else {
				System.out.println("水泵压力较明显!");
				resultL1 = "水泵震动较明显!";
				engineInfo.setException_L1("水泵震动较明显!");
			}
			// 风扇传送带偏移量X
			if (newEngineInfo.getOffset_X() > 8) {
				System.out.println("冷却系统温度异常!");
				resultX = "冷却系统温度异常!";
				engineInfo.setException_X("冷却系统温度异常!");
			} else {
				resultX = "正常";
			}
		}
		// 3.加防冻剂 汽油机
		if (newEngineInfo.getEngineType().equals("gasolineEngine")
				&& newEngineInfo.getCoolingMethod().equals("antifreeze")) {
			// 冷却液温度T
			if (newEngineInfo.getTem_T() > 95 && newEngineInfo.getTem_T() < 105) {
				resultT = "正常";
			} else {
				System.out.println("冷却液温度过高!");
				resultT = "冷却液温度过高!";
				engineInfo.setException_T("冷却液温度过高!");
			}
			// 水泵压力P1
			if (newEngineInfo.getPressure_P1() < 0.3) {
				System.out.println("水泵压力过低!");
				resultP1 = "水泵压力过低!";
				engineInfo.setException_P1("水泵压力过低!");
			} else if (newEngineInfo.getPressure_P1() > 0.5) {
				System.out.println("水泵压力过高!");
				resultP1 = "水泵压力过高!";
				engineInfo.setException_P1("水泵压力过高!");
			} else {
				resultP1 = "正常";
			}
			// 汽缸压缩压力P2
			if (newEngineInfo.getPressure_P2() > 1.0 && newEngineInfo.getPressure_P2() < 1.2) {
				resultP2 = "正常";
			} else {
				System.out.println("压力较低!");
				resultP2 = "压力较低!";
				engineInfo.setException_P2("压力较低!");
			}
			// 水泵轴与叶轮径向间隙L
			if (newEngineInfo.getGap_L1() < 0.04) {
				resultL1 = "正常";
			} else {
				System.out.println("水泵压力较明显!");
				resultL1 = "水泵震动较明显!";
				engineInfo.setException_L1("水泵震动较明显!");
			}
			// 风扇传送带偏移量X
			if (newEngineInfo.getOffset_X() > 8) {
				System.out.println("冷却系统温度异常!");
				resultX = "冷却系统温度异常!";
				engineInfo.setException_X("冷却系统温度异常!");
			} else {
				resultX = "正常";
			}
		}
		// 4.加防冻剂 柴油机
		if (newEngineInfo.getEngineType().equals("dieselEngine")
				&& newEngineInfo.getCoolingMethod().equals("antifreeze")) {
			// 冷却液温度T
			if (newEngineInfo.getTem_T() > 95 && newEngineInfo.getTem_T() < 105) {
				resultT = "正常";
			} else {
				System.out.println("冷却液温度过高!");
				resultT = "冷却液温度过高!";
				engineInfo.setException_T("冷却液温度过高!");
			}
			// 水泵压力P1
			if (newEngineInfo.getPressure_P1() < 0.3) {
				System.out.println("水泵压力过低!");
				resultP1 = "水泵压力过低!";
				engineInfo.setException_P1("水泵压力过低!");
			} else if (newEngineInfo.getPressure_P1() > 0.5) {
				System.out.println("水泵压力过高!");
				resultP1 = "水泵压力过高!";
				engineInfo.setException_P1("水泵压力过高!");
			} else {
				resultP1 = "正常";
			}
			// 汽缸压缩压力P2
			if (newEngineInfo.getPressure_P2() > 3.0 && newEngineInfo.getPressure_P2() < 6.0) {
				resultP2 = "正常";
			} else {
				System.out.println("压力较低!");
				resultP2 = "压力较低!";
				engineInfo.setException_P2("压力较低!");
			}
			// 水泵轴与叶轮径向间隙L
			if (newEngineInfo.getGap_L1() < 0.04) {
				resultL1 = "正常";
			} else {
				System.out.println("水泵压力较明显!");
				resultL1 = "水泵震动较明显!";
				engineInfo.setException_L1("水泵震动较明显!");
			}
			// 风扇传送带偏移量X
			if (newEngineInfo.getOffset_X() > 8) {
				System.out.println("冷却系统温度异常!");
				resultX = "冷却系统温度异常!";
				engineInfo.setException_X("冷却系统温度异常!");
			} else {
				resultX = "正常";
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
		// System.out.println("resultFlag: " + resultFlag);

		// 原因分析
		System.out.println("resultT:  " + resultT.toString());

		if (resultT.equals("冷却液温度过高!")) {
			resultListT.add("1.节温器故障" + "'\r\n'");
			resultListT.add("2.冷却液不足" + "'\r\n'");
			resultListT.add("3.高温季节长时间低速大负荷行驶\r\n");
			resultListT.add("4.水箱内部水垢严重，散热不好\r\n");
			resultListT.add("5.冷却风扇故障\r\n");
			resultListT.add("6.水路堵塞\r\n");
			System.out.println("打印resultListT:  " + resultListT.toString());
		}
		if (resultL1.equals("水泵震动较明显!")) {
			resultListL1.add("水泵轴与叶轮脱松");

		}
		if (resultP1.equals("水泵压力过低!")) {
			resultListP1.add("1.水密封和轴承失效");
			resultListP1.add("2.发动机水道被水舵堵住");
		}
		if (resultP1.equals("水泵压力过高!")) {
			resultListP1.add("冷却液温度过高引起");
		}
		if (resultP2.equals("压力较低!")) {
			resultListP2.add("1.汽缸垫损坏");
			resultListP2.add("2.进气门或排气门座密封不严");
			resultListP2.add("3.活塞坏开口间隙过大");
			resultListP2.add("4.气缸盖衬垫因烧蚀而漏气");
		}
		if (resultX.equals("冷却系统温度异常!")) {
			resultListX.add("风扇传送带脱落");
		}
		return "result.jsf";
	}

	public void testDouble() {
		// String stringDemo = "22.32";
		System.out.println("print Double:  ");
	}

	/*
	 * * 将所有输入记录遍历出来
	 * 
	 */
	public String result() {
		emf = Persistence.createEntityManagerFactory("engine_diagnosis_system");
		em = emf.createEntityManager();

		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(engineInfo.getClass()));
		System.out.println("成功find");

		engineInfoList = em.createQuery(cq).getResultList();

		newEngineInfoList = em.createQuery("select engine from EngineInfo engine order by engine.id desc")
				.getResultList();
		Iterator<EngineInfo> newIterator = newEngineInfoList.iterator();
		while (newIterator.hasNext()) {
			EngineInfo newEngine = (EngineInfo) newIterator.next(); // item.getName();

			System.out.println("print newId:  " + newEngine.getId());
		}

		Iterator<EngineInfo> iterator = engineInfoList.iterator();
		while (iterator.hasNext()) {
			EngineInfo engine = (EngineInfo) iterator.next(); // item.getName();
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

		newEngineInfoList = em.createQuery("select engine from EngineInfo engine order by engine.id desc")
				.getResultList();
		Iterator<EngineInfo> newIterator = newEngineInfoList.iterator();
		while (newIterator.hasNext()) {
			EngineInfo newEngine = (EngineInfo) newIterator.next(); // item.getName();

			System.out.println("print newId:  " + newEngine.getId());
		}
		// engineInfoList.size();
		// engineInfoList.get(1);
		// System.out.println("打印输出size: " + engineInfoList.size());
		// System.out.println("打印输出listItem.get(2): " + engineInfoList.get(2));
		// 问题的核心：如何将List取出来 // 注意：此处list类型应该为java.util.Iterator
		Iterator<EngineInfo> iterator = engineInfoList.iterator();
		while (iterator.hasNext()) {
			EngineInfo engine = (EngineInfo) iterator.next(); // item.getName();

			// System.out.println("print ID: " + engine.getId() + "print
			// exception_T: " + engine.getException_T());
		}
		return "log.jsf";
	}
}
