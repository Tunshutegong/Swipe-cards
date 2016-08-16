package com.example.swipecards.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swipecards.R;
import com.example.swipecards.util.BaseModel;
import com.example.swipecards.util.CardEntity;
import com.example.swipecards.util.RetrofitHelper;
import com.zc.swiple.SwipeFlingView;
import com.zc.swiple.SwipeFlingViewNew;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 卡片Fragment
 *
 * @author zc
 */
public class CardFragment extends Fragment implements SwipeFlingViewNew.onSwipeListener {

    @InjectView(R.id.frame)
    SwipeFlingViewNew mSwipeFlingView;

    private UserAdapter mAdapter;

    private int mPageIndex = 0;
    private boolean mIsRequestGirlList;
    private ArrayList<CardEntity> mGrilList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_layout, null);
        ButterKnife.inject(this, rootView);
        initView();
        requestGirlList();
        return rootView;
    }

    private void initView() {
        mAdapter = new UserAdapter(getActivity(), mGrilList);
        mSwipeFlingView.setAdapter(mAdapter);
        mSwipeFlingView.setFlingListener(this);
    }

    private void updateListView(ArrayList<CardEntity> list) {
        mGrilList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    private void requestGirlList() {
        if (mIsRequestGirlList) {
            return;
        }
        mIsRequestGirlList = true;
        Call<BaseModel<ArrayList<CardEntity>>> call = RetrofitHelper.api().getGirlList(mPageIndex);
        RetrofitHelper.call(call, new RetrofitHelper.ApiCallback<ArrayList<CardEntity>>() {
            @Override
            public void onLoadSucceed(ArrayList<CardEntity> result) {
                updateListView(result);
                ++mPageIndex;
                mIsRequestGirlList = false;
            }

            @Override
            public void onLoadFail(int statusCode) {
                mIsRequestGirlList = false;
            }

            @Override
            public void onForbidden() {
                mIsRequestGirlList = false;
            }
        });
    }

    @Override
    public void onStartDragCard() {

    }

    @Override
    public void onPreCardExit() {

    }

    @Override
    public void onTopCardViewFinish() {

    }

    @Override
    public boolean canLeftCardExit() {
        return true;
    }

    @Override
    public boolean canRightCardExit() {
        return true;
    }

    @Override
    public void onLeftCardExit(View view, Object dataObject, boolean triggerByTouchMove) {

    }

    @Override
    public void onRightCardExit(View view, Object dataObject, boolean triggerByTouchMove) {

    }

    @Override
    public void onSuperLike(View view, Object dataObject, boolean triggerByTouchMove) {

    }

    @Override
    public void onAdapterAboutToEmpty(int itemsInAdapter) {
        requestGirlList();
    }

    @Override
    public void onAdapterEmpty() {

    }

    @Override
    public void onScroll(View selectedView, float scrollProgressPercent) {

    }

    @Override
    public void onEndDragCard() {

    }

    @Override
    public void onEnd() {

    }
}