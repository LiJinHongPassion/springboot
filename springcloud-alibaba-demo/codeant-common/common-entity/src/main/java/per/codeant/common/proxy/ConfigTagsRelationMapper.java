package per.codeant.common.proxy;

import per.codeant.common.entity.ConfigTagsRelation;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConfigTagsRelationMapper extends BaseMapper<ConfigTagsRelation>{

	/**批量新增数据*/
	int batchInsert(List<ConfigTagsRelation> list);

}