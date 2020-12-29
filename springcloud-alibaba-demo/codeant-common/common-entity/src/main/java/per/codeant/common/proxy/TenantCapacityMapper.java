package per.codeant.common.proxy;

import per.codeant.common.entity.TenantCapacity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TenantCapacityMapper extends BaseMapper<TenantCapacity>{

	/**批量新增数据*/
	int batchInsert(List<TenantCapacity> list);

}