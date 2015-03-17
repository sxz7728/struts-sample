package sample.core.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import sample.core.dao.SysAreaDao;
import sample.core.dao.SysDictDao;
import sample.core.dao.SysFileDao;
import sample.core.dao.SysMenuDao;
import sample.core.dao.SysModuleDao;
import sample.core.dao.SysRoleDao;
import sample.core.dao.SysUserDao;
import sample.core.info.UserInfo;
import sample.core.model.SysArea;
import sample.core.model.SysDict;
import sample.core.model.SysFile;
import sample.core.model.SysMenu;
import sample.core.model.SysModule;
import sample.core.model.SysRole;
import sample.core.model.SysUser;
import sample.core.service.SystemService;
import sample.core.utils.Datagrid;
import sample.core.utils.DictUtils;
import sample.core.utils.ModelUtils;
import sample.core.utils.QueryBuilder;
import sample.core.utils.QueryUtils;

@Service
public class SystemServiceImpl implements SystemService {
	@Autowired
	private SysModuleDao sysModuleDao;

	@Autowired
	private SysMenuDao sysMenuDao;

	@Autowired
	private SysRoleDao sysRoleDao;

	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private SysDictDao sysDictDao;

	@Autowired
	private SysAreaDao sysAreaDao;

	@Autowired
	private SysFileDao sysFileDao;

