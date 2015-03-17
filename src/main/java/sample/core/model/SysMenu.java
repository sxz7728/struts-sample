package sample.core.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "sys_menu")
public class SysMenu extends BaseModel {
	@Column(name = "parent_id")
	private Integer parentId;

	private String name;

	private String url;

	private Integer sequence;

	@Column(name = "css_class")
	private String cssClass;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "module_id")
	@Expose(serialize = false, deserialize = false)
	private SysModule sysModule;

	@ManyToMany(mappedBy = "sysMenus")
	@Expose(serialize = false, deserialize = false)
	private List<SysRole> sysRoles;

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public SysModule getSysModule() {
		return sysModule;
	}

	public void setSysModule(SysModule sysModule) {
		this.sysModule = sysModule;
	}

	public List<SysRole> getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(List<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}
}
