package com.zhendong.li.dao.Service;

import java.io.Serializable;  
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;  
/** 
 * 接口 
 * @author admin 
 */  
public interface BaseDaoService{  
	/**
	 * 获取连接
	 * @return
	 * @throws SQLException
	 */
	public Connection getconConnection() throws SQLException;
	/**
	 * 关闭连接
	 */
	public  void close(Connection conn,PreparedStatement pst,ResultSet rs) throws Exception;
	
}