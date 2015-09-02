package adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.oxilo.scash.AppointmentActivity;
import com.oxilo.scash.CustomVolleyRequestQueue;
import com.oxilo.scash.R;

import java.util.HashMap;

import holder.ChildHolder;
import holder.ChildItem;
import holder.GroupItem;


/**
 * Created by ericbasendra on 29/06/15.
 */
public class ManageEventAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private GroupItem groupitem;
    ImageLoader mImageLoader;
    public ManageEventAdapter(Context context, GroupItem groupitem) {
        inflater = LayoutInflater.from(context);
        this.groupitem = groupitem;
        this.context = context;
        mImageLoader = CustomVolleyRequestQueue.getInstance(context.getApplicationContext())
                .getImageLoader();
    }

    @Override
    public int getCount() {
        if (groupitem != null)
            return groupitem.items.size();
        else
            return 0;
    }

    @Override
    public ChildItem getItem(int arg0) {
        return groupitem.items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent) {

        ChildHolder holder = null;
        ChildItem item = getItem(pos);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.doctor_list_row, parent, false);
            holder = new ChildHolder();
            convertView.setTag(holder);
        }
        else {
            holder = (ChildHolder) convertView.getTag();
        }


        holder.mDocName = (TextView) convertView
                .findViewById(R.id.doc_name);
        holder.mDocSpecification = (TextView)convertView
                .findViewById(R.id.doc_specify);
        holder.mDocAdd = (TextView)convertView
                .findViewById(R.id.doc_add);
        holder.mDocViewSchdule = (TextView)convertView.findViewById(R.id.view_sch);
        holder.mDocImage = (NetworkImageView)convertView.findViewById(R.id.imageView1);

        holder.mDocName.setText(item.mDocName);
        holder.mDocSpecification.setText(item.mDocSpecification);
        holder.mDocAdd.setText(item.mDocAdd);
        holder.mDocImage.setImageUrl(item.mDocImageUrl,mImageLoader);
        holder.mDocViewSchdule.setTag(item);

        holder.mDocViewSchdule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              ChildItem childItem2 = (ChildItem)view.getTag();
                Log.e("IDIDID", "" + childItem2.event_id);
                Intent i = new Intent(context, AppointmentActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra(context.getResources().getString(R.string.constant_hasmap),prepareHashMap(childItem2));
                context.startActivity(i);
//                ((Activity)context).finish();
            }
        });
        return convertView;
    }


private HashMap<String,String>prepareHashMap(ChildItem childItem){
    HashMap<String, String> hm = new HashMap<String, String>();
    hm.put("name",childItem.mDocName);
    hm.put("email",childItem.mDocEmailId);
    hm.put("start_date",childItem.mDocSchStartDate);
    hm.put("end_date",childItem.mDocSchEndDate);
    hm.put("start_time",childItem.mDocSchStartTime);
    hm.put("end_time",childItem.mDocSchEndTime);
    hm.put("holiday",childItem.mDocHoliday);
    hm.put("consulation",childItem.mDocTerrif);
    return hm;
}



}
