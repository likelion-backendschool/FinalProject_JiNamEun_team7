package com.ll.exam.ebook.app.member.controller;

import com.example.eBook.domain.member.dto.SignupForm;
import com.example.eBook.domain.member.validator.SignFormValidator;
import com.ll.exam.ebook.app.member.form.JoinForm;
import com.ll.exam.ebook.app.member.service.MemberService;
import com.ll.exam.ebook.app.util.Ut;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;


    @GetMapping("/login")
    public String showLogin(HttpServletRequest request) {
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/member/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }
        return "member/login";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        List<Article> articles = articleService.getList();
        model.addAttribute("articles", articles);
        return "member/list";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid JoinForm joinForm) {
        memberService.join(joinForm.getUsername(), joinForm.getPassword(), joinForm.getEmail());

        return "redirect:/member/login?msg=" + Ut.url.encode("회원가입이 완료되었습니다.");
    }
}