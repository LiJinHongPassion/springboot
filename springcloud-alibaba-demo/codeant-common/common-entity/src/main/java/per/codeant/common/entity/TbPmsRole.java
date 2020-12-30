package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;


@TableName("tb_pms_role")
public class TbPmsRole {

	/***/
	@TableField( value = "F_ROLEID")
	private String fRoleid;

	/***/
	@TableField( value = "F_NAME")
	private String fName;

	/***/
	@TableField( value = "F_DESC")
	private String fDesc;

	/***/
	@TableField( value = "F_status")
	private Integer fStatus;

	/***/
	@TableField( value = "F_createDate")
	private Date fCreatedate;

	public String getFRoleid(){
		return this.fRoleid;
	}
	public void setFRoleid(String fRoleid){
		this.fRoleid = fRoleid;
	}

	public String getFName(){
		return this.fName;
	}
	public void setFName(String fName){
		this.fName = fName;
	}

	public String getFDesc(){
		return this.fDesc;
	}
	public void setFDesc(String fDesc){
		this.fDesc = fDesc;
	}

	public Integer getFStatus(){
		return this.fStatus;
	}
	public void setFStatus(Integer fStatus){
		this.fStatus = fStatus;
	}

	public Date getFCreatedate(){
		return this.fCreatedate;
	}
	public void setFCreatedate(Date fCreatedate){
		this.fCreatedate = fCreatedate;
	}


	@Override
	public String toString() {
		return "TbPmsRole{" +
				"fRoleid=" + fRoleid +
				", fName=" + fName +
				", fDesc=" + fDesc +
				", fStatus=" + fStatus +
				", fCreatedate=" + fCreatedate +
				"}";
	}
}