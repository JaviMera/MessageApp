package com.teamtreehouse.ribbit.ui.fragments.messages;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.InboxFragmentAdapter;
import com.teamtreehouse.ribbit.adapters.MessageAdapter;
import com.teamtreehouse.ribbit.models.purgatory.Message;
import com.teamtreehouse.ribbit.models.purgatory.MessageFile;
import com.teamtreehouse.ribbit.models.purgatory.ObsoleteUser;
import com.teamtreehouse.ribbit.models.purgatory.Query;
import com.teamtreehouse.ribbit.models.callbacks.FindCallback;
import com.teamtreehouse.ribbit.ui.activities.ViewImageActivity;
import com.teamtreehouse.ribbit.ui.fragments.FragmentPager;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecycler;
import com.teamtreehouse.ribbit.utils.Resources;

import java.util.List;

import butterknife.BindView;

public class InboxFragment extends FragmentRecycler<InboxFragmentPresenter, InboxFragmentAdapter>
    implements
        InboxFragmentView,
        FragmentPager,
        MessageAdapter.OnRecyclerViewClick {

    protected List<Message> messages;
    protected FragmentActivity parent;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.emptyMessagesTextView)
    TextView textView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.parent = (FragmentActivity) context;
    }

    @Override
    protected InboxFragmentAdapter getAdapter() {

        return new InboxFragmentAdapter(this);
    }

    @Override
    protected InboxFragmentPresenter getPresenter() {

        return new InboxFragmentPresenter(this);
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_inbox;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        // Initialize the refresher layout
        presenter.setRefresherListener();
        presenter.setRefresherTheme(Resources.getRefresherColors());

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

//        retrieveMessages();
    }

    @Override
    public void onResume() {
        super.onResume();

//        getActivity().setProgressBarIndeterminateVisibility(true);
    }

    private void retrieveMessages() {
        Query<Message> query = Message.getQuery();
        query.whereEqualTo(Message.KEY_RECIPIENT_IDS, ObsoleteUser.getCurrentUser().getObjectId());
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

                    presenter.setEmptyTextViewVisibility(false);
                }
                else {

                    presenter.setEmptyTextViewVisibility(true);
                }
            }
        });
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
            message.removeRecipient(ObsoleteUser.getCurrentUser().getObjectId());
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

    @Override
    public void setEmptyTextViewVisibility(boolean visible) {

        textView.setVisibility(
            visible
            ? View.VISIBLE
            : View.GONE
        );
    }

    @Override
    public void setRefresherColors(int[] colors) {

        swipeRefreshLayout.setColorSchemeResources(colors);
    }

    @Override
    public void setRefresherListener() {

        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveMessages();
            }
        });
    }

    @Override
    public void launchActivity() {

        Toast.makeText(this.getContext(), "hi", Toast.LENGTH_SHORT).show();
    }
}








