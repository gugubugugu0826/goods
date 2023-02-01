package com.billy.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.billy.pojo.JAdmin;
import com.billy.service.AdminService;
import com.billy.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UsersService usersService;

    //启动项目就跳转至登录页面
    @RequestMapping({"/goLogin", "/"})
    public String goLogin() {
        return "login";
    }


    //退出登录
    @RequestMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("student");
        return "login";
    }

    @RequestMapping("/registerPage")
    public String registerPage(){
        return "user_register";
    }

    @RequestMapping("/register")
    public String register(String username, String password, Model model){
        QueryWrapper<JAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(JAdmin::getUsername,username);
        if(adminService.queryById(queryWrapper)!=null){
            model.addAttribute("msg","用户名已存在");
            return "user_register";
        }
        JAdmin admin = new JAdmin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setAdmin(0);
        adminService.insert(admin);
        return "login";
    }

    //登录
    @RequestMapping("/login")
    public String login(String username, String password, HttpSession httpSession, Model model) {
        //登录
        QueryWrapper<JAdmin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", password);
        JAdmin admin = adminService.queryById(wrapper);
        if (admin != null) {
            httpSession.setAttribute("admin", admin);
            httpSession.setAttribute("username", admin.getUsername());
            return "admin_index";
        } else {
            model.addAttribute("msg", "用户名或密码错误！");
            return "login";
        }
    }

    @RequestMapping("/admininfo")
    public String admininfo() {
        return "info_index";
    }

    @RequestMapping("/queryAdmin")
    public String queryAdmin(Model model){
        List<JAdmin> jAdmins = adminService.queryAll();
        model.addAttribute("list", jAdmins);
        return "admin_list";
    }

    @RequestMapping("/queryAdminById")
    @ResponseBody
    public String queryAdminById(Integer id){
        QueryWrapper<JAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(JAdmin::getId,id);
        JAdmin admin = adminService.queryById(queryWrapper);
        return JSON.toJSONString(admin);
    }

    @RequestMapping("/getUserLoginStatus")
    @ResponseBody
    public String getUserLoginStatus(HttpSession session){
        Object user = session.getAttribute("user");
        return user==null?"0":"1";
    }

    @RequestMapping("/addAdmin")
    @ResponseBody
    public String addAdmin(JAdmin admin){
        admin.setAdmin(0);
        adminService.insert(admin);
        Map<String, String> map = new HashMap<>();
        map.put("msg", "添加成功！");
        return JSON.toJSONString(map);
    }

    @RequestMapping("/deleteAdmin")
    @ResponseBody
    public String deleteAdmin(Integer id){
        adminService.delete(id);
        Map<String, String> map = new HashMap<>();
        map.put("msg", "删除成功！");
        return JSON.toJSONString(map);
    }

    @RequestMapping("/updateAdmin")
    @ResponseBody
    public String updateAdmin(JAdmin admin,HttpSession httpSession){
        JAdmin admin1 = (JAdmin) httpSession.getAttribute("admin");
        adminService.update(admin);
        if(admin1!=null && admin1.getId()==admin.getId()){
            QueryWrapper<JAdmin> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(JAdmin::getId,admin.getId());
            JAdmin a = adminService.queryById(queryWrapper);
            httpSession.setAttribute("admin",a);
        }
        Map<String, String> map = new HashMap<>();
        map.put("msg", "修改成功！");
        return JSON.toJSONString(map);
    }
}
