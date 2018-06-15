package com.viliyantrbr.nfcemvpayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.viliyantrbr.nfcemvpayer.R;
import com.viliyantrbr.nfcemvpayer.activity.ReadPaycardActivity;
import com.viliyantrbr.nfcemvpayer.adapter.PaycardItemCustomArrayAdapter;
import com.viliyantrbr.nfcemvpayer.adapter.TabLayoutFragmentPagerAdapter;
import com.viliyantrbr.nfcemvpayer.object.PaycardObject;
import com.viliyantrbr.nfcemvpayer.util.LogUtil;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;

public class PaycardsTabFragment extends Fragment implements TabLayoutFragmentPagerAdapter.ITabLayoutFragmentPagerAdapter {
    private static final String TAG = PaycardsTabFragment.class.getSimpleName();

    private CharSequence mPageTitle = null;

    private Realm mRealm = null;

    private RealmResults<PaycardObject> mPaycardObjectRealmResults = null;

    private PaycardItemCustomArrayAdapter mPaycardItemCustomArrayAdapter = null;

    private LinearLayout mPaycardsLinearLayout = null;

    private ListView mPaycardsListView = null;

    public PaycardsTabFragment() {
        // Required empty public constructor
    }

    private FragmentActivity getFragmentActivity(boolean requireObjectNonNull) {
        if (requireObjectNonNull) {
            return Objects.requireNonNull(getActivity());
        } else {
            return getActivity();
        }
    } // For usage as context

    private void updateXml() {
        if (mPaycardObjectRealmResults != null) {
            if (mPaycardObjectRealmResults.isEmpty()) {
                if (mPaycardsLinearLayout != null) {
                    mPaycardsLinearLayout.setVisibility(View.VISIBLE);
                }

                if (mPaycardsListView != null) {
                    mPaycardsListView.setVisibility(View.GONE);
                }
            } else {
                if (mPaycardsLinearLayout != null) {
                    mPaycardsLinearLayout.setVisibility(View.GONE);
                }

                if (mPaycardsListView != null) {
                    mPaycardsListView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void updateListView() {
        if (mRealm != null && !mRealm.isClosed()) {
            // Refresh results
            try {
                mRealm.refresh();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
            // - Refresh results

            if (mPaycardItemCustomArrayAdapter != null) {
                mPaycardItemCustomArrayAdapter.notifyDataSetChanged();
            }

            updateXml();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "\"" + TAG + "\": Fragment create");

        try {
            mPageTitle = getString(R.string.paycards);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }

        try {
            mRealm = Realm.getDefaultInstance();
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(TAG, "\"" + TAG + "\": Fragment resume");

        updateListView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "\"" + TAG + "\": Fragment destroy");

        if (mRealm != null) {
            try {
                mRealm.close();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }

            mRealm = null;
        }

        if (mPageTitle != null) {
            mPageTitle = null;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LogUtil.d(TAG, "\"" + TAG + "\": Fragment create view");

        return inflater.inflate(R.layout.fragment_paycards_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.d(TAG, "\"" + TAG + "\": Fragment view created");

        if (mRealm != null && !mRealm.isClosed()) {
            try {
                mPaycardObjectRealmResults = mRealm.where(PaycardObject.class).findAll();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }

            mPaycardItemCustomArrayAdapter = new PaycardItemCustomArrayAdapter(getFragmentActivity(true), 0, mPaycardObjectRealmResults);

            mPaycardsLinearLayout = getFragmentActivity(false).findViewById(R.id.fragment_paycards_tab_paycards_view);

            mPaycardsListView = getFragmentActivity(true).findViewById(R.id.fragment_paycards_tab_paycards_list_view);
            mPaycardsListView.setAdapter(mPaycardItemCustomArrayAdapter);

            updateXml();
        }

        // Fab(s)
        FloatingActionButton cardPositiveFloatingActionButton = getFragmentActivity(true).findViewById(R.id.fragment_paycards_tab_fab_card_positive);
        cardPositiveFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getFragmentActivity(true), ReadPaycardActivity.class));
            }
        });
        // - Fab(s)
    }

    @Override
    public Fragment getItem() {
        return this;
    }

    @Override
    public CharSequence getPageTitle() {
        return mPageTitle != null && !mPageTitle.toString().isEmpty() ? mPageTitle : "Paycards";
    }

    @Override
    public int getIcon() {
        return R.drawable.paycards_tab_icon;
    }
}
