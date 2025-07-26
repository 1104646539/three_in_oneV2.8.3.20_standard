package com.tnd.jinbiao.model;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.tnd.multifuction.db.DbHelper;

public class ResultModel {

	protected DbUtils dbUtils = DbHelper.GetInstance();

	public int id;
	public String number;
	public String company_name;
	public String persion;
	public String shiji;
	public String project_name;
	public String xian;
	public String lin;
	public String check_value;
	public String style_long;
	public String check_result;
	public long time;
	public String sample_name;
	public String sample_type;
	public String weight;
	public String mobile;
	public String orgin;
	public String url;
	public String twh;
	public String eid;

	public String concentrateUnit;

	public String sample_unit;//数据库版本为2
	public String sample_number;//数据库版本为2
	public int uploadId;

	public boolean isSelected;

	public boolean update(String[] columns) {

		try {
			dbUtils.update(this, columns);
			return true;
		} catch (DbException e) {
			e.printStackTrace();
			return false;
		}
	}
}
