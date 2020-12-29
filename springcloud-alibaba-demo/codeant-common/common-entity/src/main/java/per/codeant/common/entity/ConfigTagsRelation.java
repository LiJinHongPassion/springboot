package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;


/**
 *config_tag_relation实体类
 */
@TableName("config_tags_relation")
public class ConfigTagsRelation {

	/**id*/
	@TableField( value = "id")
	private Long id;

	/**tag_name*/
	@TableField( value = "tag_name")
	private String tagName;

	/**tag_type*/
	@TableField( value = "tag_type")
	private String tagType;

	/**data_id*/
	@TableField( value = "data_id")
	private String dataId;

	/**group_id*/
	@TableField( value = "group_id")
	private String groupId;

	/**tenant_id*/
	@TableField( value = "tenant_id")
	private String tenantId;

	/***/
	@TableId(value = "nid", type = IdType.AUTO )
	private Long nid;

	public Long getId(){
		return this.id;
	}
	public void setId(Long id){
		this.id = id;
	}

	public String getTagName(){
		return this.tagName;
	}
	public void setTagName(String tagName){
		this.tagName = tagName;
	}

	public String getTagType(){
		return this.tagType;
	}
	public void setTagType(String tagType){
		this.tagType = tagType;
	}

	public String getDataId(){
		return this.dataId;
	}
	public void setDataId(String dataId){
		this.dataId = dataId;
	}

	public String getGroupId(){
		return this.groupId;
	}
	public void setGroupId(String groupId){
		this.groupId = groupId;
	}

	public String getTenantId(){
		return this.tenantId;
	}
	public void setTenantId(String tenantId){
		this.tenantId = tenantId;
	}

	public Long getNid(){
		return this.nid;
	}
	public void setNid(Long nid){
		this.nid = nid;
	}


	@Override
	public String toString() {
		return "ConfigTagsRelation{" +
				"id=" + id +
				", tagName=" + tagName +
				", tagType=" + tagType +
				", dataId=" + dataId +
				", groupId=" + groupId +
				", tenantId=" + tenantId +
				", nid=" + nid +
				"}";
	}
}