package per.codeant.common.proxy;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import per.codeant.common.entity.ConfigInfoAggr;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConfigInfoAggrMapper extends BaseMapper<ConfigInfoAggr> {

	/**批量新增数据*/
	int batchInsert(List<ConfigInfoAggr> list);

}