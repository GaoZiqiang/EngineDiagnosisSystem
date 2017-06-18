package cn.edu.sdut.softlab.service;

public class ParameterUtil {
	
	private int T;
	private double P1;
	private double P2;
	private int L1;
	private int L2;
	private int X;
	
	public int getT() {
		return T;
	}
	public void setT(int t) {
		T = t;
	}
	public double getP1() {
		return P1;
	}
	public void setP1(double p1) {
		P1 = p1;
	}
	public double getP2() {
		return P2;
	}
	public void setP2(double p2) {
		P2 = p2;
	}
	public int getL1() {
		return L1;
	}
	public void setL1(int l1) {
		L1 = l1;
	}
	public int getL2() {
		return L2;
	}
	public void setL2(int l2) {
		L2 = l2;
	}
	public int getX() {
		return X;
	}
	public void setX(int x) {
		X = x;
	}
	
	public void Analysis() {
		System.out.println("开始进行参数分析!");
		if (T > 95) {
			System.out.println("温度过高!");
		} else {
			if (P1 > 1.1 && P1 < 1.3) {
				if (P2 > 0.8 && P2 < 1.0) {
					if (L1 == 1) {
						if (L2 == 0) {
							if (X > 8) {
								System.out.println("结束!");
							} else {
								System.out.println("风扇传动带脱落!");
							}
						} else {
							System.out.println("水泵轴与叶轮松脱!");
						}
					} else {
						System.out.println("节温器主筏门脱落!");
					}
				} else {
					System.out.println("汽缸密封故障!");
				}
			} else {
				System.out.println("冷却系统漏水!");
			}
		}
	}
}
