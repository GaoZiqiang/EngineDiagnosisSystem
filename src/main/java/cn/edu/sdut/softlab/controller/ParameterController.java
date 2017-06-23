package cn.edu.sdut.softlab.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
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
	String resultFlag = null;

	// 必须要有getter和setter方法
	public String getResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}
	private List<EngineInfo> newEngineInfoList;
	// 测试结果list
	private List<String> resultList = new ArrayList<String>();

	// private ArrayList<String> list = new ArrayList<String>;
	public List<String> getResultList() {
		return resultList;
	}

	public void setResultList(List<String> resultList) {
		this.resultList = resultList;
	}

	// resultList填充

	// newEngineInfo的getter和setter方法
	public TempEngineInfo getNewEngineInfo() {
		return newEngineInfo;
	}

	public void setNewEngineInfo(TempEngineInfo newEngineInfo) {
		this.newEngineInfo = newEngineInfo;
	}

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
	public String analysis() {
		// 1.获取T P1 P2等参数
		System.out.println("print engineInfo:  " + engineInfo.toString());

		
		engineInfo.setPressure_P1(newEngineInfo.getPressure_P1());
		engineInfo.setPressure_P2(newEngineInfo.getPressure_P2());
		engineInfo.setGap_L1(newEngineInfo.getGap_L1());
		engineInfo.setGap_L2(newEngineInfo.getGap_L2());
		engineInfo.setOffset_X(newEngineInfo.getOffset_X());

		// 2.逻辑判断

		// 情况一
		if (newEngineInfo.getEngineType().equals("gasolineEngine")
				&& newEngineInfo.getCoolingMethod().equals("coolant")) {
			if (newEngineInfo.getTem_T() > 80 && newEngineInfo.getTem_T() < 90) {
				System.out.println("温度过高!");
				resultFlag = "温度过高!";
				engineInfo.setException_T("温度过高!");
				// resultList.add("温度过高");

			} else {
				if (newEngineInfo.getPressure_P1() > 0.3 && newEngineInfo.getPressure_P1() < 0.5) {
					if (newEngineInfo.getPressure_P2() > 1.0 && newEngineInfo.getPressure_P2() < 1.2) {
						if (newEngineInfo.getGap_L1() == 1) {
							if (newEngineInfo.getGap_L2() == 0) {
								if (newEngineInfo.getOffset_X() > 8) {
									System.out.println("结束!");
									resultFlag = "测试结束，没有故障!";
									// resultList.add("测试结束，没有故障!");
								} else {
									System.out.println("冷却系统温度异常!");
									resultFlag = "冷却系统温度异常!";
									engineInfo.setException_X("冷却系统温度异常!");
									// resultList.add("风扇传动带脱落");
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
						System.out.println("压力较低!");
						resultFlag = "压力较低!";
						engineInfo.setException_P2("压力较低!");
					}
				} else {
					if (newEngineInfo.getPressure_P1() < 0.3) {
						System.out.println("水泵压力过低!");
						resultFlag = "水泵压力过低!";
						engineInfo.setException_P1("水泵压力过低!!");
					} else if (newEngineInfo.getPressure_P1() > 0.5) {
						System.out.println("水泵压力过高!");
						resultFlag = "水泵压力过高!";
						engineInfo.setException_P1("水泵压力过高!");
					}

					// resultList.add("冷却系统漏水");
				}
			}
		} else if (newEngineInfo.getEngineType().equals("dieselEngine")
				&& newEngineInfo.getCoolingMethod().equals("coolant")) {
			if (newEngineInfo.getTem_T() > 80 && newEngineInfo.getTem_T() < 90) {
				System.out.println("温度过高!");
				resultFlag = "温度过高!";
				engineInfo.setException_T("温度过高!");

			} else {
				if (newEngineInfo.getPressure_P1() > 0.3 && newEngineInfo.getPressure_P1() < 0.5) {
					if (newEngineInfo.getPressure_P2() > 3.0 && newEngineInfo.getPressure_P2() < 6.0) {
						if (newEngineInfo.getGap_L1() == 1) {
							if (newEngineInfo.getGap_L2() == 0) {
								if (newEngineInfo.getOffset_X() > 8) {
									System.out.println("结束!");
									resultFlag = "测试结束，没有故障!";
								} else {
									System.out.println("冷却系统温度异常!");
									resultFlag = "冷却系统温度异常!";
									engineInfo.setException_X("冷却系统温度异常!");
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
						System.out.println("压力较低!");
						resultFlag = "压力较低!";
						engineInfo.setException_P2("压力较低!");
					}
				} else {
					if (newEngineInfo.getPressure_P1() < 0.3) {
						System.out.println("水泵压力过低!");
						resultFlag = "水泵压力过低!";
						engineInfo.setException_P1("水泵压力过低!!");
					} else if (newEngineInfo.getPressure_P1() > 0.5) {
						System.out.println("水泵压力过高!");
						resultFlag = "水泵压力过高!";
						engineInfo.setException_P1("水泵压力过高!");
					}
				}
			}
		} else if (newEngineInfo.getEngineType().equals("gasolineEngine")
				&& newEngineInfo.getCoolingMethod().equals("antifreeze")) {
			if (newEngineInfo.getTem_T() > 95 && newEngineInfo.getTem_T() < 105) {
				System.out.println("温度过高!");
				resultFlag = "温度过高!";
				engineInfo.setException_T("温度过高!");

			} else {
				if (newEngineInfo.getPressure_P1() > 0.3 && newEngineInfo.getPressure_P1() < 0.53) {
					if (newEngineInfo.getPressure_P2() > 3.0 && newEngineInfo.getPressure_P2() < 6.0) {
						if (newEngineInfo.getGap_L1() == 1) {
							if (newEngineInfo.getGap_L2() == 0) {
								if (newEngineInfo.getOffset_X() > 8) {
									System.out.println("结束!");
									resultFlag = "测试结束，没有故障!";
								} else {
									System.out.println("冷却系统温度异常!");
									resultFlag = "冷却系统温度异常!";
									engineInfo.setException_X("冷却系统温度异常!");
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
						System.out.println("压力较低!");
						resultFlag = "压力较低!";
						engineInfo.setException_P2("压力较低!");
					}
				} else {
					if (newEngineInfo.getPressure_P1() < 0.3) {
						System.out.println("水泵压力过低!");
						resultFlag = "水泵压力过低!";
						engineInfo.setException_P1("水泵压力过低!!");
					} else if (newEngineInfo.getPressure_P1() > 0.5) {
						System.out.println("水泵压力过高!");
						resultFlag = "水泵压力过高!";
						engineInfo.setException_P1("水泵压力过高!");
					}
				}
			}
		} else if (newEngineInfo.getEngineType().equals("dieselEngine")
				&& newEngineInfo.getCoolingMethod().equals("antifreeze")) {
			if (newEngineInfo.getTem_T() > 95 && newEngineInfo.getTem_T() < 105) {
				System.out.println("温度过高!");
				resultFlag = "温度过高!";
				engineInfo.setException_T("温度过高!");

			} else {
				if (newEngineInfo.getPressure_P1() > 0.3 && newEngineInfo.getPressure_P1() < 0.5) {
					if (newEngineInfo.getPressure_P2() > 1.0 && newEngineInfo.getPressure_P2() < 1.2) {
						if (newEngineInfo.getGap_L1() == 1) {
							if (newEngineInfo.getGap_L2() == 0) {
								if (newEngineInfo.getOffset_X() > 8) {
									System.out.println("结束!");
									resultFlag = "测试结束，没有故障!";
								} else {
									System.out.println("冷却系统温度异常!");
									resultFlag = "冷却系统温度异常!";
									engineInfo.setException_X("冷却系统温度异常!");
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
						System.out.println("压力较低!");
						resultFlag = "压力较低!";
						engineInfo.setException_P2("压力较低!");
					}
				} else {
					if (newEngineInfo.getPressure_P1() < 0.3) {
						System.out.println("水泵压力过低!");
						resultFlag = "水泵压力过低!";
						engineInfo.setException_P1("水泵压力过低!!");
					} else if (newEngineInfo.getPressure_P1() > 0.5) {
						System.out.println("水泵压力过高!");
						resultFlag = "水泵压力过高!";
						engineInfo.setException_P1("水泵压力过高!");
					}
				}
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
		System.out.println("resultFlag:  " + resultFlag);

		//原因分析
		if (resultFlag.equals("温度过高!")) {
			resultList.add("1.节温器故障");
			resultList.add("2.冷却液不足");
			resultList.add("3.高温季节长时间低速大负荷行驶");
			resultList.add("4.水箱内部水垢严重，散热不好");
			resultList.add("5.冷却风扇故障");
			resultList.add("6.水路堵塞");
		} else if (resultFlag.equals("水泵压力过低!")) {
			resultList.add("1.水密封和轴承失效");
			resultList.add("2.发动机水道被水舵堵住");
		}else if(resultFlag.equals("水泵压力过高!")) {
			resultList.add("冷却液温度过高引起");
		}else if(resultFlag.equals("压力较低")) {
			resultList.add("1.汽缸垫损坏");
			resultList.add("2.进气门或排气门座密封不严");
			resultList.add("3.活塞坏开口间隙过大");
			resultList.add("4.气缸盖衬垫因烧蚀而漏气");
		}else if(resultFlag.equals("水泵轴与叶轮松脱!") || resultFlag.equals("节温器主筏门脱落!")) {
			resultList.add("水泵轴与叶轮脱松");
			
		}else if (resultFlag.equals("冷却系统温度异常!")) {
			resultList.add("风扇传送带脱落");
		}
		return "test.jsf";
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

	// 测试list
	public String listTest() {
		if (resultFlag.equals("温度过高!")) {
			resultList.add("1.节温器故障");
			resultList.add("2.冷却液不足");
			resultList.add("3.高温季节长时间低速大负荷行驶");
			resultList.add("4.水箱内部水垢严重，散热不好");
			resultList.add("5.冷却风扇故障");
			resultList.add("6.水路堵塞");
		} else if (resultFlag.equals("水泵压力过低!")) {
			resultList.add("1.水密封和轴承失效");
			resultList.add("2.发动机水道被水舵堵住");
		}else if(resultFlag.equals("水泵压力过高!")) {
			resultList.add("冷却液温度过高引起");
		}else if(resultFlag.equals("压力较低")) {
			resultList.add("1.汽缸垫损坏");
			resultList.add("2.进气门或排气门座密封不严");
			resultList.add("3.活塞坏开口间隙过大");
			resultList.add("4.气缸盖衬垫因烧蚀而漏气");
		}else if(resultFlag.equals("水泵轴与叶轮松脱!") || resultFlag.equals("节温器主筏门脱落!")) {
			resultList.add("水泵轴与叶轮脱松");
			
		}else if (resultFlag.equals("冷却系统温度异常!")) {
			resultList.add("风扇传送带脱落");
		}
		resultList.add("result1");
		resultList.add("result2");
		return "list.jsf";
	}
}
