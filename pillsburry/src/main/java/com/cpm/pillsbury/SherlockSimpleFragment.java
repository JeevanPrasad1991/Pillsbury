package com.cpm.pillsbury;

import java.io.File;

import com.cpm.pillsbury.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SherlockSimpleFragment extends Fragment{
	private MenuItem refreshMenuItem;
	MyApplication mp;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedStateInstance){
		
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.main, container);
		
	}
	
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
		inflater.inflate(R.menu.menus, menu);
		
		super.onCreateOptionsMenu(menu, inflater);
		
		
		
	}
	
public boolean onOptionsItemSelected(MenuItem item){
	
	switch (item.getItemId()){
	 case R.id.action_refresh:
		 
		 //refresh
		 refreshMenuItem = item;
		 
		 deleteCache(getActivity());

		 return true;
		 default: 
			 return super.onOptionsItemSelected(item);

	}
	
    	
    }


public static void deleteCache(Context con){
	try {
		File dir = con.getCacheDir();
		if(dir != null && dir.isDirectory()){
			deleteDir(dir);
			
		}
	} catch (Exception e) {
		// TODO: handle exception
	}
}


public static boolean deleteDir(File dir){
	if (dir != null && dir.isDirectory()) {
        String[] children = dir.list();
        for (int i = 0; i < children.length; i++) {
            boolean success = deleteDir(new File(dir, children[i]));
            if (!success) {
                return false;
            }
        }
	
	
}
	return dir.delete();
}

}
	

	
