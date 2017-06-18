package cn.edu.sdut.softlab.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the engine_info database table.
 * 
 */
@Entity
@Table(name="engine_info")
@NamedQuery(name="EngineInfo.findAll", query="SELECT e FROM EngineInfo e")
public class EngineInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="\"exception_L1\"")
	private String exception_L1;

	@Column(name="\"exception_L2\"")
	private String exception_L2;

	@Column(name="\"exception_P1\"")
	private String exception_P1;

	@Column(name="\"exception_P2\"")
	private String exception_P2;

	@Column(name="\"exception_T\"")
	private String exception_T;

	@Column(name="\"exception_X\"")
	private String exception_X;

	@Column(name="\"gap_L1\"")
	private double gap_L1;

	@Column(name="\"gap_L2\"")
	private double gap_L2;

	@Column(name="\"offset_X\"")
	private double offset_X;

	@Column(name="\"pressure_P1\"")
	private double pressure_P1;

	@Column(name="\"pressure_P2\"")
	private double pressure_P2;

	@Column(name="\"tem_T\"")
	private double tem_T;

	@Temporal(TemporalType.DATE)
	private Date time;

	public EngineInfo() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getException_L1() {
		return this.exception_L1;
	}

	public void setException_L1(String exception_L1) {
		this.exception_L1 = exception_L1;
	}

	public String getException_L2() {
		return this.exception_L2;
	}

	public void setException_L2(String exception_L2) {
		this.exception_L2 = exception_L2;
	}

	public String getException_P1() {
		return this.exception_P1;
	}

	public void setException_P1(String exception_P1) {
		this.exception_P1 = exception_P1;
	}

	public String getException_P2() {
		return this.exception_P2;
	}

	public void setException_P2(String exception_P2) {
		this.exception_P2 = exception_P2;
	}

	public String getException_T() {
		return this.exception_T;
	}

	public void setException_T(String exception_T) {
		this.exception_T = exception_T;
	}

	public String getException_X() {
		return this.exception_X;
	}

	public void setException_X(String exception_X) {
		this.exception_X = exception_X;
	}

	public double getGap_L1() {
		return this.gap_L1;
	}

	public void setGap_L1(double gap_L1) {
		this.gap_L1 = gap_L1;
	}

	public double getGap_L2() {
		return this.gap_L2;
	}

	public void setGap_L2(double gap_L2) {
		this.gap_L2 = gap_L2;
	}

	public double getOffset_X() {
		return this.offset_X;
	}

	public void setOffset_X(double offset_X) {
		this.offset_X = offset_X;
	}

	public double getPressure_P1() {
		return this.pressure_P1;
	}

	public void setPressure_P1(double pressure_P1) {
		this.pressure_P1 = pressure_P1;
	}

	public double getPressure_P2() {
		return this.pressure_P2;
	}

	public void setPressure_P2(double pressure_P2) {
		this.pressure_P2 = pressure_P2;
	}

	public double getTem_T() {
		return this.tem_T;
	}

	public void setTem_T(double tem_T) {
		this.tem_T = tem_T;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}