 package com.jayaa.mvvmrx.view;

 import android.databinding.DataBindingUtil;
 import android.support.v7.widget.RecyclerView;
 import android.view.LayoutInflater;
 import android.view.ViewGroup;

 import com.bumptech.glide.Glide;
 import com.jayaa.mvvmrx.R;
 import com.jayaa.mvvmrx.databinding.ItemVersionLayoutBinding;
 import com.jayaa.mvvmrx.model.NewsModelItem;

 import java.util.List;

public class VersionAdapter extends RecyclerView.Adapter<VersionAdapter.VersionAdapterViewHolder> {


    private List<NewsModelItem> mList;
    private LayoutInflater mInflater;

    @Override
    public VersionAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        ItemVersionLayoutBinding binding = DataBindingUtil.inflate(mInflater, R.layout.item_version_layout, parent, false);

        //return null;
        return new VersionAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(VersionAdapterViewHolder holder, int position) {
      holder.binding.setNewsModelItem(mList.get(position));
        Glide.with(holder.binding.ivNewsitem.getContext()).load(mList.get(position).getImageHref())
                .thumbnail(0.5f)
                .into(holder.binding.ivNewsitem);

    }

    @Override
    public int getItemCount() {
        if (mList != null && mList.size() > 0)
            return mList.size();
        else
            return 0;
    }

    public static class VersionAdapterViewHolder extends RecyclerView.ViewHolder {

        private final ItemVersionLayoutBinding binding;
        //ImageView iv_image;

        public VersionAdapterViewHolder(ItemVersionLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
           // iv_image=(ImageView)binding.ivNewsitem;
        }

    }

    public void showList(List<NewsModelItem> noteList) {
        this.mList = noteList;
        notifyDataSetChanged();
    }
}
