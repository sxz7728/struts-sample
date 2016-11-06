package sample.utils;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class UserInfo {
	private Integer userId;

	private String userName;

	private String userType;

	private Set<Integer> moduleIds;

	private Set<Integer> menuIds;

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

	public Set<Integer> getModuleIds() {
		return moduleIds;
	}

	public void setModuleIds(Set<Integer> moduleIds) {
		this.moduleIds = moduleIds;
	}

	public Set<Integer> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(Set<Integer> menuIds) {
		this.menuIds = menuIds;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
}
