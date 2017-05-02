package com.sellsystem.shiro;

import com.sellsystem.entity.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义认证主体
 * @author yuqs
 * @since 0.1
 */
public class ShiroPrincipal implements Serializable {

	private static final long serialVersionUID = 1428196040744555722L;
	//用户对象
	private User user;
	//租户对象
	//private Tenant tenant;
	//用户权限列表
	private List<String> authorities = new ArrayList<String>();
	//联系人对象
	private Integer linkmanId;
	//联系人openid
	private String openid;
	//用户角色列表
	private List<String> roles = new ArrayList<String>();
	private String org = "";

	//是否已授权。如果已授权，则不需要再从数据库中获取权限信息，减少数据库访问
	//这里会导致修改权限时，需要重新登录方可有效
	private boolean isAuthorized = false;
	/**
	 * 用户登录子域名
	 */
	private String XDomain;

	private Boolean isAdmin;

	/**
	 * 构造函数，参数为User对象
	 * 根据loginUser对象属性，赋值给Principal相应的属性上
	 *
	 * @param user
	 */
	public ShiroPrincipal(User user) {
		this.user = user;
	}

	public ShiroPrincipal() {
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public boolean isAuthorized() {
		return isAuthorized;
	}

	public void setAuthorized(boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDisplayName() {
		return this.user.getName();
	}

	public String getLoginUserId() {
		return this.user.getId();
	}

	public Boolean getAdmin() {
		return isAdmin;
	}

	public void setAdmin(Boolean admin) {
		isAdmin = admin;
	}

	/**
	 * <shiro:principal/>标签显示中文名称
	 */
	@Override
	public String toString() {
		if(user != null){
			return this.user.getName() + "<input type=\"hidden\" id=\"userId\" value=\"" + this.user.getName() +"\">";
		}else{
			return null;
		}
	}

	public String getOrg() {
		return org;
	}



	public void setXDomain(String XDomain) {
		this.XDomain = XDomain;
	}

	public String getXDomain() {
		return XDomain;
	}

	public Integer getLinkmanId() {
		return linkmanId;
	}

	public void setLinkmanId(Integer linkmanId) {
		this.linkmanId = linkmanId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
