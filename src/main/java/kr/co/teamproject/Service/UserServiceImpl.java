package kr.co.teamproject.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.teamproject.UserDao.UserMapper;
import kr.co.teamproject.domain.User;
import kr.co.teamproject.util.CryptoUtil;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper UserDao;

	@Override
	public Map<String, Object> join(HttpServletRequest request, HttpServletResponse response) {
		//리턴할 결과를 저장한 인스턴스
		Map<String, Object> map = new HashMap<>();
		//결과 초기화
		//회원 가입 성공 여부
		map.put("result", false);
		//이메일 중복 검사 결과
		map.put("emailcheck", false);
		//닉네임 중복 검사 결과
		map.put("nicknamecheck", false);

		//파라미터를 읽기 - Controller에서 읽으면 하지 않아도 됨
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		String nickname = request.getParameter("nickname");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String gender = request.getParameter("gender");
		
		//파일이 여러 개 라면 getFiles를 호출하고 List로 받아야 합니다.
		//List<MultipartFile> images = request.getFiles("image");

		//작업을 수행해야 하면 작업을 수행

		//email 중복 체크를 수행
		List<String> emailresult = UserDao.emailCheck();
		boolean flag = false;
		for(String e : emailresult) {
			try {
				if(email.equals(CryptoUtil.decryptAES256(e, "luna"))) {
					flag = true;
					break;
				}
			}catch(Exception ex) {
				System.out.println(ex.getLocalizedMessage());
			}
		}
		if(flag == false) {
			map.put("emailcheck", true);
		}

		//nickname 중복 검사 수행
		String nicknameresult = UserDao.nicknameCheck(nickname);
		if(nicknameresult == null) {
			map.put("nicknamecheck", true);
		}


		

		//이메일 과 닉네임 중복검사를 통과한 경우 회원가입
		if(flag == false && nicknameresult == null) {
			//Repository 의 메서드를 호출해야 하면 필요한 파라미터를 생성
			User user = new User();
			user.setMEMBER_PW(BCrypt.hashpw(pw, BCrypt.gensalt()));
			user.setMEMBER_NAME(name);
			try {
			user.setMEMBER_EMAIL(CryptoUtil.encryptAES256(email, "luna"));
			}catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			user.setMEMBER_PHONE(phone);
			user.setMEMBER_NICKNAME(nickname);
			user.setMEMBER_GENDER(gender);
			System.out.println(user);
			//Repository 메서드를 호출 - 회원 가입
			int result = UserDao.join(user);	
		}
		
		return map;
	}

	@Override
	public Map<String, Object> emailCheck(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		//파라미터 읽기
		String email = request.getParameter("email");


		//이메일을 전부 가져와서 
		List <String> result = UserDao.emailCheck();
		//가져온 이메일 순회 
		boolean flag = false;
		for(String e : result) {
			try {
				//복호화를 해서 비교
				if(email.equals(CryptoUtil.decryptAES256(e, "luna"))) {
					flag = true;
					break;
				}
			}catch(Exception ex) {
				System.out.println(ex.getLocalizedMessage());
			}
		}
		//이메일이 없는 경우는 result 에 true
		//이메일이 존재하는 경우는 result false
		if(flag == false) {
			map.put("result", true);
		}else {
			map.put("result", false);
		}
		

		return map;
	}

	@Override
	public Map<String, Object> nicknameCheck(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		//파라미터 읽기
		String nickname = request.getParameter("nickname");

		String result = UserDao.nicknameCheck(nickname);
		//닉네임이 없는 경우는 result 에 true
		//닉네임이 존재하는 경우는 result false
		if(result == null) {
			map.put("result", true);
		}else {
			map.put("result", false);
		}
		


		return map;
	}



	@Override
	public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		//로그인 성공 여부를 먼저 저장
		map.put("result", false);

		//파라미터 읽어오기 
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");

		//로그인하기 위한 정보를 가져오기
		List<User> list = UserDao.login();

		//암호화 할 때 사용했던 키
		String key = "luna";

		try {
			for(User user : list) {
				if(email.equals(CryptoUtil.decryptAES256(user.getMEMBER_EMAIL(), key)) && BCrypt.checkpw(pw, user.getMEMBER_PW())) {
					map.put("result", true);
					//필요한 정보 저장
					map.put("email", email);
					map.put("nickname", user.getMEMBER_NAME());
					break;
				}
			}


		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		//세션에 저장
		request.getSession().setAttribute("userinfo", map);


		return map;
	}
}