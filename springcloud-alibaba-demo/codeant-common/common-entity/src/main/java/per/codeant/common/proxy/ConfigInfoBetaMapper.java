package per.codeant.common.proxy;

import per.codeant.common.entity.ConfigInfoBeta;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConfigInfoBetaMapper extends BaseMapper<ConfigInfoBeta>{

	/**批量新增数据*/
	int batchInsert(List<ConfigInfoBeta> list);

}