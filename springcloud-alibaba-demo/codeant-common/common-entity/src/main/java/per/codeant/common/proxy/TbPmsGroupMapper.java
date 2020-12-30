package per.codeant.common.proxy;

import per.codeant.common.entity.TbPmsGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TbPmsGroupMapper extends BaseMapper<TbPmsGroup>{

	/**批量新增数据*/
	int batchInsert(List<TbPmsGroup> list);

}