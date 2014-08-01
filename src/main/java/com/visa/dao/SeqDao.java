package com.visa.dao;

import org.apache.ibatis.annotations.Param;

public interface SeqDao {
	public void insert(@Param("seq") String seq);
	
	public int select(@Param("seq") String seq);
}
