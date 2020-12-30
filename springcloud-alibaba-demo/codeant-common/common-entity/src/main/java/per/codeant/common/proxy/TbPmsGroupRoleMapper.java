package per.codeant.common.proxy;

import per.codeant.common.entity.TbPmsGroupRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TbPmsGroupRoleMapper extends BaseMapper<TbPmsGroupRole>{

	/**批量新增数据*/
	int batchInsert(List<TbPmsGroupRole> list);

}