package com.wuyumaomao.easygenerate.query;

/**
 *  @Description:靓号表
 *  @author:wuyumaomao
 *  @Date:2024/08/14
 */
public class InfoBeautyQuery{
	/**
	 * 查询对象
	 */
    private Integer id;

	/**
	 * 邮箱查询对象
	 */
    private String email;

	private String emailFuzzy;

	/**
	 * 用户id查询对象
	 */
    private String userId;

	private String userIdFuzzy;

	/**
	 * 0未使用，1已使用查询对象
	 */
    private Integer status;

	 public void setId(Integer id){
	   this.id=id;
	 }
	 public Integer getId(){
	  return this.id;
	 }
	 public void setEmail(String email){
	   this.email=email;
	 }
	 public String getEmail(){
	  return this.email;
	 }
	 public void setUserId(String userId){
	   this.userId=userId;
	 }
	 public String getUserId(){
	  return this.userId;
	 }
	 public void setStatus(Integer status){
	   this.status=status;
	 }
	 public Integer getStatus(){
	  return this.status;
	 }
	 public void setEmailFuzzy(String emailFuzzy){
	   this.emailFuzzy=emailFuzzy;
	 }
	 public String getEmailFuzzy(){
	  return this.emailFuzzy;
	 }
	 public void setUserIdFuzzy(String userIdFuzzy){
	   this.userIdFuzzy=userIdFuzzy;
	 }
	 public String getUserIdFuzzy(){
	  return this.userIdFuzzy;
	 }
}