package com.teamtreehouse.ribbit.ui.fragments.inbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.InboxFragmentAdapter;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.messages.ImageMessage;
import com.teamtreehouse.ribbit.models.messages.Message;
import com.teamtreehouse.ribbit.models.messages.TextMessage;
import com.teamtreehouse.ribbit.models.messages.VideoMessage;
import com.teamtreehouse.ribbit.ui.activities.MainActivity;
import com.teamtreehouse.ribbit.ui.activities.MainActivityView;
import com.teamtreehouse.ribbit.ui.activities.messages.view.ViewImageMessageActivity;
import com.teamtreehouse.ribbit.ui.activities.messages.view.ViewVideoMessageActivity;
import com.teamtreehouse.ribbit.ui.callbacks.ImageMessagesCallback;
import com.teamtreehouse.ribbit.ui.callbacks.MessageListener;
import com.teamtreehouse.ribbit.ui.callbacks.TextMessagesCallback;
import com.teamtreehouse.ribbit.ui.callbacks.VideoMessagesCallback;
import com.teamtreehouse.ribbit.ui.fragments.FragmentPager;
import com.teamtreehouse.ribbit.ui.activities.messages.view.ViewTextMessageActivity;
import com.teamtreehouse.ribbit.utils.Resources;

import butterknife.BindView;

public class FragmentInbox
    extends
        FragmentPager<MainActivityView, FragmentInboxPresenter, InboxFragmentAdapter>
    implements
        FragmentInboxView,
        MessageListener {

    private TextMessagesCallback messagesCallback;
    private ImageMessagesCallback imageMessagesCallback;
    private VideoMessagesCallback videoMessagesCallback;

    @BindView(R.id.emptyMessagesTextView)
    TextView textView;

    @Override
    protected InboxFragmentAdapter createAdapter() {

        return new InboxFragmentAdapter(this);
    }

    @Override
    protected FragmentInboxPresenter createPresenter() {

        return new FragmentInboxPresenter(this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {

        return new LinearLayoutManager(this.getContext());
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_inbox;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        String userId = Auth.getInstance().getId();

        this.messagesCallback = new TextMessagesCallback(userId, this);
        this.imageMessagesCallback = new ImageMessagesCallback(userId, this);
        this.videoMessagesCallback = new VideoMessagesCallback(userId, this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setEmptyTextViewVisibility(boolean visible) {

        textView.setVisibility(
            visible
            ? View.VISIBLE
            : View.GONE
        );
    }

    @Override
    public void onItemSelected(Message message) {

        if(message instanceof TextMessage) {

            Intent intent = new Intent(this.getActivity(), ViewTextMessageActivity.class);
            intent.putExtra(Message.KEY, message);
            startActivity(intent);
        }
        else if(message instanceof ImageMessage) {

            Intent intent = new Intent(getActivity(), ViewImageMessageActivity.class);
            intent.putExtra(Message.KEY, message);
            startActivity(intent);
        }
        else if(message instanceof VideoMessage) {

            Intent intent = new Intent(this.getActivity(), ViewVideoMessageActivity.class);
            intent.putExtra(Message.KEY, message);
            startActivity(intent);
        }
    }

    @Override
    public void execute() {

        // Check if the user has the permissions to read/write in the device before
        // choosing any type of message to send
        boolean granted = this.parent.checkPermissions(MainActivity.PERMISSIONS_STORAGE);

        if(!granted) {

            this.parent.requestPermissions(MainActivity.PERMISSIONS_STORAGE);
            return;
        }

        if(this.parent.getFabMenuVisibility() == View.VISIBLE) {

            this.parent.hideFabMenu();
            this.parent.setFabIcon(R.mipmap.ic_message);
        }
        else {

            this.parent.showFabMenu();
            this.parent.setFabIcon(R.mipmap.ic_cancel);
        }
    }

    @Override
    public void onMessageAdded(Message message) {

        InboxFragmentAdapter adapter = getAdapter();
        adapter.add(message);
    }

    @Override
    public void onMessageRemoved(Message message) {

        InboxFragmentAdapter adapter = getAdapter();
        int position = adapter.getPosition(message);
        adapter.removeItem(position);
    }
}