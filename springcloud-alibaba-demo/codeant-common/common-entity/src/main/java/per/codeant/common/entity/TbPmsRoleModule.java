package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;


@TableName("tb_pms_role_module")
public class TbPmsRoleModule {

	/***/
	@TableField( value = "MOD_ID")
	private String modId;

	/***/
	@TableField( value = "F_ROLEID")
	private String fRoleid;

	public String getModId(){
		return this.modId;
	}
	public void setModId(String modId){
		this.modId = modId;
	}

	public String getFRoleid(){
		return this.fRoleid;
	}
	public void setFRoleid(String fRoleid){
		this.fRoleid = fRoleid;
	}


	@Override
	public String toString() {
		return "TbPmsRoleModule{" +
				"modId=" + modId +
				", fRoleid=" + fRoleid +
				"}";
	}
}