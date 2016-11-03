package sample.utils;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import sample.info.UserInfo;
import sample.model.BaseModel;

public class ModelUtils {
	public static void setUserInfo(BaseModel model, UserInfo userInfo) {
		if (userInfo != null) {
			model.setOperator(userInfo.getUserId());
			model.setOperateDate(userInfo.getOperateDate());
		} else {
			model.setOperateDate(new Date());
		}

		if (!Utilities.isValidId(model.getId())) {
			model.setDelFlag(DictUtils.NO);
			model.setCreator(model.getOperator());
			model.setCreateDate(model.getOperateDate());
		}

		if (StringUtils.isEmpty(model.getUuid())) {
			model.setUuid(UUID.randomUUID().toString());
		}
	}
}
