package com.teamtreehouse.ribbit.ui.fragments.messages;

import android.content.Context;
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
import com.teamtreehouse.ribbit.dialogs.MessageOptionDialog;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.ui.activities.ActivityView;
import com.teamtreehouse.ribbit.ui.activities.ViewTextMessageActivity;
import com.teamtreehouse.ribbit.ui.callbacks.MessageRecipient;
import com.teamtreehouse.ribbit.ui.callbacks.TextMessagesCallback;
import com.teamtreehouse.ribbit.ui.fragments.FragmentPager;
import com.teamtreehouse.ribbit.utils.Resources;

import butterknife.BindView;

public class FragmentMessages
    extends
        FragmentPager<ActivityView, FragmentMessagesPresenter, InboxFragmentAdapter>
    implements
        FragmentMessagesView,
        MessageRecipient {

    private TextMessagesCallback messagesCallback;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.emptyMessagesTextView)
    TextView textView;

    @Override
    protected InboxFragmentAdapter createAdapter() {

        return new InboxFragmentAdapter(this);
    }

    @Override
    protected FragmentMessagesPresenter createPresenter() {

        return new FragmentMessagesPresenter(this);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        // Initialize the refresher layout
        presenter.setRefresherListener();
        presenter.setRefresherTheme(Resources.getRefresherColors());

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        this.messagesCallback = new TextMessagesCallback(this);
//        retrieveMessages();
    }

    @Override
    public void onResume() {
        super.onResume();

//        getActivity().setProgressBarIndeterminateVisibility(true);
    }

//    private void retrieveMessages() {
//        Query<Message> query = Message.getQuery();
//        query.whereEqualTo(Message.KEY_RECIPIENT_IDS, ObsoleteUser.getCurrentUser().getObjectId());
//        query.addDescendingOrder(Message.KEY_CREATED_AT);
//        query.findInBackground(new FindCallback<Message>() {
//            @Override
//            public void done(List<Message> messages, Exception e) {
////                getActivity().setProgressBarIndeterminateVisibility(false);
//
//                if (swipeRefreshLayout.isRefreshing()) {
//                    swipeRefreshLayout.setRefreshing(false);
//                }
//
//                if (e == null) {
//                    // We found messages!
//                    FragmentMessages.this.messages = messages;
//                    MessageAdapter adapter = (MessageAdapter) recyclerView.createAdapter();
//
//                    String[] usernames = new String[FragmentMessages.this.messages.size()];
//                    int i = 0;
//                    for (Message message : FragmentMessages.this.messages) {
//                        usernames[i] = message.getString(Message.KEY_SENDER_NAME);
//                        i++;
//                    }
//
//                    adapter.addMessages(FragmentMessages.this.messages);
//
//                    presenter.setEmptyTextViewVisibility(false);
//                }
//                else {
//
//                    presenter.setEmptyTextViewVisibility(true);
//                }
//            }
//        });
//    }

//    @Override
//    public void onItemSingleClick(Message message) {
//
//        String messageType = message.getString(Message.KEY_FILE_TYPE);
//        MessageFile file = message.getFile(Message.KEY_FILE);
//        Uri fileUri = file.getUri();
//
//        if (messageType.equals(Message.TYPE_IMAGE)) {
//            // view the image
//            Intent intent = new Intent(getActivity(), ImageMessageActivity.class);
//            intent.setData(fileUri);
//            startActivity(intent);
//        } else {
//            // view the video
//            Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
//            intent.setDataAndType(fileUri, "video/*");
//            startActivity(intent);
//        }
//
//        // Delete it!
//        List<String> ids = message.getList(Message.KEY_RECIPIENT_IDS);
//
//        if (ids.size() == 1) {
//            // last recipient - delete the whole thing!
//            message.deleteInBackground();
//        }
//        else {
//            // remove the recipient
//            message.removeRecipient(ObsoleteUser.getCurrentUser().getObjectId());
//        }
//    }

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
//                retrieveMessages();
            }
        });
    }

    @Override
    public void onItemSelected(Message message) {

        Intent intent = new Intent(this.getActivity(), ViewTextMessageActivity.class);
        intent.putExtra("message", message);
        startActivity(intent);
    }

    @Override
    public void execute() {

        MessageOptionDialog dialog = new MessageOptionDialog();
        dialog.show(this.getActivity().getSupportFragmentManager(), "messages_dialog");
    }

    @Override
    public void onMessageAdded(Message msg) {

        InboxFragmentAdapter adapter = getAdapter();
        adapter.add(msg);
    }

    @Override
    public void onMessageRemoved(Message message) {

        InboxFragmentAdapter adapter = getAdapter();
        int position = adapter.getPosition(message);
        adapter.removeItem(position);
    }
}








