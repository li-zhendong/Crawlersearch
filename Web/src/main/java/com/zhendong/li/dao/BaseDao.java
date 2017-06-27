package com.zhendong.li.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zhendong.li.dao.Service.BaseDaoService;
@Repository
public class BaseDao  implements BaseDaoService{
	private static Logger log=Logger.getLogger(BaseDao.class);
    private ComboPooledDataSource dataSource;

public ComboPooledDataSource getDataSource() {
	return dataSource;
}

public void setDataSource(ComboPooledDataSource dataSource) {
	this.dataSource = dataSource;
}
public Connection getconConnection() throws SQLException{
	return dataSource.getConnection();
}
//释放连接回连接池
public  void close(Connection conn,PreparedStatement pst,ResultSet rs) throws Exception{  
       if(rs!=null){  
           try {  
               rs.close();  
           } catch (SQLException e) {  
        	   log.error("Exception in C3p0Utils!", e);
               throw new Exception("数据库连接关闭出错!", e);            
           }  
       }  
       if(pst!=null){  
           try {  
               pst.close();  
           } catch (SQLException e) {  
        	   log.error("Exception in C3p0Utils!", e);
               throw new Exception("数据库连接关闭出错!", e);    
           }  
       }  
 
       if(conn!=null){  
           try {  
               conn.close();  
           } catch (SQLException e) {  
        	   log.error("Exception in C3p0Utils!", e);
               throw new Exception("数据库连接关闭出错!", e);    
           }  
       }  
   }
//释放连接回连接池
public static void close(Connection conn,Statement stat,ResultSet rs) throws Exception{  
     if(rs!=null){  
         try {  
             rs.close();  
         } catch (SQLException e) {  
      	   log.error("Exception in C3p0Utils!", e);
             throw new Exception("数据库连接关闭出错!", e);            
         }  
     }  
     if(stat!=null){  
         try {  
        	 stat.close();  
         } catch (SQLException e) {  
      	   log.error("Exception in C3p0Utils!", e);
             throw new Exception("数据库连接关闭出错!", e);    
         }  
     }  

     if(conn!=null){  
         try {  
             conn.close();  
         } catch (SQLException e) {  
      	   log.error("Exception in C3p0Utils!", e);
             throw new Exception("数据库连接关闭出错!", e);    
         }  
     }  
 } 
   
}