package com.visa.vo;

import java.util.Date;

public class Department {
	private Date createData;
	private String name;
	private String oldName;
	
	public Date getCreateData() {
		return createData;
	}

	public void setCreateData(Date createData) {
		this.createData = createData;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public Department(String name, String oldName) {
		super();
		this.name = name;
		this.oldName = oldName;
	}

	public Department() {
		super();
	}
	
}
