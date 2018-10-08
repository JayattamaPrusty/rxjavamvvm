 package com.jayaa.mvvmrx.view;

 import android.databinding.DataBindingUtil;
 import android.support.annotation.NonNull;
 import android.support.v7.widget.RecyclerView;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.Toast;

 import com.jayaa.mvvmrx.R;
 import com.jayaa.mvvmrx.databinding.ItemMainadapterLayoutBinding;
 import com.jayaa.mvvmrx.model.NewsModelItem;

 import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder> {


    private List<NewsModelItem> mList;
    private LayoutInflater mInflater;

    @Override
    @NonNull
    public MainAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        ItemMainadapterLayoutBinding binding = DataBindingUtil.inflate(mInflater, R.layout.item_mainadapter_layout, parent, false);

        //return null;
        return new MainAdapterViewHolder(binding);
    }

    @Override

    public void onBindViewHolder(@NonNull MainAdapterViewHolder holder, int position) {
      holder.binding.setNewsModelItem(mList.get(position));

    }

    @Override
    public int getItemCount() {
        if (mList != null && mList.size() > 0)
            return mList.size();
        else
            return 0;
    }

    public static class MainAdapterViewHolder extends RecyclerView.ViewHolder {

        private final ItemMainadapterLayoutBinding binding;
        //ImageView iv_image;

        public MainAdapterViewHolder(final ItemMainadapterLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(binding.getRoot().getContext(),binding.getNewsModelItem().getTitle(),Toast.LENGTH_SHORT).show();
                }
            });
           // iv_image=(ImageView)binding.ivNewsitem;
        }

    }

    public void showList(List<NewsModelItem> noteList) {
        this.mList = noteList;
        notifyDataSetChanged();
    }
}
