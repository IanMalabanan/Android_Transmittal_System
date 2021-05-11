package com.example.transmittal_system_android;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ClsConnection {
    String serverip = "192.168.1.42";

    String classs = "net.sourceforge.jtds.jdbc.Driver";

    String dbname = //"TrialTransmittalDB";
    "TransmittalSystem";

    String dbHRIS = "HRIS";

    String serverusernname = "sa";

    String serverpassword = "P@55w0rd";

    public Connection Conn() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        Connection conn = null;

        String conUrl;

        try {
            Class.forName(classs).newInstance();

            conUrl = "jdbc:jtds:sqlserver://" + serverip + ";"
                    + "databaseName=" + dbname + ";user=" + serverusernname + ";password="
                    + serverpassword + ";";

            conn = DriverManager.getConnection(conUrl);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }

        return conn;
    }

    public Connection ConnHRIS() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        Connection conn = null;

        String conUrl;

        try {
            Class.forName(classs).newInstance();

            conUrl = "jdbc:jtds:sqlserver://" + serverip + ";"
                    + "databaseName=" + dbHRIS + ";user=" + serverusernname + ";password="
                    + serverpassword + ";";

            conn = DriverManager.getConnection(conUrl);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }

        return conn;
    }
}
