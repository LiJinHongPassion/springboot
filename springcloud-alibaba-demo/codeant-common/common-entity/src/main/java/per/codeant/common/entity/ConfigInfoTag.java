package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;
import java.util.Date;
import com.baomidou.mybatisplus.enums.IdType;


/**
 *config_info_tag实体类
 */
@TableName("config_info_tag")
public class ConfigInfoTag {

	/**id*/
	@TableId(value = "id", type = IdType.AUTO )
	private Long id;

	/**data_id*/
	@TableField( value = "data_id")
	private String dataId;

	/**group_id*/
	@TableField( value = "group_id")
	private String groupId;

	/**tenant_id*/
	@TableField( value = "tenant_id")
	private String tenantId;

	/**tag_id*/
	@TableField( value = "tag_id")
	private String tagId;

	/**app_name*/
	@TableField( value = "app_name")
	private String appName;

	/**content*/
	@TableField( value = "content")
	private String content;

	/**md5*/
	@TableField( value = "md5")
	private String md5;

	/**创建时间*/
	@TableField( value = "gmt_create")
	private Date gmtCreate;

	/**修改时间*/
	@TableField( value = "gmt_modified")
	private Date gmtModified;

	/**source user*/
	@TableField( value = "src_user")
	private String srcUser;

	/**source ip*/
	@TableField( value = "src_ip")
	private String srcIp;

	public Long getId(){
		return this.id;
	}
	public void setId(Long id){
		this.id = id;
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

	public String getTagId(){
		return this.tagId;
	}
	public void setTagId(String tagId){
		this.tagId = tagId;
	}

	public String getAppName(){
		return this.appName;
	}
	public void setAppName(String appName){
		this.appName = appName;
	}

	public String getContent(){
		return this.content;
	}
	public void setContent(String content){
		this.content = content;
	}

	public String getMd5(){
		return this.md5;
	}
	public void setMd5(String md5){
		this.md5 = md5;
	}

	public Date getGmtCreate(){
		return this.gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified(){
		return this.gmtModified;
	}
	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}

	public String getSrcUser(){
		return this.srcUser;
	}
	public void setSrcUser(String srcUser){
		this.srcUser = srcUser;
	}

	public String getSrcIp(){
		return this.srcIp;
	}
	public void setSrcIp(String srcIp){
		this.srcIp = srcIp;
	}


	@Override
	public String toString() {
		return "ConfigInfoTag{" +
				"id=" + id +
				", dataId=" + dataId +
				", groupId=" + groupId +
				", tenantId=" + tenantId +
				", tagId=" + tagId +
				", appName=" + appName +
				", content=" + content +
				", md5=" + md5 +
				", gmtCreate=" + gmtCreate +
				", gmtModified=" + gmtModified +
				", srcUser=" + srcUser +
				", srcIp=" + srcIp +
				"}";
	}
}