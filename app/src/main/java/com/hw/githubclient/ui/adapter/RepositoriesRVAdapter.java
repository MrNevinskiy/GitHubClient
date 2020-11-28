package com.hw.githubclient.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hw.githubclient.R;
import com.hw.githubclient.mvp.presenter.list.IRepositoryListPresenter;
import com.hw.githubclient.mvp.view.list.RepositoryItemView;

public class RepositoriesRVAdapter extends RecyclerView.Adapter<RepositoriesRVAdapter.ViewHolder> {
    private IRepositoryListPresenter presenter;

    public RepositoriesRVAdapter(IRepositoryListPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public RepositoriesRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View repoView = inflater.inflate(R.layout.item_repository, parent, false);

        RepositoriesRVAdapter.ViewHolder viewHolder = new RepositoriesRVAdapter.ViewHolder(repoView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoriesRVAdapter.ViewHolder holder, int position) {
        holder.position = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onItemClick(holder);
            }
        });

        presenter.bindView(holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements RepositoryItemView {
        TextView name;
        int position = -1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tv_name);
        }

        @Override
        public int getPos() {
            return position;
        }

        @Override
        public void setName(String text) {
            name.setText(text);
        }
    }
}
