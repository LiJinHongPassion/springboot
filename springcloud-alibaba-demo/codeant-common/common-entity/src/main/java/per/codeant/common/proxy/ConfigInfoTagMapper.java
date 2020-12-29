package per.codeant.common.proxy;

import per.codeant.common.entity.ConfigInfoTag;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConfigInfoTagMapper extends BaseMapper<ConfigInfoTag>{

	/**批量新增数据*/
	int batchInsert(List<ConfigInfoTag> list);

}