package per.codeant.common.proxy;

import per.codeant.common.entity.TbPmsModule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TbPmsModuleMapper extends BaseMapper<TbPmsModule>{

	/**批量新增数据*/
	int batchInsert(List<TbPmsModule> list);

}