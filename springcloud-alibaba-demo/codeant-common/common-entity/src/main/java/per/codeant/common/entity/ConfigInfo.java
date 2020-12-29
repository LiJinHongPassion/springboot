package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;


/**
 *config_info实体类
 */
@TableName("config_info")
public class ConfigInfo {

	/**id*/
	@TableId(value = "id", type = IdType.AUTO )
	private Long id;

	/**data_id*/
	@TableField( value = "data_id")
	private String dataId;

	/***/
	@TableField( value = "group_id")
	private String groupId;

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

	/***/
	@TableField( value = "app_name")
	private String appName;

	/**租户字段*/
	@TableField( value = "tenant_id")
	private String tenantId;

	/***/
	@TableField( value = "c_desc")
	private String cDesc;

	/***/
	@TableField( value = "c_use")
	private String cUse;

	/***/
	@TableField( value = "effect")
	private String effect;

	/***/
	@TableField( value = "type")
	private String type;

	/***/
	@TableField( value = "c_schema")
	private String cSchema;

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

	public String getAppName(){
		return this.appName;
	}
	public void setAppName(String appName){
		this.appName = appName;
	}

	public String getTenantId(){
		return this.tenantId;
	}
	public void setTenantId(String tenantId){
		this.tenantId = tenantId;
	}

	public String getCDesc(){
		return this.cDesc;
	}
	public void setCDesc(String cDesc){
		this.cDesc = cDesc;
	}

	public String getCUse(){
		return this.cUse;
	}
	public void setCUse(String cUse){
		this.cUse = cUse;
	}

	public String getEffect(){
		return this.effect;
	}
	public void setEffect(String effect){
		this.effect = effect;
	}

	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}

	public String getCSchema(){
		return this.cSchema;
	}
	public void setCSchema(String cSchema){
		this.cSchema = cSchema;
	}


	@Override
	public String toString() {
		return "ConfigInfo{" +
				"id=" + id +
				", dataId=" + dataId +
				", groupId=" + groupId +
				", content=" + content +
				", md5=" + md5 +
				", gmtCreate=" + gmtCreate +
				", gmtModified=" + gmtModified +
				", srcUser=" + srcUser +
				", srcIp=" + srcIp +
				", appName=" + appName +
				", tenantId=" + tenantId +
				", cDesc=" + cDesc +
				", cUse=" + cUse +
				", effect=" + effect +
				", type=" + type +
				", cSchema=" + cSchema +
				"}";
	}
}