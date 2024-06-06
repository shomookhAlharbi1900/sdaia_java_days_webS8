package org.example.dao;

import org.example.dto.jobsFilterDto;
import org.example.models.jobs;

import java.sql.*;
import java.util.ArrayList;

public class jobsDAO {
    private static final String URL = "jdbc:sqlite:C:\\Users\\dev\\IdeaProjects\\HrApiDay5\\hr.db";
    private static final String SELECT_ALL_jobs = "select * from jobs";
    private static final String SELECT_ONE_id_job = "select * from jobs where job_id = ?";
    private static final String INSERT_jobs = " insert into jobs values (?,?,?,?)";
    private static final String UPDATE_jobs = "update jobs set job_title = ? min_salary = ? , max_salary = ?,where job_id  = ?";
    private static final String DELETE_jobs = "delete from jobs where job_id = ?";
    private static final String SELECT_jobs_WITH_PAGINATION = "select * from jobs order by job_id limit ? offset ?";
    private static final String SELECT_jobs_WITH_MIN = "select * from jobs where min_salary = ?";
    private static final String SELECT_jobs_WITH_MIN_PAGINATION = "select * from jobs where min_salary = ? order by job_id limit ? offset ?";


    public void INSERT_jobs(jobs j) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(INSERT_jobs);
        st.setInt(1, j.getJob_id());
        st.setString(2, j.getJob_title());
        st.setDouble(3, j.getMin_salary());
        st.setDouble(4, j.getMax_salary());
        st.executeUpdate();
        conn.close();
    }

    public void UPDATE_jobs(jobs j) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(UPDATE_jobs);
        st.setInt(4, j.getJob_id());
        st.setString(1, j.getJob_title());
        st.setDouble(2, j.getMin_salary());
        st.setDouble(3, j.getMax_salary());
        st.executeUpdate();
        conn.close();
    }

    public void DELETE_jobs(int job_id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(DELETE_jobs);
        st.setInt(1, job_id);
        st.executeUpdate();
        conn.close();
    }

    public jobs SELECT_ONE_id_job(int job_id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(SELECT_ONE_id_job);
        st.setInt(1, job_id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            return new jobs(rs);
        } else {
            return null;
        }
    }

    public ArrayList<jobs> SELECT_ALL_jobs(jobsFilterDto filter) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st;
        if (filter.getMin_salary() != null && filter.getLimit() != null) {
            st = conn.prepareStatement(SELECT_jobs_WITH_MIN_PAGINATION);
            st.setDouble(1, filter.getMin_salary());
            st.setInt(2, filter.getLimit());
            st.setInt(3, filter.getOffset());
        } else if (filter.getMin_salary() != null) {
            st = conn.prepareStatement(SELECT_jobs_WITH_MIN);
            st.setDouble(1, filter.getMin_salary());
        } else if (filter.getLimit() != null) {
            st = conn.prepareStatement(SELECT_jobs_WITH_PAGINATION);
            st.setInt(1, filter.getLimit());
            st.setInt(2, filter.getOffset());
        } else {
            st = conn.prepareStatement(SELECT_ALL_jobs);
        }
        ResultSet rs = st.executeQuery();
        ArrayList<jobs> job = new ArrayList<>();
        while (rs.next()) {
            job.add(new jobs(rs));
        }

        return job;
    }






















//    public ArrayList<jobs> SELECT_ALL_jobs(Double min_salary, Integer limit, int offset) throws SQLException, ClassNotFoundException {
//        Class.forName("org.sqlite.JDBC");
//        Connection conn = DriverManager.getConnection(URL);     هذي لو كانت كل براميتر لحاله
//        PreparedStatement st;
//        if (min_salary != null && limit != null) {
//            st = conn.prepareStatement(SELECT_jobs_WITH_MIN_PAGINATION);
//            st.setDouble(1, min_salary);
//            st.setInt(2, limit);
//            st.setInt(3, offset);
//        } else if (min_salary != null) {
//            st = conn.prepareStatement(SELECT_jobs_WITH_MIN);
//            st.setDouble(1, min_salary);
//        } else if (limit != null) {
//            st = conn.prepareStatement(SELECT_jobs_WITH_PAGINATION);
//            st.setInt(1, limit);
//            st.setInt(2, offset);
//        } else {
//            st = conn.prepareStatement(SELECT_ALL_jobs);
//        }
//        ResultSet rs = st.executeQuery();
//        ArrayList<jobs> job = new ArrayList<>();
//        while (rs.next()) {
//            job.add(new jobs(rs));
//        }
//
//        return job;
//    }



}
