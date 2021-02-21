package tk.mybatis.simple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import tk.mybatis.simple.model.SysRole;

/*
 * RoleMapper接口主要演示了MyBatis注解方式的基本用法。
 * 这种方式的优点是，对于需求比较简单的系统，效率较高。
 * 但是， 
 * 由于注解方式写在代码中，SQL语句较长时，需要手动拼接字符串，
 * 当SQL变动时，需要重新编译代码，不方便维护。
 * 因此，一般情况下不建议使用注解方式（包括@Provider注解）。 
 * */
@CacheNamespaceRef(RoleMapper.class)
public interface RoleMapper {
	
	@Select({
	  "select id, role_name roleName, enabled, create_by createBy, create_time createTime",
		"from sys_role",
		"where id = #{id}"})
	SysRole selectById(Long id);
	
  // 对应XML中的 resultMap 元素
	@Results(id = "roleResultMap", value = {
		@Result(property = "id", column = "id", id = true),
		@Result(property = "roleName", column = "role_name"),
		@Result(property = "enabled", column = "enabled"),
		@Result(property = "createBy", column = "create_by"),
		@Result(property = "createTime", column = "create_time")
	})
	@Select("select id, role_name, enabled, create_by, create_time from sys_role where id = #{id}")
	SysRole selectById2(Long id);
	
	@ResultMap("roleResultMap") // 引用了id为“roleResultMap”的@Results
	@Select("select * from sys_role")
	List<SysRole> selectAll();
	
	List<SysRole> selectAll(RowBounds rowBounds);
	
	@Insert({
	  "insert into sys_role(id, role_name, enabled, create_by, create_time)", 
		"values(#{id}, #{roleName}, #{enabled}, #{createBy}, #{createTime, jdbcType=TIMESTAMP})"
    })
	int insert(SysRole sysRole);
	
	@Insert({
	  "insert into sys_role(role_name, enabled, create_by, create_time)", 
    "values(#{roleName}, #{enabled}, #{createBy}, #{createTime, jdbcType=TIMESTAMP})"
	  })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert2(SysRole sysRole);
	
	@Insert({
	  "insert into sys_role(role_name, enabled, create_by, create_time)", 
    "values(#{roleName}, #{enabled}, #{createBy}, #{createTime, jdbcType=TIMESTAMP})"
	  })
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", 
	  keyProperty = "id", 
	  resultType = Long.class, 
	  before = false)
	int insert3(SysRole sysRole);
	
	@Update({
	  "update sys_role", 
	  "set role_name = #{roleName}, enabled = #{enabled}, ", 
	  "    create_by = #{createBy}, create_time = #{createTime, jdbcType=TIMESTAMP} ", 
	  "where id = #{id}"
		})
	int updateById(SysRole sysRole);
	
	@Delete("delete from sys_role where id = #{id}")
	int deleteById(Long id);
	
	/**
	 * 获取所有角色和对应的权限信息
	 * 
	 * @return
	 */
	List<SysRole> selectAllRoleAndPrivileges();
	
	/**
	 * 根据用户 ID 获取用户的角色信息
	 * 
	 * @param userId
	 * @return
	 */
	List<SysRole> selectRoleByUserIdChoose(Long userId);
	
}

