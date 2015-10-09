package com.tnw.mvp;

import com.tnw.entities.CategoryNode;

import java.util.List;

/**
 * Created by CSH on 2015/9/18 0018.
 */
public interface HomeBaseView extends MVPView{

    void showMenu(List<CategoryNode> list);

    void showAddress(List<CategoryNode> list);

}
