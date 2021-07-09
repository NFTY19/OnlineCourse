package com.nfty19.educenter.controller;

import com.nfty19.commonutils.JwtUtils;
import com.nfty19.commonutils.R;
import com.nfty19.educenter.entity.UcenterMember;
import com.nfty19.educenter.entity.vo.RegisterVo;
import com.nfty19.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author nfty19
 * @since 2021-06-15
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    //登录
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member) {
        //member对象封装手机号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token = memberService.login(member);
        if (token == null)  return R.error();
        else return R.ok().data("token",token);
    }

    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        boolean flag = memberService.register(registerVo);
        if (flag)   return R.ok();
        else return R.error();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

    @GetMapping("send/{phone}")
    public R send(@PathVariable String phone) {
        return R.ok().data("code",phone.substring(7));
    }
}

