package per.codeant.common.proxy;

import per.codeant.common.entity.TbPmsRoleModule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TbPmsRoleModuleMapper extends BaseMapper<TbPmsRoleModule>{

	/**批量新增数据*/
	int batchInsert(List<TbPmsRoleModule> list);

}