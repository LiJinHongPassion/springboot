package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;


@TableName("tb_pms_area")
public class TbPmsArea {

	/***/
	@TableField( value = "AREA_ID")
	private String areaId;

	/***/
	@TableField( value = "AREA_NAME")
	private String areaName;

	/***/
	@TableField( value = "AREA_DESC")
	private String areaDesc;

	/***/
	@TableField( value = "PARENT_AREA_ID")
	private String parentAreaId;

	/***/
	@TableField( value = "SORT_ORDER")
	private Integer sortOrder;

	public String getAreaId(){
		return this.areaId;
	}
	public void setAreaId(String areaId){
		this.areaId = areaId;
	}

	public String getAreaName(){
		return this.areaName;
	}
	public void setAreaName(String areaName){
		this.areaName = areaName;
	}

	public String getAreaDesc(){
		return this.areaDesc;
	}
	public void setAreaDesc(String areaDesc){
		this.areaDesc = areaDesc;
	}

	public String getParentAreaId(){
		return this.parentAreaId;
	}
	public void setParentAreaId(String parentAreaId){
		this.parentAreaId = parentAreaId;
	}

	public Integer getSortOrder(){
		return this.sortOrder;
	}
	public void setSortOrder(Integer sortOrder){
		this.sortOrder = sortOrder;
	}


	@Override
	public String toString() {
		return "TbPmsArea{" +
				"areaId=" + areaId +
				", areaName=" + areaName +
				", areaDesc=" + areaDesc +
				", parentAreaId=" + parentAreaId +
				", sortOrder=" + sortOrder +
				"}";
	}
}