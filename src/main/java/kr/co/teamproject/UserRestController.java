package kr.co.teamproject;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.teamproject.Service.UserService;

@RestController
public class UserRestController {
	@Autowired
	private UserService UserService;

	//회원 가입 요청을 처리할 메소드
	@GetMapping("/user/joinajax")
	public Map<String, Object> join(@RequestParam("email")String email, HttpServletRequest request, HttpServletResponse respone){
		System.out.println(email);
		return UserService.join(request, respone);
	}
	//이메일 중복 검사 요청을 처리할 메소드
	@GetMapping("/user/emailCheck")
	public Map<String, Object> emailCheck(HttpServletRequest request, HttpServletResponse respone){
		return UserService.emailCheck(request, respone);
	}

	//닉네임 중복 검사 요청을 처리할 메소드
	@GetMapping("/user/nicknameCheck")
	public Map<String, Object> nicknameCheck(HttpServletRequest request, HttpServletResponse respone){
		return UserService.nicknameCheck(request, respone);
	}
	@PostMapping("/user/login")
	public Map<String, Object> loginCheck(HttpServletRequest request, HttpServletResponse respone){
		return UserService.login(request, respone);
	}
}
