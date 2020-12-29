package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;


/**
 *tenant_info实体类
 */
@TableName("tenant_info")
public class TenantInfo {

	/**id*/
	@TableId(value = "id", type = IdType.AUTO )
	private Long id;

	/**kp*/
	@TableField( value = "kp")
	private String kp;

	/**tenant_id*/
	@TableField( value = "tenant_id")
	private String tenantId;

	/**tenant_name*/
	@TableField( value = "tenant_name")
	private String tenantName;

	/**tenant_desc*/
	@TableField( value = "tenant_desc")
	private String tenantDesc;

	/**create_source*/
	@TableField( value = "create_source")
	private String createSource;

	/**创建时间*/
	@TableField( value = "gmt_create")
	private Long gmtCreate;

	/**修改时间*/
	@TableField( value = "gmt_modified")
	private Long gmtModified;

	public Long getId(){
		return this.id;
	}
	public void setId(Long id){
		this.id = id;
	}

	public String getKp(){
		return this.kp;
	}
	public void setKp(String kp){
		this.kp = kp;
	}

	public String getTenantId(){
		return this.tenantId;
	}
	public void setTenantId(String tenantId){
		this.tenantId = tenantId;
	}

	public String getTenantName(){
		return this.tenantName;
	}
	public void setTenantName(String tenantName){
		this.tenantName = tenantName;
	}

	public String getTenantDesc(){
		return this.tenantDesc;
	}
	public void setTenantDesc(String tenantDesc){
		this.tenantDesc = tenantDesc;
	}

	public String getCreateSource(){
		return this.createSource;
	}
	public void setCreateSource(String createSource){
		this.createSource = createSource;
	}

	public Long getGmtCreate(){
		return this.gmtCreate;
	}
	public void setGmtCreate(Long gmtCreate){
		this.gmtCreate = gmtCreate;
	}

	public Long getGmtModified(){
		return this.gmtModified;
	}
	public void setGmtModified(Long gmtModified){
		this.gmtModified = gmtModified;
	}


	@Override
	public String toString() {
		return "TenantInfo{" +
				"id=" + id +
				", kp=" + kp +
				", tenantId=" + tenantId +
				", tenantName=" + tenantName +
				", tenantDesc=" + tenantDesc +
				", createSource=" + createSource +
				", gmtCreate=" + gmtCreate +
				", gmtModified=" + gmtModified +
				"}";
	}
}