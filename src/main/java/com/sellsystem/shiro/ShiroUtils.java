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
	/**
	 * 全部数据权限
	 */
	public static final String ALL_DATA = "ALL_DATA";
	/**
	 * 团队数据权限
	 */
	public static final String TEAM_DATA = "TEAM_DATA";
	/**
	 * 个人数据权限
	 */
	public static final String SELF_DATA = "SELF_DATA";




	/*public static Tenant getTenant() {
		ShiroPrincipal principal = getPrincipal();
		return principal.getTenant();
	}*/

	public static User getUser() {
		ShiroPrincipal principal = getPrincipal();
		return principal.getUser();
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
		return (ShiroPrincipal)subject.getPrincipal();
	}

	public static String getDisplayName() {
		return getUser().getName();
	}

	public static String getLoginUserId() {
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
	 * 获取联系人ID
	 *
	 */
	public static Object getLinkman(){
		Subject subject = getSubject();
		Session session = subject.getSession();
		Object linkman = session.getAttribute("linkman");
		return linkman;
	}

	/**
	 * 设置联系人ID
	 * @param linkman
	 */
	public static void setLinkman(Object linkman){
		Subject subject = getSubject();
		Session session = subject.getSession();
		session.setAttribute("linkman",linkman);
	}

	/**
	 * 获取当前session
	 * @return
	 */
	public static Session getSession(){
		Subject subject = getSubject();
		return subject.getSession();
	}


	public static String getOpenid(){
		Subject subject = getSubject();
		ShiroPrincipal shiroPrincipal = (ShiroPrincipal)subject.getPrincipal();
		return shiroPrincipal.getOpenid();
	}
}
