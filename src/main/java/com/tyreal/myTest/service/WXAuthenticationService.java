package com.tyreal.myTest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyreal.myTest.exception.http.NotFoundException;
import com.tyreal.myTest.exception.http.ParameterException;
import com.tyreal.myTest.model.User;
import com.tyreal.myTest.repository.UserRepository;
import com.tyreal.myTest.utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WXAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper mapper;

    @Value("${wx.appid}")
    private String appId;

    @Value("${wx.appsercret}")
    private String appSercret;

    @Value("${wx.code2session}")
    private String  code2session;

    public String code2Session(String code) {
        String url = MessageFormat.format(this.code2session,this.appId,this.appSercret,code);
        RestTemplate rest = new RestTemplate();
        String sessionJson = rest.getForObject(url,String.class);
        Map<String ,Object> session = new HashMap<>();
        try {
            session = mapper.readValue(sessionJson, HashMap.class);

            //session要做一次容错的机制，因为可能会出现一些问题
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //这里应该抛出错误，但是我忘记具体是哪个错误了
        }
        return this.registerUser(session);
    }

    private String registerUser(Map<String ,Object> session){
        String openId = (String) session.get("openid");
        if(openId==null){
            throw new ParameterException(20004);
        }
        Optional<User> userOptional = this.userRepository.findByOpenid(openId);
        if(userOptional.isPresent()){
            //返回jwt令牌
            //建立一个数字等级的映射关系，我们这里先不做辽

            return JwtToken.makeToken(userOptional.get().getId());
            //内部存在默认scope等级
        }else{
            User user = User.builder()
                    .openid(openId)
                    .build();
            userRepository.save(user);
            //TODO：返回jwt令牌
            Long uid = user.getId();
            //这个时候就会生成了
            return JwtToken.makeToken(uid);
        }
    }
}
