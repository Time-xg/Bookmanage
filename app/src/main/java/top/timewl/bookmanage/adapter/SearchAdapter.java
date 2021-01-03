package top.timewl.bookmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import top.timewl.bookmanage.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    private Context context;
    private List<String> datas;
    private LayoutInflater mInflater;

    public SearchAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
        mInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_seacher,parent,false);
        SearchViewHolder viewHolder = new SearchViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
class SearchViewHolder extends RecyclerView.ViewHolder{

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
