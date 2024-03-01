package com.ican.initial.demo.AppMain;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InitService {
    private List<String> cachedData;

    @PostConstruct
    private void loadDataDuringInitialization() {
        cachedData = new ArrayList<>();
        cachedData.add("SampleItem1");
        cachedData.add("SampleItem2");
    }

    public List<String> getCachedData() {
        return cachedData;
    }
}
