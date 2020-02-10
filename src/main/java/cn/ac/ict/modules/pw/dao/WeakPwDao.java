package cn.ac.ict.modules.pw.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import cn.ac.ict.modules.pw.entity.WeakPw;

/**
 * 常见弱密码DAO
 *
 */
@MyBatisDao
public interface WeakPwDao extends CrudDao<WeakPw>{
	//获取所有弱密码
	public List<WeakPw> getAll();
	public int insertpw(String pw);
}
