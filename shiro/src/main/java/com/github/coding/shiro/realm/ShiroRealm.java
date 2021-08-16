package com.github.coding.shiro.realm;

import com.github.coding.shiro.service.AuthService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
public class ShiroRealm extends AuthorizingRealm {

    public ShiroRealm() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("md5");
        credentialsMatcher.setHashIterations(1024);
        this.setCredentialsMatcher(credentialsMatcher);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String principal = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // TODO 从数据库读取身份/权限
        simpleAuthorizationInfo.setRoles(AuthService.ROLES);
        simpleAuthorizationInfo.setStringPermissions(AuthService.PERMISSIONS);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        // TODO 从数据库对比登录信息
        return new SimpleAuthenticationInfo(
                principal,
                AuthService.ACCOUNTS.get(principal),
                ByteSource.Util.bytes("XXX"),
                this.getName());
    }
}
