package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;
import java.util.Date;
import com.baomidou.mybatisplus.enums.IdType;


/**
 *增加租户字段实体类
 */
@TableName("config_info_aggr")
public class ConfigInfoAggr {

	/**id*/
	@TableId(value = "id", type = IdType.AUTO )
	private Long id;

	/**data_id*/
	@TableField( value = "data_id")
	private String dataId;

	/**group_id*/
	@TableField( value = "group_id")
	private String groupId;

	/**datum_id*/
	@TableField( value = "datum_id")
	private String datumId;

	/**内容*/
	@TableField( value = "content")
	private String content;

	/**修改时间*/
	@TableField( value = "gmt_modified")
	private Date gmtModified;

	/***/
	@TableField( value = "app_name")
	private String appName;

	/**租户字段*/
	@TableField( value = "tenant_id")
	private String tenantId;

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

	public String getDatumId(){
		return this.datumId;
	}
	public void setDatumId(String datumId){
		this.datumId = datumId;
	}

	public String getContent(){
		return this.content;
	}
	public void setContent(String content){
		this.content = content;
	}

	public Date getGmtModified(){
		return this.gmtModified;
	}
	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
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


	@Override
	public String toString() {
		return "ConfigInfoAggr{" +
				"id=" + id +
				", dataId=" + dataId +
				", groupId=" + groupId +
				", datumId=" + datumId +
				", content=" + content +
				", gmtModified=" + gmtModified +
				", appName=" + appName +
				", tenantId=" + tenantId +
				"}";
	}
}