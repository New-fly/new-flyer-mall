package org.wlgzs.xf_mall.api;


/**
 * @author:胡亚星
 * @createTime 2018-05-10 10:44
 * @description:
 **/
public class GithubApi{
    private static final String AUTHORIZE_URL = "https://github.com/login/oauth/authorize?client_id=%s&redirect_uri=%s&state=%s";
    private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + "&scope=%s";
    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token?state=%s";

    private final String githubState;

    public GithubApi(String state){
        this.githubState = state;
    }

}
