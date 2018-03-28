package com.qf.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * Created by Administrator on 2018/3/22.
 */
public class UserLogin {

    public void login(){
        // 第一步 : 加载配置文件,得到安全管理工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm-md5.ini");
        // 第二步 : 由工程得到安全管理实例对象
        SecurityManager securityManager = factory.getInstance();
        // 第三步: 把安全管理对象设置到当前的运行环境当中.
        SecurityUtils.setSecurityManager(securityManager);
        //第四步: 得到主体对象 ( 执行者)
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            //第五步: 把用户输入的用户名和密码封装到token中
            UsernamePasswordToken token = new UsernamePasswordToken("qf", "qf");
            try {
                //第六步:  执行认证提交
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                uae.printStackTrace();
            } catch (IncorrectCredentialsException ice) {
                ice.printStackTrace();
            }
        }
        //第七步: 确实是否认证通过
        boolean authenticated = currentUser.isAuthenticated();
        System.out.println(authenticated);
        //退出测试:
        currentUser.logout();
        boolean authenticated2 = currentUser.isAuthenticated();
        System.out.println(authenticated2);
    }


    public static void main(String[] args) {
        UserLogin userLogin=new UserLogin();
        userLogin.login();
    }
}
