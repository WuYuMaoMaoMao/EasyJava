package com.wuyumaomao.easygenerate.mappers;
import org.apache.ibatis.annotations.Param;
/**
 *  @Description:靓号表Mapper
 *  @author:wuyumaomao
 *  @Date:2024/08/14
 */
public interface InfoBeautyMapper<T,P> extends BaseMapper{

	/**
	 * 根据IdAndUserId查询
	 */
	 T selectByIdAndUserId(@Param("id") Integer id,@Param("userId") String userId);

	/**
	 * 根据IdAndUserId更新
	 */
	 Integer updateByIdAndUserId(@Param("bean") T t,@Param("id") Integer id,@Param("userId") String userId);

	/**
	 * 根据IdAndUserId删除
	 */
	 Integer deleteByIdAndUserId(@Param("id") Integer id,@Param("userId") String userId);


	/**
	 * 根据UserId查询
	 */
	 T selectByUserId(@Param("userId") String userId);

	/**
	 * 根据UserId更新
	 */
	 Integer updateByUserId(@Param("bean") T t,@Param("userId") String userId);

	/**
	 * 根据UserId删除
	 */
	 Integer deleteByUserId(@Param("userId") String userId);


	/**
	 * 根据Email查询
	 */
	 T selectByEmail(@Param("email") String email);

	/**
	 * 根据Email更新
	 */
	 Integer updateByEmail(@Param("bean") T t,@Param("email") String email);

	/**
	 * 根据Email删除
	 */
	 Integer deleteByEmail(@Param("email") String email);

 }
