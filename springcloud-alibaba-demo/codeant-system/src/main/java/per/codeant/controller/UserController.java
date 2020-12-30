package per.codeant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.codeant.common.entity.TbPmsUser;
import per.codeant.service.UserService;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.12.30
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("info/{id}")
    public String userInfo(@PathVariable Integer id){
        TbPmsUser tbPmsUser = userService.userInfo(id);
        TbPmsUser tbPmsUser2 = userService.userInfoByMine(id);
        return tbPmsUser.toString() + "\r\n" + tbPmsUser2.toString();
    }
}
