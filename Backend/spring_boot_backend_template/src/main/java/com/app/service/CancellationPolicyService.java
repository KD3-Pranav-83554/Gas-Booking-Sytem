package com.app.service;


import com.app.DAO.PolicyDao;
import com.app.DTO.CancellationPolicyDto;
import com.app.entities.Policy.CancellationPolicy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CancellationPolicyService {


    @Autowired
    PolicyDao policyDao;

    @Autowired
    ModelMapper mapper;

    static CancellationPolicy cp;
    public void selectCancellationPolicy(CancellationPolicyDto reqDto){
        cp=mapper.map(reqDto, CancellationPolicy.class);
    }

    public static CancellationPolicy getCancellationPolicy(){
        return cp;
    }
}
