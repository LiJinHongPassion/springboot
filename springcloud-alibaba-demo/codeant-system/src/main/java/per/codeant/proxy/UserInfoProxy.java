package per.codeant.proxy;

import org.springframework.stereotype.Repository;
import per.codeant.common.entity.TbPmsUser;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.12.30
 */
@Repository
public interface UserInfoProxy {
    TbPmsUser userInfo(Integer id);
}
