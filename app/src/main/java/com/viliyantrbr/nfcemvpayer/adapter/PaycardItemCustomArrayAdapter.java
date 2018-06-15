package com.viliyantrbr.nfcemvpayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.viliyantrbr.nfcemvpayer.R;
import com.viliyantrbr.nfcemvpayer.activity.PaycardActivity;
import com.viliyantrbr.nfcemvpayer.object.PaycardObject;
import com.viliyantrbr.nfcemvpayer.util.AidUtil;
import com.viliyantrbr.nfcemvpayer.util.HexUtil;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmResults;

public class PaycardItemCustomArrayAdapter extends ArrayAdapter<PaycardObject> {
    private LayoutInflater mLayoutInflater;

    public PaycardItemCustomArrayAdapter(@NonNull Context context, int resource, @NonNull RealmResults<PaycardObject> paycardObjectRealmResults) {
        super(context, resource, paycardObjectRealmResults);

        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        final PaycardObject paycardObject = getItem(position);

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_paycard, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.paycardPanTextView = convertView.findViewById(R.id.item_paycard_pan);
            viewHolder.paycardTypeImageView = convertView.findViewById(R.id.item_paycard_type_image);
            viewHolder.paycardAidTextView = convertView.findViewById(R.id.item_paycard_aid);
            viewHolder.paycardTypeTextView = convertView.findViewById(R.id.item_paycard_type);
            viewHolder.paycardExpDateTextView = convertView.findViewById(R.id.item_paycard_exp_date);
            viewHolder.paycardAddDateTextView = convertView.findViewById(R.id.item_paycard_add_date);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), PaycardActivity.class);
                    if (paycardObject != null) {
                        intent.putExtra(view.getContext().getString(R.string.pan_var_name), paycardObject.getApplicationPan());
                    }

                    view.getContext().startActivity(intent);
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (paycardObject != null) {
            // PAN
            String panString = HexUtil.bytesToHexadecimal(paycardObject.getApplicationPan());

            viewHolder.paycardPanTextView.setText(panString != null ? panString : "N/A");
            // - PAN

            // Type (image)
            if (Arrays.equals(paycardObject.getAid(), AidUtil.A0000000041010)) {
                viewHolder.paycardTypeImageView.setImageResource(R.drawable.card_mastercard);
            } else if (Arrays.equals(paycardObject.getAid(), AidUtil.A0000000043060)) {
                viewHolder.paycardTypeImageView.setImageResource(R.drawable.card_maestro);
            } else if (Arrays.equals(paycardObject.getAid(), AidUtil.A0000000031010)) {
                viewHolder.paycardTypeImageView.setImageResource(R.drawable.card_visa);
            } else if (Arrays.equals(paycardObject.getAid(), AidUtil.A0000000032010)) {
                viewHolder.paycardTypeImageView.setImageResource(R.drawable.card_visa_electron);
            } else {
                // TODO: Default paycard image
            }
            // - Type (image)

            // AID
            String aidString = HexUtil.bytesToHexadecimal(paycardObject.getAid());

            viewHolder.paycardAidTextView.setText("AID: " + (aidString != null ? aidString : "N/A"));
            // - AID

            // Type (text)
            if (Arrays.equals(paycardObject.getAid(), AidUtil.A0000000041010)) {
                viewHolder.paycardTypeTextView.setText("Mastercard (PayPass)");
            } else if (Arrays.equals(paycardObject.getAid(), AidUtil.A0000000043060)) {
                viewHolder.paycardTypeTextView.setText("Maestro (PayPass)");
            } else if (Arrays.equals(paycardObject.getAid(), AidUtil.A0000000031010)) {
                viewHolder.paycardTypeTextView.setText("Visa (PayWave)");
            } else if (Arrays.equals(paycardObject.getAid(), AidUtil.A0000000032010)) {
                viewHolder.paycardTypeTextView.setText("Visa Electron (PayWave)");
            } else {
                viewHolder.paycardTypeTextView.setText("Type: N/A");
            }
            // - Type (text)

            // Exp Date
            String expDateString = null;

            Date expDate = null;
            try {
                expDate = new SimpleDateFormat("yyMMdd", Locale.getDefault()).parse(HexUtil.bytesToHexadecimal(paycardObject.getApplicationExpirationDate()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }

            if (expDate != null) {
                try {
                    expDateString = new SimpleDateFormat("MM/yy", Locale.getDefault()).format(expDate);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println(e.toString());

                    e.printStackTrace();
                }
            }

            viewHolder.paycardExpDateTextView.setText("Exp. Date: " + (expDateString != null ? expDateString : "N/A"));
            // - Exp Date

            // Add Date
            String addDateString = null;

            Date addDate = null;
            try {
                addDate = paycardObject.getAddDate();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.toString());

                e.printStackTrace();
            }

            if (addDate != null) {
                try {
                    addDateString = new SimpleDateFormat("HH:mm:ss dd/MM/yy", Locale.getDefault()).format(addDate);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println(e.toString());

                    e.printStackTrace();
                }

                viewHolder.paycardAddDateTextView.setText(addDateString != null ? addDateString : "N/A");
            }
            // - Add Date
        } else {
            viewHolder.paycardPanTextView.setText("N/A"); // PAN

            // TODO: Default paycard image // Type (image)

            viewHolder.paycardAidTextView.setText("AID: N/A"); // AID

            viewHolder.paycardTypeTextView.setText("Type: N/A"); // Type (text)

            viewHolder.paycardExpDateTextView.setText("Exp. Date: N/A"); // Exp Date

            viewHolder.paycardAddDateTextView.setText("Added / Updated: N/A"); // Add Date
        }

        return convertView;
    }

    private class ViewHolder {
        TextView paycardPanTextView = null;
        ImageView paycardTypeImageView = null;
        TextView paycardAidTextView = null;
        TextView paycardTypeTextView = null;
        TextView paycardExpDateTextView = null;
        TextView paycardAddDateTextView = null;
    }
}
