package per.codeant.common.proxy;

import per.codeant.common.entity.GroupCapacity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GroupCapacityMapper extends BaseMapper<GroupCapacity>{

	/**批量新增数据*/
	int batchInsert(List<GroupCapacity> list);

}