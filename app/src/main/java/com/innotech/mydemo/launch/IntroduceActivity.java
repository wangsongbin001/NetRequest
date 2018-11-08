package com.innotech.mydemo.launch;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.innotech.mydemo.R;
import com.innotech.netrequest.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class IntroduceActivity extends BaseAppCompatActivity {
    public static final int[] RES = new int[]{R.drawable.guide_page01, R.drawable.guide_page02,
            R.drawable.guide_page03, R.drawable.guide_page04};

    @Bind(R.id.vp_intr)
    ViewPager vpIntr;
    @Bind(R.id.view0)
    View view0;
    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.view2)
    View view2;
    @Bind(R.id.view3)
    View view3;
    private List<View> dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        dots = new ArrayList<>();
        dots.add(view0);
        dots.add(view1);
        dots.add(view2);
        dots.add(view3);
        vpIntr.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeDotBg(position);
            }
        });
        vpIntr.setAdapter(new IntroduceAdapter(RES, this));
        changeDotBg(0);
    }

    private void changeDotBg(int position) {
        for (int i = 0; i < dots.size(); i++) {
            if(i == position){
                dots.get(i).setBackgroundResource(R.drawable.shape_dot_red);
                continue;
            }
            dots.get(i).setBackgroundResource(R.drawable.shape_dot_gray);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    class IntroduceAdapter extends PagerAdapter {
        private int[] mRes;
        private Context mContext;

        public IntroduceAdapter(int[] res, Context context) {
            this.mRes = res;
            this.mContext = context;
        }

        @Override
        public int getCount() {
            if (this.mRes == null) {
                return 0;
            }
            return this.mRes.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_introduce, container, false);
            ((ImageView) view.findViewById(R.id.introduce_img)).setImageResource(mRes[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
