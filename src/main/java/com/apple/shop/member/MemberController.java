package com.apple.shop.member;

import com.apple.shop.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    //회원가입 페이지 이동
    @GetMapping("/register")
    String register(){
        return "register.html";
    }
    // 회원가입
    @PostMapping("/member")
    String addMember(String username, String password, String displayName){
        //짧은 아이디/패드워드 막는 유효성검사 만들기
        Member member = new Member();
        member.setUsername(username);
        var hash = passwordEncoder.encode(password);
        member.setPassword(hash);
        member.setDisplayName(displayName);
        memberRepository.save(member);
        return "redirect:/list/page/1";
    }

    //회원가입 페이지 이동
    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
    //마이페이지 이동
    @GetMapping("/my-page")
    public String mypage(Authentication auth){
        CustomUser result = (CustomUser) auth.getPrincipal();
        System.out.println(result.displayName);
        return "mypage.html";
    }

    //마이페이지 이동
    @GetMapping("/user/1")
    @ResponseBody
    public DataDto getUser(){
        var a= memberRepository.findById(1L);
        var result = a.get();
        var data = new DataDto(result.getUsername(),result.getDisplayName());
        return data;
    }
}

class DataDto{
    public String username;
    public String displayName;

    DataDto(String a, String b){
        this.username = a;
        this.displayName =b;
    };
}