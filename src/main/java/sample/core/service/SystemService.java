package sample.core.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import sample.core.info.UserInfo;
import sample.core.model.SysArea;
import sample.core.model.SysDict;
import sample.core.model.SysFile;
import sample.core.model.SysMenu;
import sample.core.model.SysModule;
import sample.core.model.SysRole;
import sample.core.model.SysUser;
import sample.core.utils.Datagrid;
import sample.core.utils.QueryBuilder;

public interface SystemService {
	// Module
	public SysModule loadModule(Integer id);

	public List<SysModule> findModule(QueryBuilder qb);

	public Integer countModule(QueryBuilder qb);

	public Datagrid datagridModule(QueryBuilder qb);

	public SysModule saveModule(String name, Integer sequence, UserInfo userInfo);

	public SysModule updateModule(Integer id, String name, Integer sequence, UserInfo userInfo);
	
	public void deleteModule(Collection<Integer> ids);
	
	// Menu
	public SysMenu loadMenu(Integer id);

	public List<SysMenu> findMenu(QueryBuilder qb);

	public Integer countMenu(QueryBuilder qb);

	public Datagrid datagridMenu(QueryBuilder qb);

	public SysMenu saveMenu(Integer moduleId, Integer parentId, String name, String url, Integer sequence,
			String cssClass, UserInfo userInfo);

	public SysMenu updateMenu(Integer id, Integer parentId, String name, String url, Integer sequence, String cssClass,
			UserInfo userInfo);

	public void deleteMenu(Collection<Integer> ids);

	// Role
	public SysRole loadRole(Integer id);

	public List<SysRole> findRole(QueryBuilder qb);

	public Integer countRole(QueryBuilder qb);

	public Datagrid datagridRole(QueryBuilder qb);

	public SysRole saveRole(String name, Integer sequence, List<Integer> menuIds, UserInfo userInfo);

	public SysRole updateRole(Integer id, String name, Integer sequence, List<Integer> menuIds, UserInfo userInfo);

	// User
	public SysUser loadUser(Integer id);

	public List<SysUser> findUser(QueryBuilder qb);

	public Integer countUser(QueryBuilder qb);

	public Datagrid datagridUser(QueryBuilder qb);

	// Dict
	public SysDict loadDict(Integer id);

	public List<SysDict> findDict(QueryBuilder qb);

	public Integer countDict(QueryBuilder qb);

	public Datagrid datagridDict(QueryBuilder qb);

	public List<Map<String, ?>> dictionaryDict(QueryBuilder qb);

	public SysDict saveDict(String type, String dictKey, String dictValue, String parentKey, Integer sequence,
			UserInfo userInfo);

	public SysDict updateDict(Integer id, String dictKey, String dictValue, String parentKey, Integer sequence,
			UserInfo userInfo);

	public Integer deleteDict(Integer id);

	// Area
	public SysArea loadArea(Integer id);

	public List<SysArea> findArea(QueryBuilder qb);

	public Integer countArea(QueryBuilder qb);

	public Datagrid datagridArea(QueryBuilder qb);

	// File
	public SysFile loadFile(Integer id);

	public List<SysFile> findFile(QueryBuilder qb);

	public Integer countFile(QueryBuilder qb);

	public Datagrid datagridFile(QueryBuilder qb);

	// Other
	public UserInfo login(String username, String password);
}
