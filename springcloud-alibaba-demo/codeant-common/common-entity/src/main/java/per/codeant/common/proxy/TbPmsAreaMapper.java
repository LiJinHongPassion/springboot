package per.codeant.common.proxy;

import per.codeant.common.entity.TbPmsArea;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TbPmsAreaMapper extends BaseMapper<TbPmsArea>{

	/**批量新增数据*/
	int batchInsert(List<TbPmsArea> list);

}