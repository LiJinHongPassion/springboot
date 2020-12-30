package per.codeant.common.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;


@TableName("tb_pms_user")
public class TbPmsUser {

	/***/
	@TableId(value = "F_USERID", type = IdType.AUTO )
	private Integer fUserid;

	/***/
	@TableField( value = "F_NAME")
	private String fName;

	/***/
	@TableField( value = "F_EMAIL")
	private String fEmail;

	/***/
	@TableField( value = "F_MOBILE")
	private String fMobile;

	/***/
	@TableField( value = "F_PASSWORD")
	private String fPassword;

	/***/
	@TableField( value = "F_FLAG")
	private Integer fFlag;

	/***/
	@TableField( value = "F_REGISTERTIME")
	private Date fRegistertime;

	/***/
	@TableField( value = "F_LASTLOGIN")
	private Date fLastlogin;

	/***/
	@TableField( value = "F_AREAID")
	private String fAreaid;

	/***/
	@TableField( value = "F_LEVEL_ID")
	private Integer fLevelId;

	/***/
	@TableField( value = "F_HOSTID")
	private String fHostid;

	/***/
	@TableField( value = "F_ISLOCK")
	private Integer fIslock;

	/***/
	@TableField( value = "F_LOCKDATEBEGIN")
	private Date fLockdatebegin;

	/***/
	@TableField( value = "IF_SUPERUSER")
	private String ifSuperuser;

	/***/
	@TableField( value = "F_LASTMODIFY")
	private Date fLastmodify;

	public Integer getFUserid(){
		return this.fUserid;
	}
	public void setFUserid(Integer fUserid){
		this.fUserid = fUserid;
	}

	public String getFName(){
		return this.fName;
	}
	public void setFName(String fName){
		this.fName = fName;
	}

	public String getFEmail(){
		return this.fEmail;
	}
	public void setFEmail(String fEmail){
		this.fEmail = fEmail;
	}

	public String getFMobile(){
		return this.fMobile;
	}
	public void setFMobile(String fMobile){
		this.fMobile = fMobile;
	}

	public String getFPassword(){
		return this.fPassword;
	}
	public void setFPassword(String fPassword){
		this.fPassword = fPassword;
	}

	public Integer getFFlag(){
		return this.fFlag;
	}
	public void setFFlag(Integer fFlag){
		this.fFlag = fFlag;
	}

	public Date getFRegistertime(){
		return this.fRegistertime;
	}
	public void setFRegistertime(Date fRegistertime){
		this.fRegistertime = fRegistertime;
	}

	public Date getFLastlogin(){
		return this.fLastlogin;
	}
	public void setFLastlogin(Date fLastlogin){
		this.fLastlogin = fLastlogin;
	}

	public String getFAreaid(){
		return this.fAreaid;
	}
	public void setFAreaid(String fAreaid){
		this.fAreaid = fAreaid;
	}

	public Integer getFLevelId(){
		return this.fLevelId;
	}
	public void setFLevelId(Integer fLevelId){
		this.fLevelId = fLevelId;
	}

	public String getFHostid(){
		return this.fHostid;
	}
	public void setFHostid(String fHostid){
		this.fHostid = fHostid;
	}

	public Integer getFIslock(){
		return this.fIslock;
	}
	public void setFIslock(Integer fIslock){
		this.fIslock = fIslock;
	}

	public Date getFLockdatebegin(){
		return this.fLockdatebegin;
	}
	public void setFLockdatebegin(Date fLockdatebegin){
		this.fLockdatebegin = fLockdatebegin;
	}

	public String getIfSuperuser(){
		return this.ifSuperuser;
	}
	public void setIfSuperuser(String ifSuperuser){
		this.ifSuperuser = ifSuperuser;
	}

	public Date getFLastmodify(){
		return this.fLastmodify;
	}
	public void setFLastmodify(Date fLastmodify){
		this.fLastmodify = fLastmodify;
	}


	@Override
	public String toString() {
		return "TbPmsUser{" +
				"fUserid=" + fUserid +
				", fName=" + fName +
				", fEmail=" + fEmail +
				", fMobile=" + fMobile +
				", fPassword=" + fPassword +
				", fFlag=" + fFlag +
				", fRegistertime=" + fRegistertime +
				", fLastlogin=" + fLastlogin +
				", fAreaid=" + fAreaid +
				", fLevelId=" + fLevelId +
				", fHostid=" + fHostid +
				", fIslock=" + fIslock +
				", fLockdatebegin=" + fLockdatebegin +
				", ifSuperuser=" + ifSuperuser +
				", fLastmodify=" + fLastmodify +
				"}";
	}
}