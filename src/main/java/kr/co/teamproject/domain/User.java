package kr.co.teamproject.domain;

import java.util.Date;

import lombok.Data;
@Data
public class User {
	private String MEMBER_PW;
	private	String MEMBER_NAME;
	private	String MEMBER_EMAIL;
	private	String MEMBER_PHONE;
	private	String MEMBER_NICKNAME;
	private String MEMBER_GENDER;
	private	String MEMBER_ADDRESS;
	private String MEMBER_IP;
	private Date MEMBER_LOGINDATE;
	private Date MEMBER_JOINDATE;
}
