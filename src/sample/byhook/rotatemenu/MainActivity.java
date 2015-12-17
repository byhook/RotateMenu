package sample.byhook.rotatemenu;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.z.widget.BaseItemAdapter;
import cn.z.widget.RotateGroup;
import cn.z.widget.RotateItemBean;
import cn.z.widget.RotateMenu;
import cn.z.widget.RotatePagerAdapter;
import cn.z.widget.RotateTextAdapter;

public class MainActivity extends Activity {

	/**
	 * 旋转菜单
	 */
	private RotateMenu rotateMenu;

	private RotateGroup[] mPages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		initData();
	}

	private void initView(){
		rotateMenu = (RotateMenu) findViewById(R.id.rotateMenu);
	}

	private void initData(){

		List<ApplicationInfo> infos = getPackageManager().getInstalledApplications(0);


		int pages = infos.size()/9;
		if(pages<3)
			return ;

		mPages = new RotateGroup[]{new RotateGroup(this),new RotateGroup(this),new RotateGroup(this)};
		BaseItemAdapter middleItemAdapter = new BaseItemAdapter(this,infos.subList(0,9));
		BaseItemAdapter rightItemAdapter = new BaseItemAdapter(this,infos.subList(9,18));
		BaseItemAdapter downItemAdapter = new BaseItemAdapter(this,infos.subList(18,27));

		mPages[0].setItemAdapter(RotateGroup.CenterType.RIGHT_BOTTOM, middleItemAdapter);
		mPages[1].setItemAdapter(RotateGroup.CenterType.LEFT_BOTTOM, rightItemAdapter);
		mPages[2].setItemAdapter(RotateGroup.CenterType.RIGHT_TOP, downItemAdapter);

		RotatePagerAdapter adapter = new RotatePagerAdapter(mPages);
		rotateMenu.setPageAdapter(adapter);
		String[] titles = new String[]{"快捷方式","经常使用","最近打开"};
		RotateTextAdapter rotateTextAdapter = new RotateTextAdapter(this,titles);
		rotateMenu.setTextAdapter(rotateTextAdapter);
	}

	public void onClick(View view){
		rotateMenu.nextMenu();
	}

}
