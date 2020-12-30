package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;


@TableName("tb_pms_group_role")
public class TbPmsGroupRole {

	/***/
	@TableField( value = "F_groupID")
	private String fGroupid;

	/***/
	@TableField( value = "F_ROLEID")
	private String fRoleid;

	public String getFGroupid(){
		return this.fGroupid;
	}
	public void setFGroupid(String fGroupid){
		this.fGroupid = fGroupid;
	}

	public String getFRoleid(){
		return this.fRoleid;
	}
	public void setFRoleid(String fRoleid){
		this.fRoleid = fRoleid;
	}


	@Override
	public String toString() {
		return "TbPmsGroupRole{" +
				"fGroupid=" + fGroupid +
				", fRoleid=" + fRoleid +
				"}";
	}
}