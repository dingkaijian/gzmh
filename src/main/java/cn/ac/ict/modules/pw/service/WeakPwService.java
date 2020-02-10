package cn.ac.ict.modules.pw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.thinkgem.jeesite.common.service.CrudService;
import cn.ac.ict.modules.pw.dao.WeakPwDao;
import cn.ac.ict.modules.pw.entity.WeakPw;

@Service
@Transactional(readOnly = true)
public class WeakPwService  extends CrudService<WeakPwDao,WeakPw>{
	/**
	 * 获取所有弱密码
	 * @return
	 */
	public List<WeakPw> getAll(){
		return dao.getAll();
	}
	/**
	 * 校验密码是否为弱密码
	 * @param weakPws
	 * @return
	 */
	public boolean validateWeakPw(List<WeakPw> weakPws,String pw){
		int states=0;
		for(WeakPw weakPw:weakPws){
			if(pw.equals(weakPw.getPassword())){
				states+=1;
				break;
			}
		}
		if(states!=0){
			return false;
		}else{
			return true;
		}
	}
	
	public int insertpw(String pw){
		return dao.insertpw(pw);
	}
}
