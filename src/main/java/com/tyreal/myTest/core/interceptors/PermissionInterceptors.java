package com.tyreal.myTest.core.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.tyreal.myTest.core.LocalUser;
import com.tyreal.myTest.exception.http.ForbiddenException;
import com.tyreal.myTest.exception.http.UnAuthenticatedException;
import com.tyreal.myTest.model.User;
import com.tyreal.myTest.service.UserService;
import com.tyreal.myTest.utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

public class PermissionInterceptors extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //请求到达controller之前的处理，返回false则不予放行
        Optional<ScopeLevel> scopeLevel = this.getScopeLevel(handler);
        if(!scopeLevel.isPresent()){
            return true;
            //没有相关注解，就是公开API
        }

        Optional<String> tokenopt = this.getToken(request);
        tokenopt.orElseThrow(()->new UnAuthenticatedException(10004));

        String bearerToken = tokenopt.get();
        if(!bearerToken.startsWith("Bearer")){
            //TODO:这里authentication前缀是硬编码在这里的，以后可以修改
            throw new UnAuthenticatedException(10004);
        }
        String [] tokens = bearerToken.split(" ");
        if(tokens.length!=2){
            throw new UnAuthenticatedException(10004);
        }
        String token = tokens[1];
        Optional<Map<String , Claim>> optionalMap = JwtToken.getClaims(token);
        Map<String ,Claim> map = optionalMap.orElseThrow(()->new UnAuthenticatedException(10004));
        //走到这里令牌则合法，下面对比权限
        //TODO:这里的map：scope(int)和uid(long)
        Boolean valid = hasPermission(scopeLevel.get(),map);
        if(valid){
            this.setToThreadLocal(map);
        }
        return valid;
    }

    private Boolean hasPermission(ScopeLevel scopeLevel,Map<String,Claim> map){
        Integer level = scopeLevel.value();
        Integer scope = map.get("scope").asInt();
        if(level>scope){
            //说明权限不足
            throw new ForbiddenException(10005);
        }
        return true;
    }

    private Optional<ScopeLevel> getScopeLevel(Object handler){
        HandlerMethod handlerMethod;
        if(handler instanceof HandlerMethod){
            handlerMethod = (HandlerMethod) handler;
            ScopeLevel scopeLevel = handlerMethod.getMethod().getAnnotation(ScopeLevel.class);
            if(scopeLevel==null){
                return Optional.empty();
            }
            return Optional.of(scopeLevel);
        }
        return Optional.empty();
    }

    private Optional<String> getToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return token==null?Optional.empty():Optional.of(token);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //渲染之前有一次ModelAndView修改的机会
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //最后释放资源
        LocalUser.clear();
        super.afterCompletion(request, response, handler, ex);
    }

    @Autowired
    public UserService userService;

    public void setToThreadLocal(Map<String ,Claim> map){
        Long uid = map.get("uid").asLong();
        Integer scope = map.get("scope").asInt();
        User user = userService.geyUserById(uid);
        LocalUser.set(user,scope);
    }

}
