package sample.byhook.rotatemenu;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
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


		rotateMenu = (RotateMenu) findViewById(R.id.rotateMenu);

		List<RotateItemBean> items = new ArrayList<RotateItemBean>();
		for(int i=0;i<5;i++){
			items.add(new RotateItemBean());
		}

		mPages = new RotateGroup[]{new RotateGroup(this),new RotateGroup(this),new RotateGroup(this)};
		BaseItemAdapter middleItemAdapter = new BaseItemAdapter(this,items);
		BaseItemAdapter rightItemAdapter = new BaseItemAdapter(this,items);
		BaseItemAdapter downItemAdapter = new BaseItemAdapter(this,items);

		mPages[0].setItemAdapter(RotateGroup.CenterType.RIGHT_BOTTOM,middleItemAdapter);
		mPages[1].setItemAdapter(RotateGroup.CenterType.LEFT_BOTTOM,rightItemAdapter);
		mPages[2].setItemAdapter(RotateGroup.CenterType.RIGHT_TOP,downItemAdapter);
		mPages[0].setBackgroundColor(Color.YELLOW);

		PageAdapter adapter = new PageAdapter(mPages);
		rotateMenu.setPageAdapter(adapter);
	}

	public void onClick(View view){
		rotateMenu.rotateMenu();
	}

}
