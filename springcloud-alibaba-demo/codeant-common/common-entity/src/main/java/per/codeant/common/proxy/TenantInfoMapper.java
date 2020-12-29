package per.codeant.common.proxy;

import per.codeant.common.entity.TenantInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TenantInfoMapper extends BaseMapper<TenantInfo>{

	/**批量新增数据*/
	int batchInsert(List<TenantInfo> list);

}