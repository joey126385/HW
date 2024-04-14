package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.CoindeskModel;

//@Repository
public interface CoindeskRepository extends CrudRepository<CoindeskModel, String> {
	//1. 測試呼叫查詢幣別對應表資料API，並顯示其內容。
	CoindeskModel findByCode(String code);
	
	
	@Modifying
	@Query("update CoindeskModel c set c.description = :description where c.code = :code")
	void updateByDescription(@Param("description") String description ,@Param("code") String code);
	
	//4. 測試呼叫刪除幣別對應表資料API。
	 @Modifying
	 @Query("delete from CoindeskModel  c where c.code = :code")
	void deleteByCode(@Param("code")String code);
	 

}
