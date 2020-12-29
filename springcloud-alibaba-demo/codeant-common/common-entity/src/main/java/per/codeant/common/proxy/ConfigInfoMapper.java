package per.codeant.common.proxy;

import per.codeant.common.entity.ConfigInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConfigInfoMapper extends BaseMapper<ConfigInfo>{

	/**批量新增数据*/
	int batchInsert(List<ConfigInfo> list);

}