

package com.viewpagerindicator;

import androidx.viewpager.widget.ViewPager;


public interface PageIndicator extends ViewPager.OnPageChangeListener {
    
    void setViewPager(ViewPager view);

    
    void setViewPager(ViewPager view, int initialPosition);

    
    void setCurrentItem(int item);

    
    void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);

    
    void notifyDataSetChanged();
}
