package com.iii.hmh.midtermproject2;


import java.sql.*;


public interface AQIDAO {

	public int insert(AQIBean aqib) throws SQLException;
	public int update(AQIBean aqib) throws SQLException;
	public int delete(int Siteid) throws SQLException;
	public AQIBean findBySiteid(int Siteid) throws SQLException;
	public void closeConn() throws SQLException;
}
