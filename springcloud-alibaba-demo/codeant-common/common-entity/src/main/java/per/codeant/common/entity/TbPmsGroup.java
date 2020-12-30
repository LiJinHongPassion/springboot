package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;


@TableName("tb_pms_group")
public class TbPmsGroup {

	/***/
	@TableField( value = "F_GROUPID")
	private String fGroupid;

	/***/
	@TableField( value = "F_GROUPNAME")
	private String fGroupname;

	/***/
	@TableField( value = "F_FLAG")
	private Integer fFlag;

	/***/
	@TableField( value = "F_AREA_ID")
	private String fAreaId;

	/***/
	@TableField( value = "F_GROUPDESC")
	private String fGroupdesc;

	public String getFGroupid(){
		return this.fGroupid;
	}
	public void setFGroupid(String fGroupid){
		this.fGroupid = fGroupid;
	}

	public String getFGroupname(){
		return this.fGroupname;
	}
	public void setFGroupname(String fGroupname){
		this.fGroupname = fGroupname;
	}

	public Integer getFFlag(){
		return this.fFlag;
	}
	public void setFFlag(Integer fFlag){
		this.fFlag = fFlag;
	}

	public String getFAreaId(){
		return this.fAreaId;
	}
	public void setFAreaId(String fAreaId){
		this.fAreaId = fAreaId;
	}

	public String getFGroupdesc(){
		return this.fGroupdesc;
	}
	public void setFGroupdesc(String fGroupdesc){
		this.fGroupdesc = fGroupdesc;
	}


	@Override
	public String toString() {
		return "TbPmsGroup{" +
				"fGroupid=" + fGroupid +
				", fGroupname=" + fGroupname +
				", fFlag=" + fFlag +
				", fAreaId=" + fAreaId +
				", fGroupdesc=" + fGroupdesc +
				"}";
	}
}