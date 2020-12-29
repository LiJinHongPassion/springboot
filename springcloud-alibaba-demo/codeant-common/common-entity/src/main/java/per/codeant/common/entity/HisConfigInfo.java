package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;
import java.util.Date;
import com.baomidou.mybatisplus.enums.IdType;


/**
 *多租户改造实体类
 */
@TableName("his_config_info")
public class HisConfigInfo {

	/***/
	@TableField( value = "id")
	private String id;

	/***/
	@TableId(value = "nid", type = IdType.AUTO )
	private String nid;

	/***/
	@TableField( value = "data_id")
	private String dataId;

	/***/
	@TableField( value = "group_id")
	private String groupId;

	/**app_name*/
	@TableField( value = "app_name")
	private String appName;

	/***/
	@TableField( value = "content")
	private String content;

	/***/
	@TableField( value = "md5")
	private String md5;

	/***/
	@TableField( value = "gmt_create")
	private Date gmtCreate;

	/***/
	@TableField( value = "gmt_modified")
	private Date gmtModified;

	/***/
	@TableField( value = "src_user")
	private String srcUser;

	/***/
	@TableField( value = "src_ip")
	private String srcIp;

	/***/
	@TableField( value = "op_type")
	private String opType;

	/**租户字段*/
	@TableField( value = "tenant_id")
	private String tenantId;

	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}

	public String getNid(){
		return this.nid;
	}
	public void setNid(String nid){
		this.nid = nid;
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

	public String getOpType(){
		return this.opType;
	}
	public void setOpType(String opType){
		this.opType = opType;
	}

	public String getTenantId(){
		return this.tenantId;
	}
	public void setTenantId(String tenantId){
		this.tenantId = tenantId;
	}


	@Override
	public String toString() {
		return "HisConfigInfo{" +
				"id=" + id +
				", nid=" + nid +
				", dataId=" + dataId +
				", groupId=" + groupId +
				", appName=" + appName +
				", content=" + content +
				", md5=" + md5 +
				", gmtCreate=" + gmtCreate +
				", gmtModified=" + gmtModified +
				", srcUser=" + srcUser +
				", srcIp=" + srcIp +
				", opType=" + opType +
				", tenantId=" + tenantId +
				"}";
	}
}