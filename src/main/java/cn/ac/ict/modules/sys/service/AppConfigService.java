package cn.ac.ict.modules.sys.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ac.ict.modules.sys.dao.AppConfigDao;
import cn.ac.ict.modules.sys.entity.AppConfig;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.JedisClusterUtils;
import com.thinkgem.jeesite.common.utils.JedisUtils;
import com.thoughtworks.xstream.mapper.Mapper.Null;

/**
 * 系统配置表service
 * @author hujie
 *
 */
@Service
@Transactional(readOnly = true)
public class AppConfigService extends CrudService<AppConfigDao, AppConfig>{
	
	@Autowired
	private AppConfigDao appConfigDao;
	/**
	 * 开启和关闭策略
	 */
	@Transactional(readOnly = false)
	public Boolean pushAndClose(@Param("id")String id,@Param("configstate") String configstate){
		return super.dao.pushAndClose(id,configstate);
	}
	/**
	 * 根据名字查询 并处理缓存问题
	 */
	@Transactional(readOnly = false)
	public AppConfig getByConfigName(@Param("name")String name){
		AppConfig appConfig = (AppConfig)JedisUtils.getObject("config_"+name);
		if(appConfig == null){
			appConfig = dao.getByConfigName(name);
			System.out.println("缓存为空，从数据表中获取数据");
			String result = JedisUtils.setObject("config_"+appConfig.getConfigname(), appConfig, 0);
			if(result != null && result != ""){
				System.out.println("从表中获取完毕，存入缓存中，key为config_"+appConfig.getConfigname());
			}
			return appConfig;
		}else{
			System.out.println("缓存不为空，从缓存中获取！");
			return appConfig;
		}
//		AppConfig appConfig = dao.getByConfigName(name);
//		if(appConfig != null){
//			return appConfig;
//		}else{
//			return null;
//		}
		
	}
}
