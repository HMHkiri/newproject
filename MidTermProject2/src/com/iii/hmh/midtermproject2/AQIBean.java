package com.iii.hmh.midtermproject2;

import java.sql.Timestamp;

public class AQIBean {
	private int Siteid;
	private String SiteName;
	private String Country;
	private int AQI;
	private String status;
	private int PM25;
	private Timestamp PublishTime;
	
	public AQIBean() {
	}
	
	public AQIBean(int Siteid, String SiteName, String Country, int  AQI, String status, int pm,Timestamp ptime) {
		this.Siteid=Siteid;
		this.SiteName=SiteName;
		this.Country=Country;
		this.AQI=AQI;
		this.status=status;
		this.PM25=pm;
		this.PublishTime=ptime;
	}

	public int getSiteid() {
		return Siteid;
	}
	public void setSiteid(int siteid) {
		this.Siteid = siteid;
	}
	public String getSiteName() {
		return SiteName;
	}
	public void setSiteName(String siteName) {
		this.SiteName = siteName;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		this.Country = country;
	}
	public int getAQI() {
		return AQI;
	}
	public void setAQI(int aQI) {
		this.AQI = aQI;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getPM25() {
		return PM25;
	}
	public void setPM25(int pM25) {
		this.PM25 = pM25;
	}
	public Timestamp getPublishTime() {
		return PublishTime;
	}
	public void setPublishTime(Timestamp publishTime) {
		this.PublishTime = publishTime;
	}

}
