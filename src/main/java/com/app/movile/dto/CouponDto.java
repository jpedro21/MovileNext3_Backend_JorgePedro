package com.app.movile.dto;


import javax.validation.constraints.NotNull;

import org.joda.time.LocalDate;

public class CouponDto {
	
	@NotNull
	private String code;
	@NotNull
	private LocalDate iniDate;
	@NotNull
	private LocalDate endDate;
	@NotNull
	private String text;
	private RestrictionDto restrictions;
	private ActionDto action;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public LocalDate getIniDate() {
		return iniDate;
	}
	public void setIniDate(LocalDate iniDate) {
		this.iniDate = iniDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public RestrictionDto getRestrictions() {
		return restrictions;
	}
	public void setRestrictions(RestrictionDto restrictions) {
		this.restrictions = restrictions;
	}
	public ActionDto getAction() {
		return action;
	}
	public void setAction(ActionDto action) {
		this.action = action;
	}
}
