package com.teamtreehouse.ribbit.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.MessageAdapter;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.models.MessageFile;
import com.teamtreehouse.ribbit.models.Query;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.callbacks.FindCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InboxFragment extends Fragment
    implements FragmentRecyclerView {

    private int[] themeColors = new int[] {
        R.color.swipeRefresh1,
        R.color.swipeRefresh2,
        R.color.swipeRefresh3,
        R.color.swipeRefresh4
    };

    protected List<Message> messages;
    protected FragmentRecyclerPresenter presenter;
    protected FragmentActivity parent;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.messagesRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.emptyMessagesTextView)
    TextView textView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.parent = (FragmentActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inbox,
                container, false);

        ButterKnife.bind(this, rootView);

        presenter = new FragmentRecyclerPresenter(this);
        presenter.setRecyclerAdapter(new MessageAdapter(this));
        presenter.setRecyclerLayout(new LinearLayoutManager(this.parent));
        presenter.setRecyclerFixedSize(true);

        swipeRefreshLayout.setOnRefreshListener(createRefresherLayoutListener());
        swipeRefreshLayout.setColorSchemeResources(themeColors);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        retrieveMessages();
    }

    @Override
    public void onResume() {
        super.onResume();

//        getActivity().setProgressBarIndeterminateVisibility(true);
    }

    private void retrieveMessages() {
        Query<Message> query = Message.getQuery();
        query.whereEqualTo(Message.KEY_RECIPIENT_IDS, User.getCurrentUser().getObjectId());
        query.addDescendingOrder(Message.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Message>() {
            @Override
            public void done(List<Message> messages, Exception e) {
//                getActivity().setProgressBarIndeterminateVisibility(false);

                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }

                if (e == null) {
                    // We found messages!
                    InboxFragment.this.messages = messages;
                    MessageAdapter adapter = (MessageAdapter) recyclerView.getAdapter();

                    String[] usernames = new String[InboxFragment.this.messages.size()];
                    int i = 0;
                    for (Message message : InboxFragment.this.messages) {
                        usernames[i] = message.getString(Message.KEY_SENDER_NAME);
                        i++;
                    }

                    adapter.addMessages(InboxFragment.this.messages);

                    textView.setVisibility(View.GONE);
                }
                else {

                    textView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    protected OnRefreshListener createRefresherLayoutListener() {

        return new OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveMessages();
            }
        };
    }

    @Override
    public void onItemSingleClick(Message message) {

        String messageType = message.getString(Message.KEY_FILE_TYPE);
        MessageFile file = message.getFile(Message.KEY_FILE);
        Uri fileUri = file.getUri();

        if (messageType.equals(Message.TYPE_IMAGE)) {
            // view the image
            Intent intent = new Intent(getActivity(), ViewImageActivity.class);
            intent.setData(fileUri);
            startActivity(intent);
        } else {
            // view the video
            Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
            intent.setDataAndType(fileUri, "video/*");
            startActivity(intent);
        }

        // Delete it!
        List<String> ids = message.getList(Message.KEY_RECIPIENT_IDS);

        if (ids.size() == 1) {
            // last recipient - delete the whole thing!
            message.deleteInBackground();
        }
        else {
            // remove the recipient
            message.removeRecipient(User.getCurrentUser().getObjectId());
        }
    }

    @Override
    public void setRecyclerAdapter(RecyclerView.Adapter adapter) {

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setRecyclerLayout(RecyclerView.LayoutManager layoutManager) {

        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void setRecyclerFixedSize(boolean fixedSize) {

        recyclerView.setHasFixedSize(fixedSize);
    }
}








