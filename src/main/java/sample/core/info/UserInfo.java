package sample.core.info;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import sample.core.utils.DictUtils;

public class UserInfo {
	private Integer userId;

	private String userName;

	private String userType;

	private List<Integer> moduleIds;

	private List<Integer> menuIds;

	private Date operateDate;

	public boolean isAdmin() {
		return StringUtils.equals(userType, DictUtils.USER_TYPE_ADMIN);
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public List<Integer> getModuleIds() {
		return moduleIds;
	}

	public void setModuleIds(List<Integer> moduleIds) {
		this.moduleIds = moduleIds;
	}

	public List<Integer> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<Integer> menuIds) {
		this.menuIds = menuIds;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
}
