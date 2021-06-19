package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class BlackListService {

    @Value("${words.blacklist}")
    private String blackListCSV;

    private List<String> blackList;

    @PostConstruct
    void init() {
        blackList = List.of(blackListCSV.split(","));
    }

    public boolean containsBlackListedWord(String s) {
        return blackList.stream().anyMatch(s::contains);
    }

}
