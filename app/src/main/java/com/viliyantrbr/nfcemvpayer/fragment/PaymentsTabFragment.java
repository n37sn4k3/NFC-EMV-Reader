package com.viliyantrbr.nfcemvpayer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.viliyantrbr.nfcemvpayer.R;
import com.viliyantrbr.nfcemvpayer.adapter.PaymentItemCustomArrayAdapter;
import com.viliyantrbr.nfcemvpayer.adapter.TabLayoutFragmentPagerAdapter;
import com.viliyantrbr.nfcemvpayer.object.PaymentObject;
import com.viliyantrbr.nfcemvpayer.util.LogUtil;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;

public class PaymentsTabFragment extends Fragment implements TabLayoutFragmentPagerAdapter.ITabLayoutFragmentPagerAdapter {
    private static final String TAG = PaymentsTabFragment.class.getSimpleName();

    private CharSequence mPageTitle = null;

    private Realm mRealm = null;

    private RealmResults<PaymentObject> mPaymentObjectRealmResults = null;

    private PaymentItemCustomArrayAdapter mPaymentItemCustomArrayAdapter = null;

    private LinearLayout mPaymentsLinearLayout = null;

    private ListView mPaymentsListView = null;

    public PaymentsTabFragment() {
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
        if (mPaymentObjectRealmResults != null) {
            if (mPaymentObjectRealmResults.isEmpty()) {
                if (mPaymentsLinearLayout != null) {
                    mPaymentsLinearLayout.setVisibility(View.VISIBLE);
                }

                if (mPaymentsListView != null) {
                    mPaymentsListView.setVisibility(View.GONE);
                }
            } else {
                if (mPaymentsLinearLayout != null) {
                    mPaymentsLinearLayout.setVisibility(View.GONE);
                }

                if (mPaymentsListView != null) {
                    mPaymentsListView.setVisibility(View.VISIBLE);
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

            if (mPaymentItemCustomArrayAdapter != null) {
                mPaymentItemCustomArrayAdapter.notifyDataSetChanged();
            }

            updateXml();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "\"" + TAG + "\": Fragment create");

        try {
            mPageTitle = getString(R.string.payments);
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

        return inflater.inflate(R.layout.fragment_payments_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.d(TAG, "\"" + TAG + "\": Fragment view created");

        if (mRealm != null && !mRealm.isClosed()) {
            try {
                mPaymentObjectRealmResults = mRealm.where(PaymentObject.class).findAll();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }

            mPaymentItemCustomArrayAdapter = new PaymentItemCustomArrayAdapter(getFragmentActivity(true), 0, mPaymentObjectRealmResults);

            mPaymentsLinearLayout = getFragmentActivity(false).findViewById(R.id.fragment_paycards_tab_payments_view);

            mPaymentsListView = getFragmentActivity(true).findViewById(R.id.fragment_paycards_tab_payments_list_view);
            mPaymentsListView.setAdapter(mPaymentItemCustomArrayAdapter);

            updateXml();
        }
    }

    @Override
    public Fragment getItem() {
        return this;
    }

    @Override
    public CharSequence getPageTitle() {
        return mPageTitle != null && !mPageTitle.toString().isEmpty() ? mPageTitle : "Payments";
    }

    @Override
    public int getIcon() {
        return R.drawable.payments_tab_icon;
    }
}
