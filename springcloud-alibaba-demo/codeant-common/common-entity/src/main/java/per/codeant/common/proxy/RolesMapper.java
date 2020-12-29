package per.codeant.common.proxy;

import per.codeant.common.entity.Roles;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RolesMapper extends BaseMapper<Roles>{

	/**批量新增数据*/
	int batchInsert(List<Roles> list);

}