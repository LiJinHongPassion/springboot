package per.codeant.common.proxy;

import per.codeant.common.entity.TbPmsUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TbPmsUserMapper extends BaseMapper<TbPmsUser>{

	/**批量新增数据*/
	int batchInsert(List<TbPmsUser> list);

}