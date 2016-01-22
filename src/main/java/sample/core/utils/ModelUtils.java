package sample.core.utils;

import sample.core.info.UserInfo;
import sample.core.model.BaseModel;

public class ModelUtils {
	public static void setInfo(BaseModel model, UserInfo userInfo) {
		if (userInfo != null) {
			model.setOperatorId(userInfo.getUserId());
			model.setOperateDate(userInfo.getOperateDate());
		}

		if (!Utilities.isValidId(model.getId())) {
			model.setDelFlag(DictUtils.NO);
		}
	}
}
