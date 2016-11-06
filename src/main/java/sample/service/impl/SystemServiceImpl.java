package sample.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import sample.dao.SysAreaDao;
import sample.dao.SysDictDao;
import sample.dao.SysFileDao;
import sample.dao.SysMenuDao;
import sample.dao.SysModuleDao;
import sample.dao.SysRoleDao;
import sample.dao.SysUserDao;
import sample.model.SysArea;
import sample.model.SysDict;
import sample.model.SysFile;
import sample.model.SysMenu;
import sample.model.SysModule;
import sample.model.SysRole;
import sample.model.SysUser;
import sample.service.SystemService;
import sample.utils.Datagrid;
import sample.utils.DictUtils;
import sample.utils.ModelUtils;
import sample.utils.QueryBuilder;
import sample.utils.QueryUtils;
import sample.utils.UserInfo;
import sample.utils.Utilities;

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
	public Datagrid datagridModule(QueryBuilder qb) {
		return sysModuleDao.datagrid(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SysModule saveModule(String name, Integer sequence, UserInfo userInfo) {
		SysModule sysModule = new SysModule();
		sysModule.setName(name);
		sysModule.setSequence(sequence);
		ModelUtils.setUserInfo(sysModule, userInfo);
		return sysModuleDao.save(sysModule);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SysModule updateModule(Integer id, String name, Integer sequence, UserInfo userInfo) {
		SysModule sysModule = sysModuleDao.load(id);
		sysModule.setName(name);
		sysModule.setSequence(sequence);
		ModelUtils.setUserInfo(sysModule, userInfo);
		return sysModuleDao.update(sysModule);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteModule(Collection<Integer> ids, UserInfo userInfo) {
		QueryBuilder qb = new QueryBuilder();
		QueryUtils.addSetColumn(qb, "t.delFlag", DictUtils.YES);
		QueryUtils.addSetUserInfo(qb, userInfo);
		QueryUtils.addWhere(qb, "and t.delFlag = {0}", DictUtils.NO);
		QueryUtils.addWhere(qb, "and t.id in {0}", ids);
		sysModuleDao.update(qb);

		qb = new QueryBuilder();
		QueryUtils.addSetColumn(qb, "t.delFlag", DictUtils.YES);
		QueryUtils.addSetUserInfo(qb, userInfo);
		QueryUtils.addWhere(qb, "and t.delFlag = {0}", DictUtils.NO);
		QueryUtils.addWhere(qb, "and t.moduleId in {0}", ids);
		sysMenuDao.update(qb);
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
	public Datagrid datagridMenu(QueryBuilder qb) {
		return sysMenuDao.datagrid(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SysMenu saveMenu(Integer moduleId, Integer parentId, String name, String url, Integer sequence, String cssClass, UserInfo userInfo) {
		SysMenu sysMenu = new SysMenu();
		sysMenu.setModuleId(moduleId);
		sysMenu.setParentId(parentId);
		sysMenu.setName(name);
		sysMenu.setUrl(url);
		sysMenu.setSequence(sequence);
		sysMenu.setCssClass(cssClass);
		ModelUtils.setUserInfo(sysMenu, userInfo);
		return sysMenuDao.save(sysMenu);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SysMenu updateMenu(Integer id, Integer parentId, String name, String url, Integer sequence, String cssClass, UserInfo userInfo) {
		SysMenu sysMenu = sysMenuDao.load(id);
		sysMenu.setParentId(parentId);
		sysMenu.setName(name);
		sysMenu.setUrl(url);
		sysMenu.setSequence(sequence);
		sysMenu.setCssClass(cssClass);
		ModelUtils.setUserInfo(sysMenu, userInfo);
		return sysMenuDao.update(sysMenu);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteMenu(Collection<Integer> ids, UserInfo userInfo) {
		List<Integer> deleteIds = Lists.newArrayList(ids);
		List<Integer> parentIds = Lists.newArrayList(ids);

		for (int i = 0; i < 10; ++i) {
			QueryBuilder qb = new QueryBuilder();
			QueryUtils.addWhere(qb, "and t.delFlag = {0}", DictUtils.NO);
			QueryUtils.addWhere(qb, "and t.parentId in {0}", parentIds);
			List<SysMenu> sysMenus = sysMenuDao.find(qb);

			if (sysMenus.size() > 0) {
				parentIds = Utilities.getValues(sysMenus, "id");
				deleteIds.addAll(parentIds);
			} else {
				break;
			}
		}

		QueryBuilder qb = new QueryBuilder();
		QueryUtils.addSetColumn(qb, "t.delFlag", DictUtils.YES);
		QueryUtils.addSetUserInfo(qb, userInfo);
		QueryUtils.addWhere(qb, "and t.id in {0}", deleteIds);
		sysMenuDao.update(qb);
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
	public Datagrid datagridRole(QueryBuilder qb) {
		return sysRoleDao.datagrid(qb);
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
	public Datagrid datagridDict(QueryBuilder qb) {
		return sysDictDao.datagrid(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SysDict saveDict(String type, String dictKey, String dictValue, String parentKey, Integer sequence, UserInfo userInfo) {
		SysDict sysDict = new SysDict();
		sysDict.setType(type);
		sysDict.setDictKey(dictKey);
		sysDict.setDictValue(dictValue);
		sysDict.setParentKey(parentKey);
		sysDict.setSequence(sequence);
		ModelUtils.setUserInfo(sysDict, userInfo);
		return sysDictDao.save(sysDict);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SysDict updateDict(Integer id, String dictKey, String dictValue, String parentKey, Integer sequence, UserInfo userInfo) {
		SysDict sysDict = sysDictDao.load(id);
		sysDict.setDictKey(dictKey);
		sysDict.setDictValue(dictValue);
		sysDict.setParentKey(parentKey);
		sysDict.setSequence(sequence);
		ModelUtils.setUserInfo(sysDict, userInfo);
		return sysDictDao.update(sysDict);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteDict(Collection<Integer> ids, UserInfo userInfo) {
		QueryBuilder qb = new QueryBuilder();
		QueryUtils.addSetColumn(qb, "t.delFlag", DictUtils.YES);
		QueryUtils.addSetUserInfo(qb, userInfo);
		QueryUtils.addWhere(qb, "and t.id in {0}", ids);
		sysDictDao.update(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public SysDict getDict(QueryBuilder qb) {
		return sysDictDao.get(qb);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Map<String, Object>> listMapDict(QueryBuilder qb) {
		return sysDictDao.listMap(qb);
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
	public Datagrid datagridFile(QueryBuilder qb) {
		return sysFileDao.datagrid(qb);
	}

	// Other
	@Transactional(propagation = Propagation.REQUIRED)
	public UserInfo login(String username, String password) {
		QueryBuilder qb = new QueryBuilder();
		QueryUtils.addWhere(qb, "and t.delFlag = {0}", DictUtils.NO);
		QueryUtils.addWhere(qb, "and t.username = {0}", username);
		SysUser sysUser = sysUserDao.get(qb);

		if (sysUser != null && StringUtils.equals(password, sysUser.getPassword())) {
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(sysUser.getId());
			userInfo.setUserName(sysUser.getUsername());
			userInfo.setUserType(sysUser.getType());

			qb = new QueryBuilder();
			QueryUtils.addWhere(qb, "and t.delFlag = {0}", DictUtils.NO);

			if (!userInfo.isAdmin()) {
				List<Integer> roleIds = Utilities.getValues(sysUser.getSysRoles(), "id");
				QueryUtils.addWhere(qb, "and exits(");
				QueryUtils.addWhere(qb, "from t.sysRoles t1 where 1 = 1");
				QueryUtils.addWhere(qb, "and t.delFlag = {0}", DictUtils.NO);
				QueryUtils.addWhere(qb, "and t.id in {0}", roleIds);
				QueryUtils.addWhere(qb, ")");
			}

			List<SysMenu> sysMenus = sysMenuDao.find(qb);
			List<Integer> moduleIds = Utilities.getValues(sysMenus, "moduleId");
			List<Integer> menuIds = Utilities.getValues(sysMenus, "id");
			userInfo.setModuleIds(Sets.newHashSet(moduleIds));
			userInfo.setMenuIds(Sets.newHashSet(menuIds));
			return userInfo;
		}

		return null;
	}
}
