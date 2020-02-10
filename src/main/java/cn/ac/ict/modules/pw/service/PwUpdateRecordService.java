package cn.ac.ict.modules.pw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;

import cn.ac.ict.modules.pw.dao.PwUpdateRecordDao;
import cn.ac.ict.modules.pw.entity.PwUpdateRecord;

/**
 * 密码修改记录Service
 */
@Service
@Transactional(readOnly = true)
public class PwUpdateRecordService extends CrudService<PwUpdateRecordDao, PwUpdateRecord>{
	
	@Autowired
	private PwUpdateRecordDao pwUpdateRecordDao;
	
	public int insertSelective(PwUpdateRecord pwUpdateRecord){
		return dao.insertSelective(pwUpdateRecord);
	}

	public int getCount() {
		return pwUpdateRecordDao.getCount();
		
	}
	
}
