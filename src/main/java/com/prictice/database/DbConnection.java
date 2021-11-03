package com.prictice.database;

import java.sql.*;

/**
 * @author 苏博
 * @className: DbConnection.java
 * @package me.kafeitu
 * @description: 数据库连接测试
 * @date 2019/8/8 18:41
 */
public class DbConnection {

    private final static String MYSQL_URL = "jdbc:mysql://127.0.0.1:3306/?user=root&password=123456&useUnicode=true&characterEncoding=UTF8";

    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        //连接数据库
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(MYSQL_URL);
            Statement s =  conn.createStatement();
            System.out.println(!conn.isClosed()?"连接成功":"连接失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //连接，事务默认就是自动提交的。 关闭自动提交。
            conn.setAutoCommit(false);//关闭自动提交事务

            //name varchar(4)
            String sql = "update student.student set name = ? where id = ?";
            ps = conn.prepareStatement(sql);

            ps.setString(1, "张一");
            ps.setInt(2, 1);
            ps.executeUpdate();

            //会发生字段超长异常，导致回滚
            ps.setString(1, "张二三四五六");
            ps.setInt(2, 2);
            ps.executeUpdate();

            //成功： 提交事务。
            conn.commit();
            System.out.println("事务成功提交");

        } catch (Exception e) {
            try {
                //事变： 回滚事务
                conn.rollback();
                System.out.println("发生异常,回滚事务");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try {
                ps.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
