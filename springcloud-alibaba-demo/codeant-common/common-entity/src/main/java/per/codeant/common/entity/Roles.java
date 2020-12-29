package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;


@TableName("roles")
public class Roles {

	/***/
	@TableField( value = "username")
	private String username;

	/***/
	@TableField( value = "role")
	private String role;

	public String getUsername(){
		return this.username;
	}
	public void setUsername(String username){
		this.username = username;
	}

	public String getRole(){
		return this.role;
	}
	public void setRole(String role){
		this.role = role;
	}


	@Override
	public String toString() {
		return "Roles{" +
				"username=" + username +
				", role=" + role +
				"}";
	}
}