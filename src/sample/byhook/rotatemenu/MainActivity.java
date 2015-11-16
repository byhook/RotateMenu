package sample.byhook.rotatemenu;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.z.widget.BaseItemAdapter;
import cn.z.widget.RotateGroup;
import cn.z.widget.RotateItemBean;
import cn.z.widget.RotateMenu;

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
		List<RotateItemBean> items = new ArrayList<RotateItemBean>();

		List<ApplicationInfo> infos = getPackageManager().getInstalledApplications(0);
		RotateItemBean bean = null;
		ApplicationInfo info;

		int pages = infos.size()/9;
		if(pages<3)
			return ;

		mPages = new RotateGroup[]{new RotateGroup(this),new RotateGroup(this),new RotateGroup(this)};
		BaseItemAdapter middleItemAdapter = new BaseItemAdapter(this,infos.subList(0,9));
		BaseItemAdapter rightItemAdapter = new BaseItemAdapter(this,infos.subList(9,18));
		BaseItemAdapter downItemAdapter = new BaseItemAdapter(this,infos.subList(18,27));

		mPages[0].setItemAdapter(RotateGroup.CenterType.RIGHT_BOTTOM,middleItemAdapter);
		mPages[1].setItemAdapter(RotateGroup.CenterType.LEFT_BOTTOM,rightItemAdapter);
		mPages[2].setItemAdapter(RotateGroup.CenterType.RIGHT_TOP,downItemAdapter);

		PageAdapter adapter = new PageAdapter(mPages);
		rotateMenu.setPageAdapter(adapter);
	}

	public void onClick(View view){
		rotateMenu.rotateMenu();
	}

}
