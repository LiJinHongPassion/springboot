package per.codeant.common.proxy;

import per.codeant.common.entity.TbPmsRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TbPmsRoleMapper extends BaseMapper<TbPmsRole>{

	/**批量新增数据*/
	int batchInsert(List<TbPmsRole> list);

}