package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;


@TableName("users")
public class Users {

	/***/
	@TableId(value = "username", type = IdType.INPUT )
	private String username;

	/***/
	@TableField( value = "password")
	private String password;

	/***/
	@TableField( value = "enabled")
	private Byte enabled;

	public String getUsername(){
		return this.username;
	}
	public void setUsername(String username){
		this.username = username;
	}

	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password = password;
	}

	public Byte getEnabled(){
		return this.enabled;
	}
	public void setEnabled(Byte enabled){
		this.enabled = enabled;
	}


	@Override
	public String toString() {
		return "Users{" +
				"username=" + username +
				", password=" + password +
				", enabled=" + enabled +
				"}";
	}
}