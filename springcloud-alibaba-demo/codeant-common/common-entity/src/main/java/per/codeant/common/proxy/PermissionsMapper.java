package per.codeant.common.proxy;

import per.codeant.common.entity.Permissions;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PermissionsMapper extends BaseMapper<Permissions>{

	/**批量新增数据*/
	int batchInsert(List<Permissions> list);

}