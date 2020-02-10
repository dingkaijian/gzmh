package cn.ac.ict.modules.pw.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import cn.ac.ict.modules.pw.entity.PwUpdateRecord;

/**
 * 密码修改记录DAO
 *
 */
@MyBatisDao
public interface PwUpdateRecordDao extends CrudDao<PwUpdateRecord>{

	public int insertSelective(PwUpdateRecord pwUpdateRecord);

	public int getCount();
	
	
}
