package cn.ac.ict.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import cn.ac.ict.modules.sys.entity.AppConfig;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 系统配置表DAO接口
 * @author hujie
 *
 */
@MyBatisDao
public interface AppConfigDao extends CrudDao<AppConfig>{
	
	boolean pushAndClose(@Param("id")String id,@Param("configstate") String configstate);
	AppConfig getByConfigName(@Param("name") String name);
}
