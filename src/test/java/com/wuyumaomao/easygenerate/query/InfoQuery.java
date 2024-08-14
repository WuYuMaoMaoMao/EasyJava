package com.wuyumaomao.easygenerate.query;
import java.util.Date;

/**
 *  @Description:用户信息
 *  @author:wuyumaomao
 *  @Date:2024/08/14
 */
public class InfoQuery{
	/**
	 * 用户id查询对象
	 */
    private Integer userId;

	/**
	 * 邮箱查询对象
	 */
    private String email;

	private String emailFuzzy;

	/**
	 * 昵称查询对象
	 */
    private String nickName;

	private String nickNameFuzzy;

	/**
	 * 0：直接加入，1：同意后加入查询对象
	 */
    private Integer joinType;

	/**
	 * 0女，1男查询对象
	 */
    private Integer sex;

	/**
	 * 密码查询对象
	 */
    private String password;

	private String passwordFuzzy;

	/**
	 * 个性签名查询对象
	 */
    private String personSignature;

	private String personSignatureFuzzy;

	/**
	 * 状态查询对象
	 */
    private Integer status;

	/**
	 * 创建时间查询对象
	 */
    private Date createTime;

	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 最后登录时间查询对象
	 */
    private Date lastLoginTime;

	private String lastLoginTimeStart;

	private String lastLoginTimeEnd;

	/**
	 * 地区查询对象
	 */
    private String areaName;

	private String areaNameFuzzy;

	/**
	 * 地区编号查询对象
	 */
    private String areaCode;

	private String areaCodeFuzzy;

	/**
	 * 最后离开时间查询对象
	 */
    private Date lastOffTime;

	private String lastOffTimeStart;

	private String lastOffTimeEnd;

	 public void setUserId(Integer userId){
	   this.userId=userId;
	 }
	 public Integer getUserId(){
	  return this.userId;
	 }
	 public void setEmail(String email){
	   this.email=email;
	 }
	 public String getEmail(){
	  return this.email;
	 }
	 public void setNickName(String nickName){
	   this.nickName=nickName;
	 }
	 public String getNickName(){
	  return this.nickName;
	 }
	 public void setJoinType(Integer joinType){
	   this.joinType=joinType;
	 }
	 public Integer getJoinType(){
	  return this.joinType;
	 }
	 public void setSex(Integer sex){
	   this.sex=sex;
	 }
	 public Integer getSex(){
	  return this.sex;
	 }
	 public void setPassword(String password){
	   this.password=password;
	 }
	 public String getPassword(){
	  return this.password;
	 }
	 public void setPersonSignature(String personSignature){
	   this.personSignature=personSignature;
	 }
	 public String getPersonSignature(){
	  return this.personSignature;
	 }
	 public void setStatus(Integer status){
	   this.status=status;
	 }
	 public Integer getStatus(){
	  return this.status;
	 }
	 public void setCreateTime(Date createTime){
	   this.createTime=createTime;
	 }
	 public Date getCreateTime(){
	  return this.createTime;
	 }
	 public void setLastLoginTime(Date lastLoginTime){
	   this.lastLoginTime=lastLoginTime;
	 }
	 public Date getLastLoginTime(){
	  return this.lastLoginTime;
	 }
	 public void setAreaName(String areaName){
	   this.areaName=areaName;
	 }
	 public String getAreaName(){
	  return this.areaName;
	 }
	 public void setAreaCode(String areaCode){
	   this.areaCode=areaCode;
	 }
	 public String getAreaCode(){
	  return this.areaCode;
	 }
	 public void setLastOffTime(Date lastOffTime){
	   this.lastOffTime=lastOffTime;
	 }
	 public Date getLastOffTime(){
	  return this.lastOffTime;
	 }
	 public void setEmailFuzzy(String emailFuzzy){
	   this.emailFuzzy=emailFuzzy;
	 }
	 public String getEmailFuzzy(){
	  return this.emailFuzzy;
	 }
	 public void setNickNameFuzzy(String nickNameFuzzy){
	   this.nickNameFuzzy=nickNameFuzzy;
	 }
	 public String getNickNameFuzzy(){
	  return this.nickNameFuzzy;
	 }
	 public void setPasswordFuzzy(String passwordFuzzy){
	   this.passwordFuzzy=passwordFuzzy;
	 }
	 public String getPasswordFuzzy(){
	  return this.passwordFuzzy;
	 }
	 public void setPersonSignatureFuzzy(String personSignatureFuzzy){
	   this.personSignatureFuzzy=personSignatureFuzzy;
	 }
	 public String getPersonSignatureFuzzy(){
	  return this.personSignatureFuzzy;
	 }
	 public void setCreateTimeStart(String createTimeStart){
	   this.createTimeStart=createTimeStart;
	 }
	 public String getCreateTimeStart(){
	  return this.createTimeStart;
	 }
	 public void setCreateTimeEnd(String createTimeEnd){
	   this.createTimeEnd=createTimeEnd;
	 }
	 public String getCreateTimeEnd(){
	  return this.createTimeEnd;
	 }
	 public void setLastLoginTimeStart(String lastLoginTimeStart){
	   this.lastLoginTimeStart=lastLoginTimeStart;
	 }
	 public String getLastLoginTimeStart(){
	  return this.lastLoginTimeStart;
	 }
	 public void setLastLoginTimeEnd(String lastLoginTimeEnd){
	   this.lastLoginTimeEnd=lastLoginTimeEnd;
	 }
	 public String getLastLoginTimeEnd(){
	  return this.lastLoginTimeEnd;
	 }
	 public void setAreaNameFuzzy(String areaNameFuzzy){
	   this.areaNameFuzzy=areaNameFuzzy;
	 }
	 public String getAreaNameFuzzy(){
	  return this.areaNameFuzzy;
	 }
	 public void setAreaCodeFuzzy(String areaCodeFuzzy){
	   this.areaCodeFuzzy=areaCodeFuzzy;
	 }
	 public String getAreaCodeFuzzy(){
	  return this.areaCodeFuzzy;
	 }
	 public void setLastOffTimeStart(String lastOffTimeStart){
	   this.lastOffTimeStart=lastOffTimeStart;
	 }
	 public String getLastOffTimeStart(){
	  return this.lastOffTimeStart;
	 }
	 public void setLastOffTimeEnd(String lastOffTimeEnd){
	   this.lastOffTimeEnd=lastOffTimeEnd;
	 }
	 public String getLastOffTimeEnd(){
	  return this.lastOffTimeEnd;
	 }
}