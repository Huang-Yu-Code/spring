package com.github.codingob.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
public class ShiroRealm extends AuthorizingRealm {
    private final static Map<String, String> ACCOUNTS = new ConcurrentHashMap<>();
    private final static Map<String, Set<String>> ROLES = new ConcurrentHashMap<>();
    private final static Map<String, Set<String>> PERMISSIONS = new ConcurrentHashMap<>();

    static {
        ACCOUNTS.put("guest", new Md5Hash("guest", "XXX", 1024).toHex());
        ACCOUNTS.put("admin", new Md5Hash("admin", "XXX", 1024).toHex());
        CopyOnWriteArraySet<String> guestRoles = new CopyOnWriteArraySet<>();
        guestRoles.add("guest");
        ROLES.put("guest", guestRoles);

        CopyOnWriteArraySet<String> adminRoles = new CopyOnWriteArraySet<>();
        adminRoles.add("guest");
        adminRoles.add("admin");
        ROLES.put("admin", adminRoles);

        CopyOnWriteArraySet<String> guestPermissions = new CopyOnWriteArraySet<>();
        guestPermissions.add("guest:create");
        guestPermissions.add("guest:read");
        guestPermissions.add("guest:update");
        guestPermissions.add("guest:delete");
        PERMISSIONS.put("guest", guestPermissions);

        CopyOnWriteArraySet<String> adminPermissions = new CopyOnWriteArraySet<>(guestRoles);
        adminPermissions.add("admin:create");
        adminPermissions.add("admin:read");
        adminPermissions.add("admin:update");
        adminPermissions.add("admin:delete");
        PERMISSIONS.put("admin", adminPermissions);
    }

    public ShiroRealm() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("md5");
        credentialsMatcher.setHashIterations(1024);
        this.setCredentialsMatcher(credentialsMatcher);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // TODO 从数据库读取身份/权限
        authorizationInfo.setRoles(ROLES.get(principalCollection.getPrimaryPrincipal().toString()));
        authorizationInfo.setStringPermissions(PERMISSIONS.get(principalCollection.getPrimaryPrincipal().toString()));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // TODO 从数据库对比登录信息
        return new SimpleAuthenticationInfo(
                authenticationToken.getPrincipal(),
                ACCOUNTS.get(authenticationToken.getPrincipal().toString()),
                ByteSource.Util.bytes("XXX"),
                this.getName()
        );
    }
}
