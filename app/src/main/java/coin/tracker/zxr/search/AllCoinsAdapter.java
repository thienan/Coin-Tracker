package coin.tracker.zxr.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import coin.tracker.zxr.R;
import coin.tracker.zxr.models.CoinListItem;
import coin.tracker.zxr.utils.CoinHelper;
import coin.tracker.zxr.utils.TextUtils;

/**
 * Created by Mayur on 22-09-2017.
 */

public class AllCoinsAdapter extends RecyclerView.Adapter<AllCoinsAdapter.ViewHolder> {

    private HashMap<String, String> items;
    private ArrayList<String> coinTags;
    private Context context;
    SearchCoinListener searchCoinListener;

    public AllCoinsAdapter(Context context,
                           HashMap<String,String> items) {
        this.items = items;
        this.context = context;
        this.searchCoinListener = (SearchCoinListener) context ;
        coinTags = new ArrayList<String>(items.keySet());

        // Don't show coins that user has already selected
        ArrayList<String> userCoins = CoinHelper.getInstance().getAllUserCoins();
        for (String coinTag : userCoins) {
            coinTags.remove(coinTag);
            this.items.remove(coinTag);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_coin, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String coinTag = coinTags.get(position);
        final String coinName = items.get(coinTag);

        if (TextUtils.isValidString(coinName)) {
            holder.tvCoinName.setText(coinName);
        } else {
            holder.tvCoinName.setText("n/a");
        }

        if (TextUtils.isValidString(coinTag)) {
            holder.tvCoinTag.setText(coinTag);
        }

        holder.cbSelectedCoin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (searchCoinListener != null) {
                    if (isChecked) {
                        searchCoinListener.onCoinSelected(coinTag, coinName);
                    } else {
                        searchCoinListener.onCoinUnselected(coinTag, coinName);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
//        FIXME implement lazy loading
//        return items.size();
        return 10;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvCoinTag)
        TextView tvCoinTag;

        @BindView(R.id.tvCoinName)
        TextView tvCoinName;

        @BindView(R.id.cbSelectedCoin)
        CheckBox cbSelectedCoin;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}