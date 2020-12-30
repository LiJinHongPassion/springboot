package per.codeant.common.proxy;

import per.codeant.common.entity.TbPmsUserGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TbPmsUserGroupMapper extends BaseMapper<TbPmsUserGroup>{

	/**批量新增数据*/
	int batchInsert(List<TbPmsUserGroup> list);

}