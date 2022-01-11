package kr.co.teamproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.teamproject.Service.UserService;

@Controller
public class UserPageController {
	@Autowired
	private UserService UserService;

	@GetMapping("user/join")
	public String join() {
		return "user/join";
	}
	@GetMapping("/user/login")
	public String login() {
		return "user/login";
	}

}
