package com.billy.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.billy.pojo.Goods;
import com.billy.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping("/queryUsers")
    public String queryUsers(String brand,String type,Model model){
        //查询用户列表
        List<Goods> users = usersService.queryAll(brand, type);
        model.addAttribute("list", users);
        return "users_index";
    }

    @RequestMapping("/deleteUsers")
    @ResponseBody
    public String deleteUsers(Integer id) {
        Map<String, Object> map = new HashMap<>();
        usersService.delete(id);
        map.put("msg", "删除成功！");
        return JSON.toJSONString(map);
    }

    @RequestMapping("/addUsers")
    @ResponseBody
    public String addUsers(Goods users) {
        Map<String, Object> map = new HashMap<>();
        usersService.add(users);
        map.put("msg", "添加成功！");
        return JSON.toJSONString(map);
    }

    @RequestMapping("/updateUsers")
    @ResponseBody
    public String updateUsers(Goods users) {
        Map<String, Object> map = new HashMap<>();
        usersService.update(users);
        map.put("msg", "修改成功！");
        return JSON.toJSONString(map);
    }

    @RequestMapping("/updateIndexUsers")
    public String updateIndexUsers(Goods users, HttpSession session) {
        usersService.update(users);
        session.setAttribute("user",users);
        return "redirect:userInfoIndex";
    }

    @RequestMapping("/queryUsersById")
    @ResponseBody
    public String queryUsersById(Integer id) {
        Map<String, Object> map = new HashMap<>();
        Goods users = usersService.queryUsersById(id);
        return JSON.toJSONString(users);
    }

    @RequestMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<Goods> users = usersService.queryAll(null, null);
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("id", "id");
        writer.addHeaderAlias("goodsname", "商品名称");
        writer.addHeaderAlias("brand", "品牌");
        writer.addHeaderAlias("views", "浏览量");
        writer.addHeaderAlias("price", "价格");
        writer.addHeaderAlias("transactions", "交易人数");
        writer.addHeaderAlias("rate", "转化率");
        writer.addHeaderAlias("time", "交易时间");
        writer.addHeaderAlias("type", "类型");
        writer.addHeaderAlias("province", "省份");
        writer.addHeaderAlias("goodsnumber", "成交商品数");
        writer.write(users, true);
        response.setContentType("application/vnd.openxmlformats-officeedocument.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("goods", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }
}
