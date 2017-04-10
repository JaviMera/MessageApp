package com.teamtreehouse.ribbit.adapters;

import android.view.View;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.viewholders.FragmentRecyclerVH;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecipient;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecipientVH;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecipientsView;

/**
 * Created by javie on 4/10/2017.
 */

public class FragmentRecipientsAdapter extends RecyclerAdapter<User, FragmentRecipientsView>{

    public FragmentRecipientsAdapter(FragmentRecipientsView parent) {
        super(parent);
    }

    @Override
    protected FragmentRecyclerVH getViewHolder(FragmentRecipientsView parent, View view) {
        return new FragmentRecipientVH(parent, view);
    }

    @Override
    protected int getItemLayout() {

        return R.layout.recipient_item;
    }
}
