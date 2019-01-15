package org.wlgzs.xf_mall.service.impl;

import org.springframework.stereotype.Service;
import org.wlgzs.xf_mall.dao.AuthorizationRepository;
import org.wlgzs.xf_mall.entity.Authorization;
import org.wlgzs.xf_mall.service.AuthorizationService;

import javax.annotation.Resource;

/**
 * @author:胡亚星
 * @createTime 2018-05-11 22:03
 * @description:
 **/
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Resource
    AuthorizationRepository authorizationRepository;

    @Override
    public void save(Authorization authorization) {
        authorizationRepository.save(authorization);
    }

    @Override
    public Authorization isBinding(long githubId) {
        return authorizationRepository.isBinding(githubId);
    }
}
