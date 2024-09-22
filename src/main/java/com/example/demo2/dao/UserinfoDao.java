package com.example.demo2.dao;

import com.example.demo2.model.Userchainrelation;
import com.example.demo2.model.Userinfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserinfoDao {

    @Select("SELECT EXISTS(SELECT 1 FROM userinfo WHERE address = #{address})")
    Boolean selectUserByAddress(@Param("address") String address);

    @Insert("INSERT INTO userinfo (id, address, nickname, logo, description) VALUES (#{id}, #{address}, #{nickname}, #{logo}, #{description})")
    Integer insertUser(Userinfo userinfo);

    @Insert("INSERT INTO userchainrelation (address,chainId) VALUES (#{address}, #{chainId})")
    Integer insertChain(Userchainrelation userchainrelation);

    @Delete("DELETE FROM userinfo WHERE address = #{address}")
    Integer deleteUser(@Param("address") String address);

    @Delete("DELETE FROM userchainrelation WHERE address = #{address}")
    Integer deleteChain(@Param("address") String address);

}
