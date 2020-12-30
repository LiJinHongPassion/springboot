package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;


@TableName("tb_pms_user_group")
public class TbPmsUserGroup {

	/***/
	@TableField( value = "F_USERID")
	private String fUserid;

	/***/
	@TableField( value = "F_GROUPID")
	private String fGroupid;

	public String getFUserid(){
		return this.fUserid;
	}
	public void setFUserid(String fUserid){
		this.fUserid = fUserid;
	}

	public String getFGroupid(){
		return this.fGroupid;
	}
	public void setFGroupid(String fGroupid){
		this.fGroupid = fGroupid;
	}


	@Override
	public String toString() {
		return "TbPmsUserGroup{" +
				"fUserid=" + fUserid +
				", fGroupid=" + fGroupid +
				"}";
	}
}