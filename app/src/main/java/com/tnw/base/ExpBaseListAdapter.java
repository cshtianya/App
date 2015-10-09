/*
 * Copyright (C) 2009 Teleca Poland Sp. z o.o. <android@teleca.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tnw.base;

import android.app.Activity;
import android.content.Context;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Nice wrapper-abstraction around ArrayList
 * @param <T>
 */
public abstract class ExpBaseListAdapter<T> extends BaseExpandableListAdapter{
	
	protected List<T> mList = new ArrayList<T>();
	public List<T> getmList() {
		return mList;
	}

	protected Context mContext;

	public ExpBaseListAdapter(Activity context){
		this.mContext = context;
	}
	
	public void clear()
	{
		mList.clear();
		notifyDataSetChanged();
	}
	
	@Override
	public int getGroupCount() {
		return mList.size();
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public T getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		if(mList!=null && mList.size()>0)
			return mList.get(groupPosition);
		else
			return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void setList(List<T> list,boolean boo){
		this.mList = list;
		if(boo)
		{
			notifyDataSetChanged();
		}
	}

	public void appendList(List<T> list,boolean boo){
		this.mList.addAll(list);
		if(boo)
		{
			notifyDataSetChanged();
		}
	}
	
	public List<T> getList(){
		return mList;
	}
	
	public void setList(T[] list,boolean boo){
		ArrayList<T> arrayList = new ArrayList<T>(list.length);  
		for (T t : list) {  
			arrayList.add(t);  
		}  
		setList(arrayList,boo);
	}
	
}
