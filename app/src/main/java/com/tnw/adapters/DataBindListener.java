package com.tnw.adapters;

import java.util.List;

public interface DataBindListener<ML extends List<?>>{
	
	ML getMList();
	
	void appendList(ML list);
	
}
