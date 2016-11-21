package aschantz.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import aschantz.shop.MainActivity;
import aschantz.shop.R;
import aschantz.shop.data.Item;

/**
 * Created by aschantz on 11/10/16.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivIcon;
        public TextView tvItem;
        public TextView tvPrice;
        public Button btnDelete;
        public Button btnEdit;
        public TextView tvDescription;
        public CheckBox cbPurchased;

        public ViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvItem = (TextView) itemView.findViewById(R.id.tvItem);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            cbPurchased = (CheckBox) itemView.findViewById(R.id.cbPurchased);
        }
    }

    private List<Item> itemList;
    private Context context;
    private int lastPosition = -1;

    public ItemAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.tvItem.setText(itemList.get(position).getItemName());
        viewHolder.tvPrice.setText(itemList.get(position).getPrice());
        viewHolder.tvDescription.setText(itemList.get(position).getDescription());

        viewHolder.cbPurchased.setChecked(itemList.get(position).isBought());
        viewHolder.cbPurchased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = itemList.get(position);
                item.setBought(viewHolder.cbPurchased.isChecked());
                item.save();
            }
        });


        viewHolder.ivIcon.setImageResource(
                itemList.get(position).getItemType().getIconId());

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).showEditItemActivity(itemList.get(position), position);
            }
        });

        setAnimation(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void checkAll() {
        int count = getItemCount();
        for(int i = 0; i < Item.count(Item.class); i++){
            itemList.get(i).setBought(true);
            notifyItemChanged(i);
        }
        notifyDataSetChanged();
    }

    public void unCheckAll() {
        int count = getItemCount();
        for(int i = 0; i < Item.count(Item.class); i++){
            itemList.get(i).setBought(false);
            notifyItemChanged(i);
        }
        notifyDataSetChanged();
    }

    public void addItem(Item item) {
        item.save();
        itemList.add(item);
        notifyDataSetChanged();
    }

    public void updateItem(int index, Item item) {
        itemList.set(index, item);
        item.save();
        notifyItemChanged(index);
    }

    public void removeItem(int index) {
        // remove it from the DB
        itemList.get(index).delete();
        // remove it from the list
        itemList.remove(index);
        notifyDataSetChanged();
    }

    public void clearAll() {
        itemList.removeAll(itemList);
        notifyDataSetChanged();
    }

    public void swapItem(int oldPosition, int newPosition) {
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(itemList, i, i + 1);
            }
        } else {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(itemList, i, i - 1);
            }
        }
        notifyItemMoved(oldPosition, newPosition);
    }

    public Item getItem(int i) {
        return itemList.get(i);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
