package com.iii.hmh.midtermproject2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class AQIDAOjdbc implements AQIDAO {
	
	DataSource ds = null;
	Connection conn =null;

	public AQIDAOjdbc() {
		try {
			Context context = new InitialContext();
			ds = (DataSource) context.lookup("java:comp/env/jdbc/midtermproject");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	

	private static final String INSERT_STMT = 
			"INSERT INTO midtermproject VALUES (?, ?, ?, ?, ?, ?, ?)";
	@Override
	public int insert(AQIBean aqib) throws SQLException {
		int updateCount = 0;
		conn = ds.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(INSERT_STMT);
		pstmt.setInt(1, aqib.getSiteid());
		pstmt.setString(2, aqib.getSiteName());
		pstmt.setString(3, aqib.getCountry());
		pstmt.setInt(4, aqib.getAQI());
		pstmt.setString(5, aqib.getStatus());
		pstmt.setInt(6, aqib.getPM25());
		pstmt.setTimestamp(7, aqib.getPublishTime());
		updateCount = pstmt.executeUpdate();
		pstmt.close();
		conn.close();
		return updateCount;
	}
	
	private static final String UPDATE_STMT = 
			"UPDATE midtermproject SET SiteName=?, Country=?, AQI=?, Status=?, [PM2.5]=?,PublishTime=? WHERE Siteid=?";
	@Override
	public int update(AQIBean aqib) throws SQLException {
		int updateCount = 0;
		conn = ds.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(UPDATE_STMT);
		pstmt.setString(1, aqib.getSiteName());
		pstmt.setString(2, aqib.getCountry());
		pstmt.setInt(3, aqib.getAQI());
		pstmt.setString(4, aqib.getStatus());
		pstmt.setInt(5, aqib.getPM25());
		pstmt.setTimestamp(6, aqib.getPublishTime());
		pstmt.setInt(7, aqib.getSiteid());
		updateCount = pstmt.executeUpdate();
		pstmt.close();
		conn.close();
		return updateCount;
	}

	private static final String DELETE_STMT = 
			"DELETE FROM midtermproject WHERE Siteid=?";
	@Override
	public int delete(int Siteid) throws SQLException {

		int updateCount = 0;
		conn = ds.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(DELETE_STMT);
		pstmt.setInt(1, Siteid);
		updateCount = pstmt.executeUpdate();
		pstmt.close();
		conn.close();
		return updateCount;
	}
	private static final String GET_ONE_STMT = 
			"SELECT Siteid, SiteName, Country, AQI, Status, [PM2.5],PublishTime FROM midtermproject WHERE Siteid=?";
	@Override
	public AQIBean findBySiteid(int Siteid) throws SQLException {

		AQIBean aqib = null;
		conn = ds.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(GET_ONE_STMT);
		pstmt.setInt(1, Siteid);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			aqib = new AQIBean();
			aqib.setSiteid(rs.getInt("Siteid"));
			aqib.setSiteName(rs.getString("SiteName"));
			aqib.setCountry(rs.getString("Country"));
			aqib.setAQI(rs.getInt("AQI"));
			aqib.setStatus(rs.getString("Status"));
			aqib.setPM25(rs.getInt("PM2.5"));
			aqib.setPublishTime(rs.getTimestamp("PublishTime"));
		}
		rs.close();
		pstmt.close();
		conn.close();
		return aqib;
	}
	

	public void closeConn() throws SQLException {
		if (conn != null)
			conn.close();
	}

}
