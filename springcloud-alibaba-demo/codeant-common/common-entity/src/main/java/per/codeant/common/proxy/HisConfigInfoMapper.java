package per.codeant.common.proxy;

import per.codeant.common.entity.HisConfigInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HisConfigInfoMapper extends BaseMapper<HisConfigInfo>{

	/**批量新增数据*/
	int batchInsert(List<HisConfigInfo> list);

}