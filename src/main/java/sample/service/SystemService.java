package sample.service;

import java.util.Collection;
import java.util.List;

import sample.model.SysArea;
import sample.model.SysDict;
import sample.model.SysFile;
import sample.model.SysMenu;
import sample.model.SysModule;
import sample.model.SysRole;
import sample.model.SysUser;
import sample.utils.Datagrid;
import sample.utils.QueryBuilder;
import sample.utils.UserInfo;

public interface SystemService {
	// Module
	public SysModule loadModule(Integer id);

	public List<SysModule> findModule(QueryBuilder qb);

	public Datagrid datagridModule(QueryBuilder qb);

	public SysModule saveModule(String name, Integer sequence, UserInfo userInfo);

	public SysModule updateModule(Integer id, String name, Integer sequence, UserInfo userInfo);

	public void deleteModule(Collection<Integer> ids, UserInfo userInfo);

	// Menu
	public SysMenu loadMenu(Integer id);

	public List<SysMenu> findMenu(QueryBuilder qb);

	public Datagrid datagridMenu(QueryBuilder qb);

	public SysMenu saveMenu(Integer moduleId, Integer parentId, String name, String url, Integer sequence, String cssClass, UserInfo userInfo);

	public SysMenu updateMenu(Integer id, Integer parentId, String name, String url, Integer sequence, String cssClass, UserInfo userInfo);

	public void deleteMenu(Collection<Integer> ids, UserInfo userInfo);

	// Role
	public SysRole loadRole(Integer id);

	public List<SysRole> findRole(QueryBuilder qb);

	public Datagrid datagridRole(QueryBuilder qb);

	// User
	public SysUser loadUser(Integer id);

	public List<SysUser> findUser(QueryBuilder qb);

	public Datagrid datagridUser(QueryBuilder qb);

	// Dict
	public SysDict loadDict(Integer id);

	public List<SysDict> findDict(QueryBuilder qb);

	public Datagrid datagridDict(QueryBuilder qb);

	public SysDict saveDict(String type, String dictKey, String dictValue, String parentKey, Integer sequence, UserInfo userInfo);

	public SysDict updateDict(Integer id, String dictKey, String dictValue, String parentKey, Integer sequence, UserInfo userInfo);

	public void deleteDict(Collection<Integer> ids, UserInfo userInfo);

	public SysDict getDict(QueryBuilder qb);
	
	// Area
	public SysArea loadArea(Integer id);

	public List<SysArea> findArea(QueryBuilder qb);

	public Datagrid datagridArea(QueryBuilder qb);

	// File
	public SysFile loadFile(Integer id);

	public List<SysFile> findFile(QueryBuilder qb);

	public Datagrid datagridFile(QueryBuilder qb);

	// Other
	public UserInfo login(String username, String password);
}
