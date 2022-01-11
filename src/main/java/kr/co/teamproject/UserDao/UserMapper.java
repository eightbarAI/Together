package kr.co.teamproject.UserDao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import kr.co.teamproject.domain.User;

@Repository
public interface UserMapper {
	//이메일 중복 처리를 위한 SQL
	/*
	@Select("select MEMBER_EMAIL from MEMBER where MEMBER_EMAIL = #{MEMBER_EMAIL}")
	public String emailCheck(String email);
	*/
	
	//이메일은 암호화를 했으므로 데이터베이스에서 직접 비교가 불가능해서 이메일을 리턴
	@Select("select MEMBER_EMAIL from MEMBER")
	public List<String> emailCheck();
	
	//닉네임 중복 처리를 위한 SQL
	@Select("select MEMBER_NICKNAME from MEMBER where MEMBER_NICKNAME = #{MEMBER_NICKNAME}")
	public String nicknameCheck(String nickname);
	
	//회원 가입을 위한 SQL
	@Insert("insert into MEMBER(MEMBER_PW, MEMBER_NAME, MEMBER_EMAIL, MEMBER_PHONE, MEMBER_NICKNAME, MEMBER_GENDER) values(#{MEMBER_PW}, #{MEMBER_NAME}, #{MEMBER_EMAIL}, #{MEMBER_PHONE}, #{MEMBER_NICKNAME}, #{MEMBER_GENDER})")
	public int join(User user);

	//로그인 처리를 위한 메소드 
	@Select("select MEMBER_EMAIL, MEMBER_PW from MEMBER")
	public List<User> login();
	
	
}
