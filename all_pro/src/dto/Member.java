package dto;

import java.util.Date;

public class Member {

	private String mem_id;
	private String mem_pass;
	private String mem_name;
	private int mem_age;
	private String mem_ph;
	private String mem_addr;
	private String mem_job;
	private Date mem_date;
	private int mem_rank;
	
	public Member() {
		// TODO Auto-generated constructor stub
	}

	public Member(String mem_id, String mem_pass, String mem_name, int mem_age, String mem_ph,
			String mem_addr, String mem_job, Date mem_date, int mem_rank) {
		super();
		this.mem_id = mem_id;
		this.mem_pass = mem_pass;
		this.mem_name = mem_name;
		this.mem_age = mem_age;
		this.mem_ph = mem_ph;
		this.mem_addr = mem_addr;
		this.mem_job = mem_job;
		this.mem_date = mem_date;
		this.mem_rank = mem_rank;
	}


	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getMem_pass() {
		return mem_pass;
	}

	public void setMem_pass(String mem_pass) {
		this.mem_pass = mem_pass;
	}

	public String getMem_name() {
		return mem_name;
	}

	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}

	public int getMem_age() {
		return mem_age;
	}

	public void setMem_age(int mem_age) {
		this.mem_age = mem_age;
	}

	public String getMem_ph() {
		return mem_ph;
	}

	public void setMem_ph(String mem_ph) {
		this.mem_ph = mem_ph;
	}

	public String getMem_addr() {
		return mem_addr;
	}

	public void setMem_addr(String mem_addr) {
		this.mem_addr = mem_addr;
	}

	public String getMem_job() {
		return mem_job;
	}

	public void setMem_job(String mem_job) {
		this.mem_job = mem_job;
	}

	public Date getMem_date() {
		return mem_date;
	}

	public void setMem_date(Date mem_date) {
		this.mem_date = mem_date;
	}

	public int getMem_rank() {
		return mem_rank;
	}

	public void setMem_rank(int mem_rank) {
		this.mem_rank = mem_rank;
	}

	@Override
	public String toString() {
		return "Member [mem_id=" + ", mem_pass=" + mem_pass + ", mem_name=" + mem_name
				+ ", mem_age=" + mem_age + ", mem_ph=" + mem_ph + ", mem_addr=" + mem_addr + ", mem_job=" + mem_job
				+ ", mem_date=" + mem_date + ", mem_rank=" + mem_rank + "]";
	}
	

}