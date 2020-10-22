package com.tianlb.myTest.service;

import com.tianlb.myTest.exception.http.NotFoundException;
import com.tianlb.myTest.repository.ThemeRepository;
import com.tianlb.myTest.model.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {

    @Autowired
    private ThemeRepository themeRepository;

    public List<Theme> getByNames(List<String> names){
        List<Theme> themes = themeRepository.findByNames(names);
        if(themes.isEmpty()){
            //这里不会产生null，最多返回空的list，所以不使用optional
            throw new NotFoundException(30010);
        }
        return themes;
    }

    public Optional<Theme> getByName(String  name){
        return themeRepository.findByName(name);
    }

}
