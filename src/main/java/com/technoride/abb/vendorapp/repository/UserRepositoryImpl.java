package com.technoride.abb.vendorapp.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean login(String username, String password)
    {
        String query = "select * from users where user_id=? and user_pwd=?";
        User user =jdbcTemplate.queryForObject(query, new Object[]{username, password}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUser_id(resultSet.getString("user_id"));
                user.setUser_pwd(resultSet.getString("user_pwd"));
                user.setUser_active(resultSet.getShort("user_active"));
                user.setUser_level(resultSet.getShort("user_level"));
                return user;
            }
        });

        if(user.getUser_active() == UserStatus.USER_ACTIVE)
        return true;
        else
        return false;
    }

    private interface UserStatus
    {
      public static final short USER_ACTIVE=1;
      public static final short USER_INACTIVE =0;
    }

    private class User
    {
        private long id;
        private String user_id;
        private String user_pwd;
        private short user_level;
        private short user_active;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_pwd() {
            return user_pwd;
        }

        public void setUser_pwd(String user_pwd) {
            this.user_pwd = user_pwd;
        }

        public short getUser_level() {
            return user_level;
        }

        public void setUser_level(short user_level) {
            this.user_level = user_level;
        }

        public short getUser_active() {
            return user_active;
        }

        public void setUser_active(short user_active) {
            this.user_active = user_active;
        }
    }
}
