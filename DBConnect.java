package database;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import entities.*;
import  java.util.*;

public class DBConnect  {


    private static final String db = "jdbc:mysql://192.168.0.101:3306/hw4";
    private static final String user = "android";
    private static final String pass = "";




        static Connection con = null;
        public static Connection getConnection()
        {
            if (con != null) return con;
            // get db, user, pass from settings file
            return getConnection(db, user, pass);
        }

        private static Connection getConnection(String db_name,String user_name,String password)
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                con=DriverManager.getConnection(db_name, user_name, password);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return con;
        }
    }







