package com.tnd.multifuction.interfaces;


import com.tnd.multifuction.model.UserInputModel;

import java.util.List;

public interface onItemSelectedListener<T extends UserInputModel> {

    int getSelectedCount();

    List<T> getSelectedList();
}
