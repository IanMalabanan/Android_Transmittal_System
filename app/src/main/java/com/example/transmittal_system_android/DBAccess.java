package com.example.transmittal_system_android;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBAccess {
    ClsConnection connectionClass;

    CallableStatement _callableStatement;

    public Boolean isSuccess = false;

    public String z = "",_empid = "", fname = "", FullName_FnameFirst = "", deptcode = ""
            , sectioncode = "", transmitter = "";

    public String[] array;

    ClsDTDataRecords clsDTDataRecords = new ClsDTDataRecords();

    ArrayList<ClsModelDataRecords> data =  new ArrayList<ClsModelDataRecords>();


    public boolean UserLogin(String _username,String _userpass) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            if (con == null) {
                z = "Error in Network Connection";
            } else {
                String SP = "{call AccountLogin(?,?)}";

                _callableStatement = con.prepareCall(SP);

                _callableStatement.setString(1, _username);

                _callableStatement.setString(2, _userpass);

                ResultSet rs = _callableStatement.executeQuery();

                if (rs.next()) {

                    z = "";
                    _empid = rs.getString("EmpID");

                    FullName_FnameFirst = rs.getString("FullName");

                    deptcode = rs.getString("department");

                    sectioncode = rs.getString("section");

                    isSuccess = true;

                } else {
                    z = "Access Denied";
                    isSuccess = false;
                }
            }
        } catch (Exception ex) {
            isSuccess = false;
            z = ex.toString();
        }

        return isSuccess;
    }

    public boolean GetAllRecordsForReceiving() {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            //String query = "{call GetAllRecordsForReceiving()}";

            //CallableStatement callableStatement = con.prepareCall(query);

            String query = "SELECT  [DocID] ,[RrNo] FROM [TransmittalSystem].[dbo].[tbl_TransmittalMasterList] where (IsTransmittalFinished is null or IsTransmittalFinished = 0) and (IsAccountingReceived = 0 or IsAccountingReceived is null) and (IsDocumentCompleted = 0 or IsDocumentCompleted is null) and ltrim(rtrim(RrNo)) is not null and ltrim(rtrim(RrNo)) != '-'  and ltrim(rtrim(RrNo)) != '' order by DocID desc";

            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {

                ClsModelDataRecords itemPanels = new ClsModelDataRecords();

                itemPanels.setRrNo(rs.getString("RRNo"));

                data.add(itemPanels);

                clsDTDataRecords.setDataRecordsArrayList(data);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllRecordsForReceivingByRRNo(String RRno) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "SELECT  [DocID] ,[RrNo] FROM [dbo].[tbl_TransmittalMasterList] where (IsTransmittalFinished is null or IsTransmittalFinished = 0) and (IsAccountingReceived = 0 or IsAccountingReceived is null) and (IsDocumentCompleted = 0 or IsDocumentCompleted is null) and ltrim(rtrim(RrNo)) is not null and ltrim(rtrim(RrNo)) != '-'  and ltrim(rtrim(RrNo)) != '' and ltrim(rtrim(RrNo)) like '%" + RRno.trim() + "%' order by DocID desc";

            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {

                ClsModelDataRecords itemPanels = new ClsModelDataRecords();

                itemPanels.setRrNo(rs.getString("RRNo"));

                data.add(itemPanels);

                clsDTDataRecords.setDataRecordsArrayList(data);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean SKPI_GetAllEmployeesByEmpID(String empid) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.ConnHRIS();

            String query = "{call SKPI_GetAllEmployeesByEmpID(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1, empid);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {
                transmitter = rs.getString("FullName_LnameFirst");

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }




    public boolean SaveAccountingData(String RRNo, String TransmittedBy, String AccntReceivedBy,
           String ReceivedDocuments, boolean Stat) {

        String query = "{call SaveAccountingData(?,?,?,?,?)}";

        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            CallableStatement callableStatement;

            callableStatement = con.prepareCall(query);

            callableStatement.setString(1, RRNo);

            callableStatement.setString(2, TransmittedBy);

            callableStatement.setString(3, AccntReceivedBy);

            callableStatement.setString(4, ReceivedDocuments);

            callableStatement.setBoolean(5, Stat);

            callableStatement.executeUpdate();

            con.setAutoCommit(true);

            isSuccess = true;

            z = "Record Has Been Updated";

        } catch (Exception ex) {
            z = ex.getMessage().toString();
            isSuccess = false;
        }

        return isSuccess;
    }

}
