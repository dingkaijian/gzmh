package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.AffairsListDao;
import com.thinkgem.jeesite.modules.sys.entity.AffairsList;

import cn.ac.ict.modules.client.entity.OAuthClient;


/**
 * 事项信息列表Service
 * @author bixue
 * 20200103
 */
@Service
@Transactional(readOnly = true)
public class AffairsListService extends CrudService<AffairsListDao, AffairsList> {

	@Autowired
	private AffairsListDao affairsListDao;
	
	/**
	 * 获取事项列表信息
	 */
	public List<AffairsList> findList(AffairsList affairsList){
		return super.findList(affairsList);
	}
	/**
	 * 获取用户关注信息
	 * @param userId
	 * @param key
	 * @return
	 */
	@Transactional(readOnly = false,rollbackFor=RuntimeException.class)
	public List<AffairsList> selectAllByUser(String userId) {
		List<AffairsList> AffairsLists=affairsListDao.selectAllByUser(userId);
		List<OAuthClient> AffairsLists2=affairsListDao.selectAffairButton();
		if(AffairsLists.size()>0){
			for(int i=0;i<AffairsLists.size();i++){
				AffairsList Affairs=AffairsLists.get(i);
				if(StringUtils.isNotEmpty(Affairs.getAffairname())&&Affairs.getAffairname().contains("人类遗传资源管理")){
					Affairs.setClientlist(AffairsLists2);
				}
			}
		}
		return AffairsLists;
	}
}