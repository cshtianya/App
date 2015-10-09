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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

/**
 * Nice wrapper-abstraction around ArrayList
 * @author 
 * @param <T>
 */
public abstract class ArrayListAdapter<T> extends BaseAdapter{
	
	protected List<T> mList = new ArrayList<T>();
	public List<T> getmList() {
		return mList;
	}

	protected Context mContext;
	protected AbsListView mListView;

	protected LayoutInflater inflater;

	public ArrayListAdapter(Context context){
		this.mContext = context;
		inflater = LayoutInflater.from(context);
	}
	
	public void clear()
	{
		mList.clear();
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if(mList != null)
			return mList.size();
		else
			return 0;
	}

	@Override
	public T getItem(int position) {
		return mList == null ? null : mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	abstract public View getView(int position, View convertView, ViewGroup parent);
	
	public void setList(List<T> list,boolean boo){
		this.mList = list;
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
	
	public AbsListView getListView(){
		return mListView;
	}
	
	public void setListView(AbsListView listView){
		mListView = listView;
	}
	
}
