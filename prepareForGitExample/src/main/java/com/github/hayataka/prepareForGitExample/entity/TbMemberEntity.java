package com.github.hayataka.prepareForGitExample.entity;

import java.util.Date;

public class TbMemberEntity {

	private String name;
	private Date birthday;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TbMemberEntity [name=").append(name).append(", birthday=").append(birthday).append("]");
		return builder.toString();
	}
}
