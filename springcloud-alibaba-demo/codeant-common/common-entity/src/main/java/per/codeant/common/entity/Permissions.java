package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;


@TableName("permissions")
public class Permissions {

	/***/
	@TableField( value = "role")
	private String role;

	/***/
	@TableField( value = "resource")
	private String resource;

	/***/
	@TableField( value = "action")
	private String action;

	public String getRole(){
		return this.role;
	}
	public void setRole(String role){
		this.role = role;
	}

	public String getResource(){
		return this.resource;
	}
	public void setResource(String resource){
		this.resource = resource;
	}

	public String getAction(){
		return this.action;
	}
	public void setAction(String action){
		this.action = action;
	}


	@Override
	public String toString() {
		return "Permissions{" +
				"role=" + role +
				", resource=" + resource +
				", action=" + action +
				"}";
	}
}