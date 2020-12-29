package per.codeant.common.proxy;

import per.codeant.common.entity.Users;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsersMapper extends BaseMapper<Users>{

	/**批量新增数据*/
	int batchInsert(List<Users> list);

}