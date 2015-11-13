package sample.byhook.rotatemenu;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import cn.z.widget.RotateMenu;

public class MainActivity extends Activity {

	/**
	 * 旋转菜单
	 */
	private RotateMenu rotateMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		rotateMenu = (RotateMenu) findViewById(R.id.rotateMenu);
//		rotateMenu = findViewById(R.id.middle);
//		rotateMenu.setPivotX(360);
//		rotateMenu.setPivotY(360);
//		rotateMenu.setRotation(-90F);
//		rotateMenu.invalidate();
//		ObjectAnimator.ofFloat(rotateMenu, "rotation", 0.0F, -90.0F)
//				.setDuration(5000)
//				.start();
//
//		View child = findViewById(R.id.right);
//		child.setPivotX(0);
//		child.setPivotY(360);
//		ObjectAnimator.ofFloat(child, "rotation", 0.0F, -90.0F)
//				.setDuration(5000)
//				.start();
	}

	public void onClick(View view){
		rotateMenu.rotateMenu();
	}

}
