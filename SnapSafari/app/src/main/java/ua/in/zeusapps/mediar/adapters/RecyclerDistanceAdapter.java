package ua.in.zeusapps.mediar.adapters;

import android.content.Context;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ua.in.zeusapps.mediar.R;
import ua.in.zeusapps.mediar.controls.DistanceDiffUtil;
import ua.in.zeusapps.mediar.models.Event;

/**
 * Created by- slava on 15.01.18.
 */

public class RecyclerDistanceAdapter extends RecyclerView.Adapter<RecyclerDistanceAdapter.ViewHolder> {

    private List<String> mDistances;
    private List<Event> mAnimals;
    private Context mContext;

    public RecyclerDistanceAdapter(List<String> list, List<Event> animals, Context context) {
        mDistances = list;
        mAnimals = animals;
        mContext = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextDistance;
        TextView mAnimalName;

        ViewHolder(View itemView) {
            super(itemView);
            mTextDistance = (TextView) itemView.findViewById(R.id.tv_distance);
            mAnimalName = (TextView) itemView.findViewById(R.id.tv_animal_name);
        }
    }

    @MainThread
    public void updateList(@NonNull List<String> newList) {
//        if (newList != null && mDistances != null) {
//            for (int i = 0; i < newList.size(); i++) {
//                if (!newList.get(i).equals(mDistances.get(i))) {
//                    changeItemAtPosition(i, newList.get(i));
//                }
//            }
//        }

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DistanceDiffUtil(mDistances, newList));
        diffResult.dispatchUpdatesTo(this);

        mDistances = newList;
    }

    public void setAnimals(List<Event> animals) {
        mAnimals = animals;
    }

    public List<Event> getAnimals() {
        return mAnimals;
    }

    @Override
    public RecyclerDistanceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_distance, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mAnimals.size() == 0) return;
        Double d = Double.valueOf(mDistances.get(position).replace(",","."));
        if (d < 10.0){
            holder.mTextDistance.setTextColor(mContext.getResources().getColor(R.color.colorSnap));
        } else {
            holder.mTextDistance.setTextColor(mContext.getResources().getColor(R.color.colorSnapCards));
        }
        holder.mTextDistance.setText(
                String.format(
                        mContext.getResources().getString(R.string.model_meters),
                        mDistances.get(position)));
        holder.mAnimalName.setText(mAnimals.get(position).getCard().getTitle());
    }

    @Override
    public int getItemCount() {
        return mDistances == null ? 0 : mDistances.size();
    }
}
