package per.codeant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.codeant.common.entity.TbPmsUser;
import per.codeant.common.proxy.TbPmsUserMapper;
import per.codeant.proxy.UserInfoProxy;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.12.30
 */
@Service
public class UserService {
    //生成的Mapper
    @Autowired
    private TbPmsUserMapper tbPmsUserMapper;
    //自定义的Mapper
    @Autowired
    private UserInfoProxy userInfoProxy;


    public TbPmsUser userInfo(Integer id){
        return tbPmsUserMapper.selectById(id);
    }

    public TbPmsUser userInfoByMine(Integer id){
        return userInfoProxy.userInfo(id);
    }
}