	// Module
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public SysModule loadModule(Integer id) {
		return sysModuleDao.load(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<SysModule> findModule(QueryBuilder qb) {
		return sysModuleDao.find(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Integer countModule(QueryBuilder qb) {
		return sysModuleDao.count(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Datagrid datagridModule(QueryBuilder qb) {
		return sysModuleDao.datagrid(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SysModule saveModule(String name, Integer sequence, UserInfo userInfo) {
		SysModule sysModule = new SysModule();
		sysModule.setName(name);
		sysModule.setSequence(sequence);
		sysModule.setDeleted(DictUtils.NO);
		ModelUtils.setOperator(sysModule, userInfo);
		return sysModuleDao.save(sysModule);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SysModule updateModule(Integer id, String name, Integer sequence,
			UserInfo userInfo) {
		SysModule sysModule = sysModuleDao.load(id);
		sysModule.setName(name);
		sysModule.setSequence(sequence);
		ModelUtils.setOperator(sysModule, userInfo);
		return sysModuleDao.update(sysModule);
	}

	// Menu
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public SysMenu loadMenu(Integer id) {
		return sysMenuDao.load(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<SysMenu> findMenu(QueryBuilder qb) {
		return sysMenuDao.find(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Integer countMenu(QueryBuilder qb) {
		return sysMenuDao.count(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Datagrid datagridMenu(QueryBuilder qb) {
		return sysMenuDao.datagrid(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SysMenu saveMenu(Integer moduleId, Integer parentId, String name,
			String url, Integer sequence, String cssClass, UserInfo userInfo) {
		SysMenu sysMenu = new SysMenu();
		sysMenu.setSysModule(sysModuleDao.load(moduleId));
		sysMenu.setParentId(parentId);
		sysMenu.setName(name);
		sysMenu.setUrl(url);
		sysMenu.setSequence(sequence);
		sysMenu.setCssClass(cssClass);
		sysMenu.setDeleted(DictUtils.NO);
		ModelUtils.setOperator(sysMenu, userInfo);
		return sysMenuDao.save(sysMenu);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SysMenu updateMenu(Integer id, Integer parentId, String name,
			String url, Integer sequence, String cssClass, UserInfo userInfo) {
		SysMenu sysMenu = sysMenuDao.load(id);
		sysMenu.setParentId(parentId);
		sysMenu.setName(name);
		sysMenu.setUrl(url);
		sysMenu.setSequence(sequence);
		sysMenu.setCssClass(cssClass);
		ModelUtils.setOperator(sysMenu, userInfo);
		return sysMenuDao.update(sysMenu);
	}

	// Role
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public SysRole loadRole(Integer id) {
		return sysRoleDao.load(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<SysRole> findRole(QueryBuilder qb) {
		return sysRoleDao.find(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Integer countRole(QueryBuilder qb) {
		return sysRoleDao.count(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Datagrid datagridRole(QueryBuilder qb) {
		return sysRoleDao.datagrid(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SysRole saveRole(String name, Integer sequence,
			List<Integer> menuIds, UserInfo userInfo) {
		SysRole sysRole = new SysRole();
		sysRole.setName(name);
		sysRole.setSequence(sequence);

		QueryBuilder qb = QueryUtils.addWhereNotDeleted(new QueryBuilder());
		QueryUtils.addWhereWithDefault(qb, "and t.id in {0}", menuIds, -1);
		List<SysMenu> sysMenus = sysMenuDao.find(qb);
		sysRole.setSysMenus(sysMenus);

		sysRole.setDeleted(DictUtils.NO);
		ModelUtils.setOperator(sysRole, userInfo);
		return sysRoleDao.save(sysRole);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SysRole updateRole(Integer id, String name, Integer sequence,
			List<Integer> menuIds, UserInfo userInfo) {
		SysRole sysRole = sysRoleDao.load(id);
		sysRole.setName(name);
		sysRole.setSequence(sequence);

		QueryBuilder qb = QueryUtils.addWhereNotDeleted(new QueryBuilder());
		QueryUtils.addWhereWithDefault(qb, "and t.id in {0}", menuIds, -1);
		List<SysMenu> sysMenus = sysMenuDao.find(qb);
		sysRole.setSysMenus(sysMenus);

		ModelUtils.setOperator(sysRole, userInfo);
		return sysRoleDao.save(sysRole);
	}

	// User
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public SysUser loadUser(Integer id) {
		return sysUserDao.load(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<SysUser> findUser(QueryBuilder qb) {
		return sysUserDao.find(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Integer countUser(QueryBuilder qb) {
		return sysUserDao.count(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Datagrid datagridUser(QueryBuilder qb) {
		return sysUserDao.datagrid(qb);
	}

	// Dict
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public SysDict loadDict(Integer id) {
		return sysDictDao.load(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<SysDict> findDict(QueryBuilder qb) {
		return sysDictDao.find(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Integer countDict(QueryBuilder qb) {
		return sysDictDao.count(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Datagrid datagridDict(QueryBuilder qb) {
		return sysDictDao.datagrid(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Map<String, ?>> dictionaryDict(QueryBuilder qb) {
		return sysDictDao.dictionary(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SysDict saveDict(String type, String dictKey, String dictValue,
			String parentKey, Integer sequence, UserInfo userInfo) {
		SysDict sysDict = new SysDict();
		sysDict.setType(type);
		sysDict.setDictKey(dictKey);
		sysDict.setDictValue(dictValue);
		sysDict.setParentKey(parentKey);
		sysDict.setSequence(sequence);

		sysDict.setDeleted(DictUtils.NO);
		ModelUtils.setOperator(sysDict, userInfo);
		return sysDictDao.save(sysDict);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SysDict updateDict(Integer id, String dictKey, String dictValue,
			String parentKey, Integer sequence, UserInfo userInfo) {
		SysDict sysDict = sysDictDao.load(id);
		sysDict.setDictKey(dictKey);
		sysDict.setDictValue(dictValue);
		sysDict.setParentKey(parentKey);
		sysDict.setSequence(sequence);

		ModelUtils.setOperator(sysDict, userInfo);
		return sysDictDao.update(sysDict);
	}

	// Area
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public SysArea loadArea(Integer id) {
		return sysAreaDao.load(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<SysArea> findArea(QueryBuilder qb) {
		return sysAreaDao.find(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Integer countArea(QueryBuilder qb) {
		return sysAreaDao.count(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Datagrid datagridArea(QueryBuilder qb) {
		return sysAreaDao.datagrid(qb);
	}

	// File
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public SysFile loadFile(Integer id) {
		return sysFileDao.load(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<SysFile> findFile(QueryBuilder qb) {
		return sysFileDao.find(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Integer countFile(QueryBuilder qb) {
		return sysFileDao.count(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Datagrid datagridFile(QueryBuilder qb) {
		return sysFileDao.datagrid(qb);
	}

	// Other
	@Transactional(propagation = Propagation.REQUIRED)
	public UserInfo login(String username, String password) {
		QueryBuilder qb = QueryUtils.addWhereNotDeleted(new QueryBuilder());
		QueryUtils.addWhere(qb, "and t.username = {0}", username);

		List<SysUser> sysUsers = sysUserDao.find(qb);

		if (sysUsers.size() == 1) {
			SysUser sysUser = sysUsers.get(0);

			if (StringUtils.equals(password, sysUser.getPassword())) {
				UserInfo userInfo = new UserInfo();
				userInfo.setUserId(sysUser.getId());
				userInfo.setUserName(sysUser.getUsername());
				userInfo.setUserType(sysUser.getType());

				List<SysMenu> sysMenus = null;

				if (userInfo.isAdmin()) {
					qb = QueryUtils.addWhereNotDeleted(new QueryBuilder());
					sysMenus = sysMenuDao.find(qb);
				} else {
					sysMenus = Lists.newArrayList();

					for (SysRole sysRole : sysUser.getSysRoles()) {
						sysMenus = ListUtils.union(sysMenus,
								sysRole.getSysMenus());
					}
				}

				List<Integer> moduleIds = Lists.newArrayList();
				List<Integer> menuIds = Lists.newArrayList();

				for (SysMenu sysMenu : sysMenus) {
					if (!DictUtils.getYesNo(sysMenu.getDeleted())) {
						if (!menuIds.contains(sysMenu.getId())) {
							menuIds.add(sysMenu.getId());
						}

						SysModule sysModule = sysMenu.getSysModule();

						if (!moduleIds.contains(sysModule.getId())) {
							moduleIds.add(sysModule.getId());
						}
					}
				}

				userInfo.setModuleIds(moduleIds);
				userInfo.setMenuIds(menuIds);
				return userInfo;
			}
		}

		return null;
	}
}
