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
import com.teamtreehouse.ribbit.dialogs.MessageOptionDialog;
import com.teamtreehouse.ribbit.models.ImageMessage;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.models.TextMessage;
import com.teamtreehouse.ribbit.models.VideoMessage;
import com.teamtreehouse.ribbit.ui.activities.ActivityView;
import com.teamtreehouse.ribbit.ui.activities.messages.view.ViewImageMessageActivity;
import com.teamtreehouse.ribbit.ui.activities.messages.view.ViewVideoMessageActivity;
import com.teamtreehouse.ribbit.ui.callbacks.ImageMessagesCallback;
import com.teamtreehouse.ribbit.ui.callbacks.MessageRecipient;
import com.teamtreehouse.ribbit.ui.callbacks.TextMessagesCallback;
import com.teamtreehouse.ribbit.ui.callbacks.VideoMessagesCallback;
import com.teamtreehouse.ribbit.ui.fragments.FragmentPager;
import com.teamtreehouse.ribbit.ui.activities.messages.view.ViewTextMessageActivity;
import com.teamtreehouse.ribbit.utils.Resources;

import butterknife.BindView;

public class FragmentInbox
    extends
        FragmentPager<ActivityView, FragmentInboxPresenter, InboxFragmentAdapter>
    implements
        FragmentInboxView,
        MessageRecipient,
        ImageMessagesCallback.ImageMessageListener, VideoMessagesCallback.VideoMessageListener {

    private TextMessagesCallback messagesCallback;
    private ImageMessagesCallback imageMessagesCallback;
    private VideoMessagesCallback videoMessagesCallback;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

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
        this.imageMessagesCallback = new ImageMessagesCallback(this);
        this.videoMessagesCallback = new VideoMessagesCallback(this);
    }

    @Override
    public void onResume() {
        super.onResume();

//        getActivity().setProgressBarIndeterminateVisibility(true);
    }

//    private void retrieveMessages() {
//        Query<TextMessage> query = TextMessage.getQuery();
//        query.whereEqualTo(TextMessage.KEY_RECIPIENT_IDS, ObsoleteUser.getCurrentUser().getObjectId());
//        query.addDescendingOrder(TextMessage.KEY_CREATED_AT);
//        query.findInBackground(new FindCallback<TextMessage>() {
//            @Override
//            public void done(List<TextMessage> messages, Exception e) {
////                getActivity().setProgressBarIndeterminateVisibility(false);
//
//                if (swipeRefreshLayout.isRefreshing()) {
//                    swipeRefreshLayout.setRefreshing(false);
//                }
//
//                if (e == null) {
//                    // We found messages!
//                    FragmentInbox.this.messages = messages;
//                    MessageAdapter adapter = (MessageAdapter) recyclerView.createAdapter();
//
//                    String[] usernames = new String[FragmentInbox.this.messages.size()];
//                    int i = 0;
//                    for (TextMessage message : FragmentInbox.this.messages) {
//                        usernames[i] = message.getString(TextMessage.KEY_SENDER_NAME);
//                        i++;
//                    }
//
//                    adapter.addMessages(FragmentInbox.this.messages);
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
//    public void onItemSingleClick(TextMessage message) {
//
//        String messageType = message.getString(TextMessage.KEY_FILE_TYPE);
//        MessageFile file = message.getFile(TextMessage.KEY_FILE);
//        Uri fileUri = file.getUri();
//
//        if (messageType.equals(TextMessage.TYPE_IMAGE)) {
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
//        List<String> ids = message.getList(TextMessage.KEY_RECIPIENT_IDS);
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

        if(message instanceof TextMessage) {

            Intent intent = new Intent(this.getActivity(), ViewTextMessageActivity.class);
            intent.putExtra(Message.KEY, message);
            startActivity(intent);
        }
        else if(message instanceof ImageMessage) {

//            int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            if (permission != PackageManager.PERMISSION_GRANTED) {
//
//                ((MainActivity)this.parent).requestPermissions();
//                return;
//            }

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

        MessageOptionDialog dialog = new MessageOptionDialog();
        dialog.show(this.getActivity().getSupportFragmentManager(), "messages_dialog");
    }

    @Override
    public void onMessageAdded(TextMessage msg) {

        InboxFragmentAdapter adapter = getAdapter();
        adapter.add(msg);
    }

    @Override
    public void onMessageRemoved(TextMessage message) {

        InboxFragmentAdapter adapter = getAdapter();
        int position = adapter.getPosition(message);
        adapter.removeItem(position);
    }

    @Override
    public void onMessageAdded(ImageMessage message) {

        InboxFragmentAdapter adapter = getAdapter();
        adapter.add(message);
    }

    @Override
    public void onMessageRemoved(ImageMessage message) {

        InboxFragmentAdapter adapter = getAdapter();
        int position = adapter.getPosition(message);
        adapter.removeItem(position);
    }

    @Override
    public void onMessageAdded(VideoMessage message) {

        InboxFragmentAdapter adapter = getAdapter();
        adapter.add(message);
    }

    @Override
    public void onMessageRemoved(VideoMessage message) {

        InboxFragmentAdapter adapter = getAdapter();
        int position = adapter.getPosition(message);
        adapter.removeItem(position);
    }
}