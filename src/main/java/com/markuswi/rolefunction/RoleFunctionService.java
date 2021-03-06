package com.markuswi.rolefunction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markuswi.function.Function;
import com.markuswi.function.FunctionService;
import com.markuswi.role.Role;
import com.markuswi.role.RoleService;

@Service
public class RoleFunctionService {

	@Autowired
	private RoleFunctionDao roleFunctionDao;

	@Autowired
	private RoleService roleService;

	@Autowired
	private FunctionService functionService;

	public RoleFunction saveRoleFunction(RoleFunction roleFunction) {
		return roleFunctionDao.saveRoleFunction(roleFunction);
	}

	public void createRoleFunctionForNewFunction(Function function) {
		List<Role> allRoles = roleService.loadAllRoles();
		for (Role role : allRoles) {
			createNewRoleFunction(function, role);
		}
	}

	public void createRoleFunctionForNewRole(Role role) {
		List<Function> allFunctions = functionService.loadAllFunctions();
		for (Function function : allFunctions) {
			createNewRoleFunction(function, role);
		}
	}
	
	private void createNewRoleFunction(Function function, Role role) {
		
		if(roleFunctionDao.loadRoleFunctionByFunctionAndRole(function.getId(), role.getId()) == null) {
			RoleFunction roleFunction = new RoleFunction();
			roleFunction.setReadable(function.isReadableByDefault());
			roleFunction.setWriteable(function.isWriteableByDefault());
			roleFunction.setDeleteable(function.isDeleteableByDefault());
			roleFunction.setDeactivateable(function.isDeactivateableByDefault());

			roleFunction.setFunction(function);
			roleFunction.setRole(role);
			this.saveRoleFunction(roleFunction);
		}
	}

	public RoleFunction loadRoalFunctionByFunctionNameAndRole(String functionName, String roleId) {
		RoleFunction roleFunction = null;
		Function function = functionService.loadFunctionByName(functionName);
		if (function != null) {
			roleFunction = roleFunctionDao.loadRoleFunctionByFunctionAndRole(function.getId(), roleId);
		}
		return roleFunction;
	}

	public RoleFunction loadRoleFunctionById(Integer id) {
		return roleFunctionDao.loadRoleFunctionById(id);
	}

}
