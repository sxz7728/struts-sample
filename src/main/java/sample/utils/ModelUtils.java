package sample.utils;

import sample.info.UserInfo;
import sample.model.BaseModel;

public class ModelUtils {
	public static void setInfo(BaseModel model, UserInfo userInfo) {
		if (userInfo != null) {
			model.setOperatorId(userInfo.getUserId());
			model.setOperateDate(userInfo.getOperateDate());
		}

		if (!Utilities.isValidId(model.getId())) {
			model.setDelFlag(DictUtils.NO);
			model.setCreatorId(model.getOperatorId());
			model.setCreateDate(model.getOperateDate());
		}
	}
}
