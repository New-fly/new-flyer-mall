package org.wlgzs.xf_mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wlgzs.xf_mall.dao.AuthorizationRepository;
import org.wlgzs.xf_mall.entity.Authorization;
import org.wlgzs.xf_mall.service.AuthorizationService;

/**
 * @author:胡亚星
 * @createTime 2018-05-11 22:03
 * @description:
 **/
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Autowired
    AuthorizationRepository authorizationRepository;

    @Override
    public void save(Authorization authorization) {
        authorizationRepository.save(authorization);
    }

    @Override
    public Authorization isBinding(long githubId) {
        Authorization authorization = authorizationRepository.isBinding(githubId);
        return authorization;
    }
}
