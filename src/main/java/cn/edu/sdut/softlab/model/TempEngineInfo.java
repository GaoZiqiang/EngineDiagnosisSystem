package cn.edu.sdut.softlab.model;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Named;

@RequestScoped
@Named("tempEngine")
@Default
public class TempEngine {
	private Double tem_T;
	private Double pressure_P1;
	private Double pressure_P2;
	private Double gap_L1;
	private Double gap_L2;
	private Double offset_X;

	private String exception_T;
	private String exception_P1;
	private String exception_P2;
	private String exception_L1;
	private String exception_L2;
	private String exception_X;

	public Double getTem_T() {
		return tem_T;
	}

	public void setTem_T(Double tem_T) {
		this.tem_T = tem_T;
	}

	public Double getPressure_P1() {
		return pressure_P1;
	}

	public void setPressure_P1(Double pressure_P1) {
		this.pressure_P1 = pressure_P1;
	}

	public Double getPressure_P2() {
		return pressure_P2;
	}

	public void setPressure_P2(Double pressure_P2) {
		this.pressure_P2 = pressure_P2;
	}

	public Double getGap_L1() {
		return gap_L1;
	}

	public void setGap_L1(Double gap_L1) {
		this.gap_L1 = gap_L1;
	}

	public Double getGap_L2() {
		return gap_L2;
	}

	public void setGap_L2(Double gap_L2) {
		this.gap_L2 = gap_L2;
	}

	public Double getOffset_X() {
		return offset_X;
	}

	public void setOffset_X(Double offset_X) {
		this.offset_X = offset_X;
	}

	public String getException_T() {
		return exception_T;
	}

	public void setException_T(String exception_T) {
		this.exception_T = exception_T;
	}

	public String getException_P1() {
		return exception_P1;
	}

	public void setException_P1(String exception_P1) {
		this.exception_P1 = exception_P1;
	}

	public String getException_P2() {
		return exception_P2;
	}

	public void setException_P2(String exception_P2) {
		this.exception_P2 = exception_P2;
	}

	public String getException_L1() {
		return exception_L1;
	}

	public void setException_L1(String exception_L1) {
		this.exception_L1 = exception_L1;
	}

	public String getException_L2() {
		return exception_L2;
	}

	public void setException_L2(String exception_L2) {
		this.exception_L2 = exception_L2;
	}

	public String getException_X() {
		return exception_X;
	}

	public void setException_X(String exception_X) {
		this.exception_X = exception_X;
	}

}
