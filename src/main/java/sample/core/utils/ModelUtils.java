package sample.core.utils;

import sample.core.info.UserInfo;
import sample.core.model.BaseModel;

public class ModelUtils {
	public static void setOperator(BaseModel model, UserInfo userInfo) {
		model.setOperatorId(userInfo.getUserId());
		model.setOperateDate(userInfo.getOperateDate());
	}
}
