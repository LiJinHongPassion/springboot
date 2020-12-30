package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;


@TableName("tb_pms_module")
public class TbPmsModule {

	/***/
	@TableField( value = "MOD_ID")
	private String modId;

	/***/
	@TableField( value = "MOD_PARENTID")
	private String modParentid;

	/***/
	@TableField( value = "MOD_NAME")
	private String modName;

	/***/
	@TableField( value = "MOD_TYPE")
	private Integer modType;

	/***/
	@TableField( value = "MOD_DESC")
	private String modDesc;

	/***/
	@TableField( value = "MOD_URL")
	private String modUrl;

	/***/
	@TableField( value = "MOD_COUNTER")
	private Integer modCounter;

	/***/
	@TableField( value = "MOD_ORDER")
	private Integer modOrder;

	public String getModId(){
		return this.modId;
	}
	public void setModId(String modId){
		this.modId = modId;
	}

	public String getModParentid(){
		return this.modParentid;
	}
	public void setModParentid(String modParentid){
		this.modParentid = modParentid;
	}

	public String getModName(){
		return this.modName;
	}
	public void setModName(String modName){
		this.modName = modName;
	}

	public Integer getModType(){
		return this.modType;
	}
	public void setModType(Integer modType){
		this.modType = modType;
	}

	public String getModDesc(){
		return this.modDesc;
	}
	public void setModDesc(String modDesc){
		this.modDesc = modDesc;
	}

	public String getModUrl(){
		return this.modUrl;
	}
	public void setModUrl(String modUrl){
		this.modUrl = modUrl;
	}

	public Integer getModCounter(){
		return this.modCounter;
	}
	public void setModCounter(Integer modCounter){
		this.modCounter = modCounter;
	}

	public Integer getModOrder(){
		return this.modOrder;
	}
	public void setModOrder(Integer modOrder){
		this.modOrder = modOrder;
	}


	@Override
	public String toString() {
		return "TbPmsModule{" +
				"modId=" + modId +
				", modParentid=" + modParentid +
				", modName=" + modName +
				", modType=" + modType +
				", modDesc=" + modDesc +
				", modUrl=" + modUrl +
				", modCounter=" + modCounter +
				", modOrder=" + modOrder +
				"}";
	}
}