package com.sellsystem.shiro;

import com.sellsystem.entity.Role;
import com.sellsystem.entity.RolePermission;
import com.sellsystem.entity.User;
import com.sellsystem.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 理论：
 *
 *  身份认证
 *  在认证、授权内部实现机制中都有提到，最终处理都将交给Real进行处理。
 *  因为在Shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的。
 *  通常情况下，在Realm中会直接从我们的数据源中获取Shiro需要的验证信息。
 *  可以说，Realm是专用于安全框架的DAO.
 *
 *  Shiro的认证过程最终会交由Realm执行，这时会调用Realm的getAuthenticationInfo(token)方法。
 *  该方法主要执行以下操作:
 *  1、检查提交的进行认证的令牌信息
 *  2、根据令牌信息从数据源(通常为数据库)中获取用户信息
 *  3、对用户信息进行匹配验证。
 *  4、验证通过将返回一个封装了用户信息的AuthenticationInfo实例。
 *  5、验证失败则抛出AuthenticationException异常信息。
 *  而在我们的应用程序中要做的就是自定义一个Realm类，继承AuthorizingRealm抽象类，重载doGetAuthenticationInfo ()，重写获取用户信息的方法。
 *
 *  在权限管理系统中，有这么几个角色很重要。
 *  第一是用户表：在用户表中保存了用户的基本信息，账号、密码、姓名，性别等；
 *  第二是：权限表（资源+控制权限）：这个表中主要是保存了用户的URL地址，权限信息；
 *  第三就是角色表：在这个表重要保存了系统存在的角色；
 *  第四就是关联表：用户-角色管理表（用户在系统中都有什么角色，比如admin，vip等），角色-权限关联表（每个角色都有什么权限可以进行操作）。
 *
 *  shiro的认证最终是交给了Realm进行执行了，所以我们需要自己重新实现一个Realm，此Realm继承AuthorizingRealm。
 */

/**
 * 身份校验核心类
 * Created by zhangwei on 2017/4/26.
 */
public class DrugShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 认证信息（身份认证）,验证用户输入的用户名和密码是否正确
     * Authentication：用来验证用户身份
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-------------------------------------DrugShiroRealm doGetAuthenticationInfo()-----------------------------------------");

        //获取用户输入的账号
        String account = (String) authenticationToken.getPrincipal();
        System.out.println("---------------------------------------user  credentials" + authenticationToken.getCredentials());

        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User user = userService.getUserByAccount(account).getData();
        if (null == user) {
            return null;
        }

        /*
         * 获取权限信息
         * 根据user、role、permission进行实现（三个表）
         * 获取之后在前端for循环显示所有链接
         */
        //user.setPermissions(user.getPermissions(userId));

        //账号判断；

        //加密方式
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,   //用户名
                user.getPassword(),  //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()), //加密盐  salt=account+salt
                getName()  //realm name
        );

        //明文：若存在，将此用户存放到登陆认证info中，无需自己做密码对比，Shiro会进行密码对比校验
        //SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        return authenticationInfo;
    }

    /**
     * 此方法调用  hasRole、hasPermission的时候才会进行回调
     * （使用了相应的注解或者shiro标签才会执行此方法否则不会执行，所以只是简单的身份认证没有权限控制的话，那么这个方法可以不实现，直接返回null即可）
     *
     * 权限信息（授权）：
     * 1、如果用户正常退出，缓存自动清空
     * 2、如果用户非正常退出，缓存自动清空
     * 3、如果修改了用户权限，而用户不退出系统，修改的权限无法立即生效。（手动编程实现，在service中调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所有从spring中获取realm实例，调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        /*
         * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
         * 其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理，
         * 放到缓存中时，这样的话doGetAuthorizationInfo就只会执行一次了，
         * 缓存过期之后会再次执行
         */
        System.out.println("-------------------权限配置-->DrugShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();

        //实际项目中，这里可以根据实际情况做缓存，如果不做Shiro也有自己的时间间隔机制，2min内不会重复执行该方法
        //User user = userService.getUser(username);

        //权限单个添加
        //或按下面这样添加
        //添加一个角色，不是配置意义上的添加，而是证明该用户拥有admin角色
        //authorizationInfo.addRole("admin");
        //添加权限
        //authorizationInfo.addStringPermission("user:query");

        //在认证成功后返回
        //设置角色信息
        //支持Set集合
        //用户的角色对应的所有权限如果只使用角色定义访问权限，下面四行可以不要
        /*List<Role> roleList = user.getRoleList();
        for (Role role : roleList) {
            authorizationInfo.addStringPermissions(role.getPermissionNameList());
        }*/
        for (Role role : user.getRoleList()) {
            authorizationInfo.addStringPermission(role.getName());
            for (RolePermission permission : role.getPermissionList()) {
                authorizationInfo.addStringPermission(permission.getName());
            }
        }

        //设置权限信息
        //authorizationInfo.setStringPermissions(getStringPermissions(user.getRoleList()));
        return authorizationInfo;
    }

    /**
     * 将权限对象中的code取出来
     * @param permissions
     * @return
     */
    public Set<String> getStringPermissions(Set<RolePermission> permissions) {
        Set<String> stringPermissions = new HashSet<>();
        if (null != permissions) {
            for (RolePermission permission : permissions) {
                stringPermissions.add(permission.getName());
            }
        }
        return stringPermissions;
    }
}















