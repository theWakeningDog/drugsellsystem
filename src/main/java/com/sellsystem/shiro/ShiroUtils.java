package com.sellsystem.shiro;

import com.sellsystem.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * shiro工具类
 * @author yuqs
 * @since 0.1
 */
public class ShiroUtils {

	public static User getUser() {
//		ShiroPrincipal principal = getPrincipal();
//		return principal.getUser();
//		Subject subject = getSubject();
//		return (User)subject.getPrincipal();
		User user = new User();
		user.setId("c8ab7c2d-0c87-11e7-8d59-0021cc62c2f3");
		return user;
	}

	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取当前登录的认证实体
	 * @return
	 */
	public static ShiroPrincipal getPrincipal() {
		Subject subject = getSubject();
		Object o = subject.getPrincipal();
		//return (ShiroPrincipal)subject.getPrincipal();
		return new ShiroPrincipal((User) subject.getPrincipal());
	}

	public static String getName() {
		return getUser().getName();
	}

	public static String getUserId() {
		return getUser().getId();
	}

	/**
	 * 返回当前登录的认证实体部门ID
	 * @return
	 */
	public static String getOrgId() {
		return getPrincipal().getOrg();
	}

	/**
	 * 获取所有组集合
	 * @return
	 */
	public static List<String> getGroups() {
		List<String> groups = new ArrayList<String>();
		String orgId = getOrgId();
		ShiroPrincipal principal = getPrincipal();
		if(principal != null) {
			groups.addAll(principal.getRoles());
		}
		if(orgId != null) {
			groups.add(String.valueOf(orgId));
		}
		return groups;
	}

	/**
	 * 获取当前session
	 * @return
	 */
	public static Session getSession(){
		Subject subject = getSubject();
		return subject.getSession();
	}
}
