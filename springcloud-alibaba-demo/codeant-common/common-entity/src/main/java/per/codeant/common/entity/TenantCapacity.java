package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;
import java.util.Date;
import com.baomidou.mybatisplus.enums.IdType;


/**
 *租户容量信息表实体类
 */
@TableName("tenant_capacity")
public class TenantCapacity {

	/**主键ID*/
	@TableId(value = "id", type = IdType.AUTO )
	private String id;

	/**Tenant ID*/
	@TableField( value = "tenant_id")
	private String tenantId;

	/**配额，0表示使用默认值*/
	@TableField( value = "quota")
	private Integer quota;

	/**使用量*/
	@TableField( value = "usage")
	private Integer usage;

	/**单个配置大小上限，单位为字节，0表示使用默认值*/
	@TableField( value = "max_size")
	private Integer maxSize;

	/**聚合子配置最大个数*/
	@TableField( value = "max_aggr_count")
	private Integer maxAggrCount;

	/**单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值*/
	@TableField( value = "max_aggr_size")
	private Integer maxAggrSize;

	/**最大变更历史数量*/
	@TableField( value = "max_history_count")
	private Integer maxHistoryCount;

	/**创建时间*/
	@TableField( value = "gmt_create")
	private Date gmtCreate;

	/**修改时间*/
	@TableField( value = "gmt_modified")
	private Date gmtModified;

	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}

	public String getTenantId(){
		return this.tenantId;
	}
	public void setTenantId(String tenantId){
		this.tenantId = tenantId;
	}

	public Integer getQuota(){
		return this.quota;
	}
	public void setQuota(Integer quota){
		this.quota = quota;
	}

	public Integer getUsage(){
		return this.usage;
	}
	public void setUsage(Integer usage){
		this.usage = usage;
	}

	public Integer getMaxSize(){
		return this.maxSize;
	}
	public void setMaxSize(Integer maxSize){
		this.maxSize = maxSize;
	}

	public Integer getMaxAggrCount(){
		return this.maxAggrCount;
	}
	public void setMaxAggrCount(Integer maxAggrCount){
		this.maxAggrCount = maxAggrCount;
	}

	public Integer getMaxAggrSize(){
		return this.maxAggrSize;
	}
	public void setMaxAggrSize(Integer maxAggrSize){
		this.maxAggrSize = maxAggrSize;
	}

	public Integer getMaxHistoryCount(){
		return this.maxHistoryCount;
	}
	public void setMaxHistoryCount(Integer maxHistoryCount){
		this.maxHistoryCount = maxHistoryCount;
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


	@Override
	public String toString() {
		return "TenantCapacity{" +
				"id=" + id +
				", tenantId=" + tenantId +
				", quota=" + quota +
				", usage=" + usage +
				", maxSize=" + maxSize +
				", maxAggrCount=" + maxAggrCount +
				", maxAggrSize=" + maxAggrSize +
				", maxHistoryCount=" + maxHistoryCount +
				", gmtCreate=" + gmtCreate +
				", gmtModified=" + gmtModified +
				"}";
	}
}